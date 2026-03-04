package com.example.aushadhiplus.data.remote.dto

data class MedicineResponse(
    val _id: String,
    val name: String,
    val manufacturer: String,
    val price: Int,
    val quantity: Int,
    val expiryDate: String,
    val category: String,
    val lowStockThreshold: Int
)