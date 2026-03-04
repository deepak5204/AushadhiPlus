package com.example.aushadhiplus.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Color Palette from the image
val DashboardBlue = Color(0xFF1976D2)
val DashboardAmber = Color(0xFFFFB300)
val DashboardRed = Color(0xFFE53935)
val DashboardGreen = Color(0xFF43A047)
val BackgroundGray = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("AushadhiPlus", color = Color.White, fontWeight = FontWeight.Bold)},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DashboardBlue),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = true, onClick = {}, icon = {Icon(Icons.Default.Dashboard, contentDescription = "Dashboard")}, label = { Text("Dashboard") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Inventory, "Inventory") }, label = { Text("Inventory") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.BarChart, "Reports") }, label = { Text("Reports") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, "Settings") }, label = { Text("Settings") })
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGray)
                .padding(horizontal = 8.dp)
        ) {

            item {
                Text(
                    text = "Inventory Dashboard",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Stats Grid
            item {
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatCard("TOTAL MEDICINES", "44", "Different items", DashboardBlue, Icons.Default.Medication, Modifier.weight(1f))
                        StatCard("LOW STOCK ITEMS", "16", "Action required", DashboardAmber, Icons.Default.Warning, Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatCard("EXPIRING SOON", "3", "Within 30 days", DashboardRed, Icons.Default.EventBusy, Modifier.weight(1f))
                        StatCard("TOTAL VALUE", "₹43,519", "Current cost", DashboardGreen, Icons.Default.AccountBalanceWallet, Modifier.weight(1f))
                    }
                }
            }

            // Recent Alerts Section
            item {
                Spacer(Modifier.height(24.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Recent Low Stock Alerts", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.height(8.dp))
                        AlertItem("Paracetamol", "12")
                        AlertItem("Amoxicillin", "28")
                    }
                }
            }

        }
    }
}


@Composable
fun StatCard(label: String, value: String, subText: String, color: Color, icon: ImageVector, modifier: Modifier) {
    Card(
        modifier = modifier.height(130.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier
            .padding(12.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(32.dp))
            }
            Text(subText, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
        }
    }
}

@Composable
fun AlertItem(name: String, qty: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier
                .size(8.dp)
                .background(DashboardRed, RoundedCornerShape(2.dp)))
            Spacer(Modifier.width(8.dp))
            Text(name, fontSize = 14.sp)
        }
        Text("Qty: $qty", fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}


@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen()
}