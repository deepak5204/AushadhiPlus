package com.example.aushadhiplus.presentation.medicine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MedicineScreen(
    modifier: Modifier = Modifier, viewModel: MedicineViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val uiState = state
    when (uiState) {

        is MedicineUiState.IsLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MedicineUiState.Error -> {
            Text(text = (state as MedicineUiState.Error).message ?: "Something went wrong")
        }

        is MedicineUiState.Success -> {
            val medicines = uiState.data
            println(medicines)
            LazyColumn {

                items(medicines) { medicine ->
//                    println("medicine name: " + medicine.name)
                    Text(text = medicine.name)
                }
            }
        }
    }
}