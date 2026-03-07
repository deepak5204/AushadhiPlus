package com.example.aushadhiplus.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.aushadhiplus.data.remote.api.MedicineApiService
import com.example.aushadhiplus.data.remote.mapper.toDomain
import com.example.aushadhiplus.domain.model.Medicine

class MedicinePagingSource(
    private val api: MedicineApiService,
    private val search: String
) : PagingSource<Int, Medicine>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Medicine> {
        return try {
            val page = params.key ?: 1

            val response = api.getMedicines(
                search = search, page = page, limit = params.loadSize
            )

            val medicines = response.medicines.map { it.toDomain() }

            LoadResult.Page(
                data = medicines,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (medicines.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {

            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Medicine>): Int? {
        return state.anchorPosition
    }
}