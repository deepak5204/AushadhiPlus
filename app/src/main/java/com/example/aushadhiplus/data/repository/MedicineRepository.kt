package com.example.aushadhiplus.data.repository

import com.example.aushadhiplus.data.remote.api.MedicineApiService
import com.example.aushadhiplus.data.remote.mapper.toDomain
import com.example.aushadhiplus.domain.model.Medicine
import com.example.aushadhiplus.domain.model.MedicineRequest
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val apiService: MedicineApiService
) {
    suspend fun getMedicines(): List<Medicine> {
        val response = apiService.getMedicines()

        return response.medicines.map {
            it.toDomain()
        }
    }

    suspend fun addMedicine(request: MedicineRequest) =
        apiService.addMedicine(request)

    suspend fun deleteMedicine(id: String) =
        apiService.deleteMedicine(id)
}