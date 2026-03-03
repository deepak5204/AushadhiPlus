package com.example.aushadhiplus.data.remote.dto


// represent backend contract
data class UserDto(
    val _id: String,
    val name: String,
    val email: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)