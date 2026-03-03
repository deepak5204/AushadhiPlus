package com.example.aushadhiplus.data.repository

import com.example.aushadhiplus.core.util.Resource
import com.example.aushadhiplus.data.local.TokenManager
import com.example.aushadhiplus.data.remote.api.AuthApiService
import com.example.aushadhiplus.domain.model.LoginRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Resource<Unit> {

        return try {

            val response = api.login(LoginRequest(email, password))

            tokenManager.saveToken(response.token)

            Resource.Success(Unit)

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login failed")
        }
    }
}