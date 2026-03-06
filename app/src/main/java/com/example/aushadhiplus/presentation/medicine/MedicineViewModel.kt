package com.example.aushadhiplus.presentation.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.aushadhiplus.data.repository.MedicineRepository
import com.example.aushadhiplus.domain.model.MedicineRequest
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

    private val _addState = MutableStateFlow<AddMedicineUiState>(AddMedicineUiState.Idle)
    val addState: StateFlow<AddMedicineUiState> = _addState

    init {
        fetchMedicines()
    }


    fun addMedicine(request: MedicineRequest) {
        viewModelScope.launch {
            _addState.value = AddMedicineUiState.Loading
            try {
                repository.addMedicine(request)
                _addState.value = AddMedicineUiState.Success
            } catch (e: Exception) {
                _addState.value = AddMedicineUiState.Error(e.message ?: "Error")
            }
        }
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