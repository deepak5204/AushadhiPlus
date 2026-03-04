package com.example.aushadhiplus.domain.model

data class MedicineRequest(
    val name: String,
    val price: Double,
    val quantity: Int,
    val expiryDate: String,
    val manufacturer: String
)