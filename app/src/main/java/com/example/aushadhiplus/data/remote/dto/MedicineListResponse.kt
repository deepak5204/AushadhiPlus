package com.example.aushadhiplus.data.remote.dto

data class MedicineListResponse(
    val medicines: List<MedicineResponse>,
    val totalPages: Int,
    val currentPage: Int
)