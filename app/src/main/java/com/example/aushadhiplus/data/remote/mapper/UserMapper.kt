package com.example.aushadhiplus.data.remote.mapper

import com.example.aushadhiplus.data.remote.dto.UserDto
import com.example.aushadhiplus.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = _id,
        name = name,
        email = email,
        role = role
    )
}