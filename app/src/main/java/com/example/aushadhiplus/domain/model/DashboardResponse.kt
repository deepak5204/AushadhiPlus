package com.example.aushadhiplus.domain.model

data class DashboardResponse(
    val totalMedicines: Int,
    val lowStockCount: Int,
    val expiringSoonCount: Int,
    val totalInventoryValue: Double
)