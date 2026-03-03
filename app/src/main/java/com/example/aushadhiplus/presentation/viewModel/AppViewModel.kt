package com.example.aushadhiplus.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aushadhiplus.data.local.TokenManager
import com.example.aushadhiplus.presentation.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _authState =
        MutableStateFlow<AuthState>(AuthState.Loading)

    val authState: StateFlow<AuthState> = _authState

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus(){
        viewModelScope.launch {
            tokenManager.token.collect { token ->
                _authState.value = if(token.isNullOrEmpty()){
                    AuthState.Unauthenticated
                } else {
                    AuthState.Authenticated
                }
            }
        }
    }
}