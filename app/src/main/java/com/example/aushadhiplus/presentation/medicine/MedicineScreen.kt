package com.example.aushadhiplus.presentation.medicine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.aushadhiplus.domain.model.Medicine
import com.example.aushadhiplus.presentation.medicine.components.MedicineItem

@Composable
fun MedicineScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel(),
    onAddMedicineClick: () -> Unit = {},
    onEditClick: (Medicine) -> Unit = {}

) {
    val state by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchMedicines()
    }
    var medicineToDelete by remember { mutableStateOf<Medicine?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddMedicineClick() },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(16.dp) // Professional rounded look
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add Medicine"
                )
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
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
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                viewModel.onSearchQueryChanged(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            placeholder = { Text("Search medicine...") },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        val medicines = uiState.data.collectAsLazyPagingItems()

                        LazyColumn(
                            modifier = Modifier,
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            items(
                                count = medicines.itemCount,
                                key = { index -> medicines[index]?.id ?: index }) { index ->

                                medicines[index]?.let { medicine ->
                                    MedicineItem(medicine, onEditClick = { medicine ->
                                        onEditClick(medicine)
                                    }, onDeleteClick = { medicine ->
                                        medicineToDelete = medicine
                                    })
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
                    }

                    medicineToDelete?.let { medicine ->
                        AlertDialog(onDismissRequest = {
                            medicineToDelete = null
                        }, title = {
                            Text("Delete Medicine")
                        }, text = {
                            Text("Are you sure you want to delete ${medicine.name}?")
                        }, confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.deleteMedicine(medicine.id)
                                    medicineToDelete = null
                                }) {
                                Text("Delete")
                            }
                        }, dismissButton = {
                            TextButton(
                                onClick = {
                                    medicineToDelete = null
                                }) {
                                Text("Cancel")
                            }
                        })
                    }
                }
            }
        }
    }
}

