package com.example.aushadhiplus.presentation.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.aushadhiplus.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MedicineUiState>(MedicineUiState.Loading)
    val uiState: StateFlow<MedicineUiState> = _uiState

    init {
        fetchMedicines()
    }

    private fun fetchMedicines() {
//        viewModelScope.launch { // Pager.flow → already async stream
        try {
            val medicinesFlow = repository.getMedicines().cachedIn(viewModelScope)

            _uiState.value = MedicineUiState.Success(data = medicinesFlow)

        } catch (e: Exception) {
            _uiState.value = MedicineUiState.Error(e.message ?: "Something went wrong")
        }
//        }
    }
}