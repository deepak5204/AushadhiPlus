package com.example.aushadhiplus.presentation.dashboard

import com.example.aushadhiplus.domain.model.DashboardResponse

sealed class DashboardUiState {
    object isLoading: DashboardUiState()
    data class Success(val data: DashboardResponse) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}