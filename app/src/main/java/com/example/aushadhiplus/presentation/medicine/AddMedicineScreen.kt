package com.example.aushadhiplus.presentation.medicine

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.aushadhiplus.domain.model.MedicineRequest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    onDismiss: () -> Unit = {},
    onAdd: () -> Unit = {},
    viewModel: MedicineViewModel = hiltViewModel()
) {

    var name by remember { mutableStateOf("") }
    var manufacturer by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var threshold by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val categories = listOf("Tablet", "Syrup", "Injection")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = 0.4f) // Dimmed background for dialog effect
    ) {

        Scaffold(
            containerColor = Color.Transparent, // Keep the dimmed background visible
            snackbarHost = {
                // 2. This is where the Snackbar actually renders
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Header
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Add New Medicine",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E)
                                    )
                                )
                                IconButton(onClick = onDismiss) {
                                    Icon(Icons.Default.Close, contentDescription = "Close")
                                }
                            }
                        }

                        // Name and Manufacturer
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                FormField(
                                    label = "Name",
                                    value = name,
                                    onValueChange = { name = it },
                                    keyboardType = KeyboardType.Text,
                                    modifier = Modifier.weight(1f),
                                    placeholder = "name"
                                )
                                FormField(
                                    label = "Manufacturer",
                                    value = manufacturer,
                                    onValueChange = { manufacturer = it },
                                    keyboardType = KeyboardType.Text,
                                    modifier = Modifier.weight(1f),
                                    placeholder = "manufacturer"
                                )
                            }
                        }

                        // Price and Quantity
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                FormField(
                                    label = "Price (₹)",
                                    value = price,
                                    onValueChange = { price = it },
                                    keyboardType = KeyboardType.Number,
                                    modifier = Modifier.weight(1f),
                                    placeholder = "Price"
                                )
                                FormField(
                                    label = "Quantity",
                                    value = quantity,
                                    onValueChange = { quantity = it },
                                    keyboardType = KeyboardType.Number,
                                    modifier = Modifier.weight(1f),
                                    placeholder = "Enter quantity"
                                )
                            }
                        }

                        // Expiry Date and Category
                        item {
                            // 2. The Logic to show the Dialog
                            if (showDatePicker) {
                                DatePickerDialog(
                                    onDismissRequest = { showDatePicker = false },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            val selectedDate = datePickerState.selectedDateMillis
                                            if (selectedDate != null) {
                                                // Convert millis to readable String (YYYY-MM-DD)
                                                expiryDate = SimpleDateFormat(
                                                    "yyyy-MM-dd",
                                                    Locale.getDefault()
                                                )
                                                    .format(Date(selectedDate))
                                            }
                                            showDatePicker = false
                                        }) { Text("OK") }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            showDatePicker = false
                                        }) { Text("Cancel") }
                                    }
                                ) {
                                    DatePicker(state = datePickerState)
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // 2. The Logic to show the Dialog
                                if (showDatePicker) {
                                    DatePickerDialog(
                                        onDismissRequest = { showDatePicker = false },
                                        confirmButton = {
                                            TextButton(onClick = {
                                                val selectedDate =
                                                    datePickerState.selectedDateMillis
                                                if (selectedDate != null) {
                                                    // Convert millis to readable String (YYYY-MM-DD)
                                                    expiryDate = SimpleDateFormat(
                                                        "yyyy-MM-dd",
                                                        Locale.getDefault()
                                                    )
                                                        .format(Date(selectedDate))
                                                }
                                                showDatePicker = false
                                            }) { Text("OK") }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = { showDatePicker = false }) {
                                                Text(
                                                    "Cancel"
                                                )
                                            }
                                        }
                                    ) {
                                        DatePicker(state = datePickerState)
                                    }
                                }

// 3. Update your FormField call
                                FormField(
                                    label = "Expiry Date",
                                    value = expiryDate,
                                    onValueChange = { /* Handled by DatePicker */ },
                                    placeholder = "yyyy-mm-dd",
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            showDatePicker = true
                                        },
                                    enabled = false,
                                    readOnly = false,
                                    trailingIcon = {
                                        IconButton(onClick = { showDatePicker = true }) {
                                            Icon(
                                                Icons.Default.CalendarMonth,
                                                contentDescription = null,
                                                tint = Color.Gray
                                            )
                                        }
                                    }
                                )
