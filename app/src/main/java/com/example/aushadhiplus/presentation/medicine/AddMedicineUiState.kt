package com.example.aushadhiplus.presentation.medicine

sealed class AddMedicineUiState {
    object Idle : AddMedicineUiState()

    object Loading : AddMedicineUiState()

    object Success : AddMedicineUiState()

    data class Error(
        val message: String
    ) : AddMedicineUiState()
}