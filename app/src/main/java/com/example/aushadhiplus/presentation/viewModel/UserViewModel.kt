package com.example.aushadhiplus.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aushadhiplus.data.repository.UserRepository
import com.example.aushadhiplus.presentation.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState : StateFlow<UserUiState> = _uiState

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState(isLoading = true)
            try {
                val result = repository.getUsers()
                _uiState.value = UserUiState(
                    users = result,
                    isLoading = false
                )
                println(result)

            } catch(e: Exception) {
                _uiState.value = UserUiState(
                    error = e.message,
                    isLoading = false
                )
                println("{$e getting error while fetching user list}")
            }
        }
    }


}