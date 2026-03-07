package com.example.aushadhiplus.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Medicine(
    val id: String,
    val name: String,
    val manufacturer: String,
    val price: Int,
    val quantity: Int,
    val expiryDate: String,
    val category: String,
    val lowStockThreshold: Int
) : Parcelable