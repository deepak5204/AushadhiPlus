package com.example.aushadhiplus.presentation.medicine

import com.example.aushadhiplus.domain.model.Medicine

sealed class MedicineUiState{
    object IsLoading: MedicineUiState()
    data class Success(val data: List<Medicine>): MedicineUiState()
    data class Error(val message: String) : MedicineUiState()
}