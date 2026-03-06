package com.example.aushadhiplus.presentation.medicine

import androidx.paging.PagingData
import com.example.aushadhiplus.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

sealed class MedicineUiState {
    object Loading : MedicineUiState()

    data class Success(
        val data: Flow<PagingData<Medicine>>
    ) : MedicineUiState()


    data class Error(val message: String) : MedicineUiState()
}