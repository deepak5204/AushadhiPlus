package com.example.aushadhiplus.data.repository

import com.example.aushadhiplus.data.remote.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getUsers() = apiService.getUsers()
}