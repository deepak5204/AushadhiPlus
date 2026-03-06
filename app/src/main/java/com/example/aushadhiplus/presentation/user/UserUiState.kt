package com.example.aushadhiplus.presentation.user

import com.example.aushadhiplus.domain.model.User

data class UserUiState(
    val isLoading: Boolean = false, val users: List<User> = emptyList(), val error: String? = null
)