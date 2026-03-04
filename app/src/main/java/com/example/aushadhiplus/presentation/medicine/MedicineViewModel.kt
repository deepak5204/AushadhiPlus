package com.example.aushadhiplus.presentation.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {
    private val _uiState = MutableStateFlow<MedicineUiState>(MedicineUiState.IsLoading)
    val uiState: StateFlow<MedicineUiState> = _uiState

    init {
        fetchMedicines()
    }

    fun fetchMedicines(){
        viewModelScope.launch {
            try {
                val result = repository.getMedicines()
                _uiState.value = MedicineUiState.Success(data = result)
            }
            catch (e: Exception){
                _uiState.value =
                    MedicineUiState.Error(e.message ?: "Error")
            }
        }
    }


}