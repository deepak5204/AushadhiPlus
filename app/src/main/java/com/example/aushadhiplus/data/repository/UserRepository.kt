package com.example.aushadhiplus.data.repository

import com.example.aushadhiplus.core.util.Resource
import com.example.aushadhiplus.data.local.TokenManager
import com.example.aushadhiplus.data.remote.api.ApiService
import com.example.aushadhiplus.data.remote.mapper.toDomain
import com.example.aushadhiplus.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,

){
    suspend fun getUsers(): Resource<List<User>> {
        return try{
            val users = apiService.getUsers().map { it.toDomain() }
            Resource.Success(users)
        } catch(e: Exception){
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}