package com.example.aushadhiplus.presentation.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.aushadhiplus.domain.model.Medicine
import com.example.aushadhiplus.presentation.dashboard.DashboardBlue
import com.example.aushadhiplus.presentation.dashboard.DashboardScreen
import com.example.aushadhiplus.presentation.medicine.AddMedicineScreen
import com.example.aushadhiplus.presentation.medicine.MedicineScreen

data class BottomNavItem(
    val route: String, val label: String, val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("dashboard", "Dashboard", Icons.Default.Dashboard),
        BottomNavItem("inventory", "Inventory", Icons.Default.Inventory),
        BottomNavItem("reports", "Reports", Icons.Default.BarChart),
        BottomNavItem("settings", "Settings", Icons.Default.Settings)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AushadhiPlus", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DashboardBlue),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                })
        },

        bottomBar = {
            NavigationBar {

                val navBackStackEntry = navController.currentBackStackEntryAsState()

                val currentRoute = navBackStackEntry.value?.destination?.route

                items.forEach { item ->

                    NavigationBarItem(
                        selected = currentRoute == item.route,

                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo("dashboard")
                                launchSingleTop = true
                            }
                        },

                        icon = {
                            Icon(item.icon, contentDescription = item.label)
                        },

                        label = { Text(item.label) })
                }
            }
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("dashboard") {
                DashboardScreen(
                    onInventoryClick = {
                        navController.navigate("medicine")
                    })
            }

            composable("inventory") {
                MedicineScreen(
                    navController = navController,
                    onAddMedicineClick = {
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.remove<Medicine>("medicine")
                        navController.navigate("add_medicine")
                    },
                    onEditClick = { medicine ->
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("medicine", medicine)

                        navController.navigate("add_medicine")
                    }
                )
            }

            composable("add_medicine") {

                val medicine =
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Medicine>("medicine")

                AddMedicineScreen(
                    onAdd = {
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.remove<Medicine>("medicine")
                        navController.popBackStack()
                    },
                    onDismiss = {
                        navController.popBackStack()
                    },
                    medicine = medicine
                )
            }

            composable("reports") {
                Text("Reports Screen")
            }

            composable("settings") {
                Text("Settings Screen")
            }

        }
    }
}