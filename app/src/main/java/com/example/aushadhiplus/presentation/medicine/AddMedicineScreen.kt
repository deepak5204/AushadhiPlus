package com.example.aushadhiplus.presentation.medicine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.aushadhiplus.domain.model.MedicineRequest

@Composable
fun AddMedicineScreen(
    viewModel: MedicineViewModel = hiltViewModel()
) {

    var name by remember { mutableStateOf("") }
    var manufacturer by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Medicine Name") })

        OutlinedTextField(
            value = manufacturer,
            onValueChange = { manufacturer = it },
            label = { Text("Manufacturer") })

        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") })

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            val request = MedicineRequest(
                name = name,
                manufacturer = manufacturer,
                price = price.toInt(),
                quantity = quantity.toInt(),
                expiryDate = "2026-12-31",
                category = "Tablet",
                lowStockThreshold = 5
            )

            viewModel.addMedicine(request)

        }) {
            Text("Add Medicine")
        }
    }
}