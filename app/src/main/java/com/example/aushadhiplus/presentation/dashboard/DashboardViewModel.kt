package com.example.aushadhiplus.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aushadhiplus.data.repository.DashboardRepository
import com.example.aushadhiplus.presentation.dashboard.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<DashboardUiState>(DashboardUiState.isLoading)

    val uiState: StateFlow<DashboardUiState> = _uiState
    init {
        fetchDashboardStats()
    }

    private fun fetchDashboardStats(){
        viewModelScope.launch {
            try {
                val result = repository.getDashboardStats()
                _uiState.value = DashboardUiState.Success(data = result)
            } catch (e: Exception) {
                _uiState.value =
                    DashboardUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

}