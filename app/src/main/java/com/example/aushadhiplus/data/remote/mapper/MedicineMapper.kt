package com.example.aushadhiplus.data.remote.mapper

import com.example.aushadhiplus.data.remote.dto.MedicineResponse
import com.example.aushadhiplus.domain.model.Medicine

fun MedicineResponse.toDomain(): Medicine {
    return Medicine(
        id = _id,
        name = name,
        manufacturer = manufacturer,
        price = price,
        quantity = quantity,
        expiryDate = expiryDate,
        category = category,
        lowStockThreshold = lowStockThreshold
    )
}