package com.example.aushadhiplus.data.repository

import com.example.aushadhiplus.data.remote.api.DashboardApiService
import com.example.aushadhiplus.domain.model.DashboardResponse
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: DashboardApiService
) {
    suspend fun getDashboardStats() : DashboardResponse {
        return apiService.getDashboardStats()
    }
}