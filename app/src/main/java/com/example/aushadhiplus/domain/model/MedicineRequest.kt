package com.example.aushadhiplus.domain.model

data class MedicineRequest(
    val name: String,
    val manufacturer: String,
    val price: Int,
    val quantity: Int,
    val expiryDate: String,
    val category: String,
    val lowStockThreshold: Int ? = 5
)