//                                FormField(
//                                    label = "Category",
//                                    value = category,
//                                    onValueChange = { category = it },
//                                    placeholder = "category",
//                                    modifier = Modifier.weight(1f),
//                                    trailingIcon = {
//                                        Icon(
//                                            Icons.Default.KeyboardArrowDown,
//                                            contentDescription = null,
//                                            tint = Color.Gray
//                                        )
//                                    })

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(
                                        text = "Category",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )

                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = { expanded = !expanded }
                                    ) {

                                        TextField(
                                            value = category,
                                            onValueChange = {},
                                            readOnly = true,
                                            placeholder = { Text("Select") },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                            },
                                            modifier = Modifier
                                                .menuAnchor()
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp)),
                                            colors = TextFieldDefaults.colors(
                                                focusedContainerColor = Color(0xFFF1F3F4),
                                                unfocusedContainerColor = Color(0xFFF1F3F4),
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            )
                                        )

                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false }
                                        ) {

                                            categories.forEach { item ->

                                                DropdownMenuItem(
                                                    text = { Text(item) },
                                                    onClick = {
                                                        category = item
                                                        expanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

//                        // Low Stock Threshold
//                        item {
//                            FormField(
//                                label = "Low Stock Threshold",
//                                value = threshold,
//                                onValueChange = { threshold = it },
//                                placeholder = "stock",
//                                modifier = Modifier.fillMaxWidth(0.5f)
//                            )
//                        }

                        // Image Upload Area
                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    "Add Product Image (optional)",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .border(1.dp, Color(0xFFB0E0E6), RoundedCornerShape(12.dp))
                                        .background(Color(0xFFF0F8FF), RoundedCornerShape(12.dp))
                                        .clickable { /* Open Camera/Gallery */ },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            Icons.Default.CameraAlt,
                                            contentDescription = null,
                                            tint = Color.Gray
                                        )
                                        Text("Camera+", fontSize = 12.sp, color = Color.Gray)
                                        Text(
                                            "Tap to upload product image",
                                            fontSize = 11.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }

                        // Buttons
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = onDismiss,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFF1F3F4
                                        )
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                ) {
                                    Text("Cancel", color = Color.Black)
                                }
                                Button(
                                    onClick = {
                                        val validationError = when {
                                            name.isBlank() -> "Medicine name is required"
                                            manufacturer.isBlank() -> "Manufacturer is required"
                                            price.isBlank() || price.toInt() <= 0 -> "Please enter a valid price"
                                            quantity.isBlank() || quantity.toInt() < 0 -> "Please enter a valid quantity"
                                            category.isBlank() -> "Please select category"
                                            expiryDate.isBlank() -> "Please select an expiry date"
                                            else -> null
                                        }

                                        if (validationError != null) {
                                            // Show local validation error
                                            scope.launch {
                                                snackbarHostState.showSnackbar(validationError)
                                            }
                                        } else {
                                            // Call ViewModel to save
                                            viewModel.addMedicine(
                                                MedicineRequest(
                                                    name = name,
                                                    manufacturer = manufacturer,
                                                    price = price.toInt(),
                                                    quantity = quantity.toInt(),
                                                    expiryDate = expiryDate,
                                                    category = category
//                                                    lowStockThreshold = threshold.toInt()
                                                )
                                            )
                                            onAdd()
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF28A745
                                        )
                                    ),
                                    shape = RoundedCornerShape(25.dp)
                                ) {
                                    Text("Add Medicine", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        TextField(
            value = value,
            placeholder = { Text(text = placeholder, color = Color.LightGray) },
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            enabled = enabled,
            readOnly = readOnly,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF1F3F4),
                unfocusedContainerColor = Color(0xFFF1F3F4),
                disabledContainerColor = Color(0xFFF1F3F4),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}