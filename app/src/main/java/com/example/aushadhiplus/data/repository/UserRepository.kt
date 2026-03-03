package com.example.aushadhiplus.data.repository

import com.example.aushadhiplus.data.remote.ApiService
import com.example.aushadhiplus.data.remote.mapper.toDomain
import com.example.aushadhiplus.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getUsers(): List<User> {
        return apiService.getUsers().map { it.toDomain() }
    }
}