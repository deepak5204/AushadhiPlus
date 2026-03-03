package com.example.aushadhiplus.data.remote.api

import com.example.aushadhiplus.domain.model.LoginRequest
import com.example.aushadhiplus.domain.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService{
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}