package com.example.aushadhiplus.data.remote.api

import com.example.aushadhiplus.data.remote.dto.UserDto
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}