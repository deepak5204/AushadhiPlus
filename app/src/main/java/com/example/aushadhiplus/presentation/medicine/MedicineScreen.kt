package com.example.aushadhiplus.presentation.medicine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.aushadhiplus.domain.model.Medicine
import com.example.aushadhiplus.presentation.medicine.components.MedicineItem

@Composable
fun MedicineScreen(
    modifier: Modifier = Modifier, viewModel: MedicineViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val uiState = state
    when (uiState) {

        is MedicineUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MedicineUiState.Error -> {
            Text(text = (state as MedicineUiState.Error).message ?: "Something went wrong")
        }

        is MedicineUiState.Success -> {

            val medicines = uiState.data.collectAsLazyPagingItems()

            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(
                    count = medicines.itemCount,
                    key = { index -> medicines[index]?.id ?: index }) { index ->

                    medicines[index]?.let { medicine ->
                        MedicineItem(medicine)
                    }
                }

                when (medicines.loadState.append) {
                    is MedicineUiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    else -> {}
                }
            }

//            val medicines = (uiState as MedicineUiState.Success).data.collectAsLazyPagingItems()
//            LazyColumn(
//                modifier = Modifier
//                    .padding(horizontal = 8.dp)
//            ) {
//
//                items(medicines.itemCount) { index ->
//
//                    medicines[index]?.let { medicine ->
//                        MedicineItem(medicine)
//                    }
//                }
//            }
        }
    }
}

