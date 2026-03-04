package com.example.aushadhiplus.data.remote.api

import com.example.aushadhiplus.domain.model.DashboardResponse
import retrofit2.http.GET

interface DashboardApiService {
    @GET("dashboard/stats")
    suspend fun getDashboardStats(): DashboardResponse
}