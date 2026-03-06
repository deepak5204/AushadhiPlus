package com.example.aushadhiplus.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aushadhiplus.core.util.Resource
import com.example.aushadhiplus.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true, error = null
            )

            val result = repository.login(
                _uiState.value.email, _uiState.value.password
            )

            when (result) {
                is Resource.Error<*> -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, error = result.message
                    )
                }

                is Resource.Loading<*> -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true, error = null
                    )
                }

                is Resource.Success<*> -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, isSuccess = true
                    )
                }
            }
        }
    }
}