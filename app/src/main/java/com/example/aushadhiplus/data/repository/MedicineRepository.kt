package com.example.aushadhiplus.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.aushadhiplus.data.paging.MedicinePagingSource
import com.example.aushadhiplus.data.remote.api.MedicineApiService
import com.example.aushadhiplus.data.remote.mapper.toDomain
import com.example.aushadhiplus.domain.model.Medicine
import com.example.aushadhiplus.domain.model.MedicineRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val apiService: MedicineApiService
) {
    fun getMedicines(): Flow<PagingData<Medicine>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20
            ), pagingSourceFactory = {
                MedicinePagingSource(apiService)
            }).flow
    }

    suspend fun addMedicine(request: MedicineRequest) = apiService.addMedicine(request)

    suspend fun deleteMedicine(id: String) = apiService.deleteMedicine(id)
}