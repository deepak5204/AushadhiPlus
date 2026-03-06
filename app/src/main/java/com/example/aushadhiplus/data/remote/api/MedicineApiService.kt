package com.example.aushadhiplus.data.remote.api

import com.example.aushadhiplus.data.remote.dto.MedicineListResponse
import com.example.aushadhiplus.data.remote.dto.MedicineResponse
import com.example.aushadhiplus.domain.model.Medicine
import com.example.aushadhiplus.domain.model.MedicineRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicineApiService {

    //    http://localhost:3000/api/medicines
    @GET("medicines")
    suspend fun getMedicines(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): MedicineListResponse

    @POST("medicines")
    suspend fun addMedicine(
        @Body request: MedicineRequest
    ): Medicine

    @PUT("medicines/{id}")
    suspend fun updateMedicine(
        @Path("id") id: String, @Body request: MedicineRequest
    ): Medicine

    @DELETE("medicines/{id}")
    suspend fun deleteMedicine(
        @Path("id") id: String
    )
}