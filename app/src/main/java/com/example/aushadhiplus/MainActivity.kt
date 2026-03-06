package com.example.aushadhiplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.aushadhiplus.presentation.auth.AuthState
import com.example.aushadhiplus.presentation.auth.LoginScreen
import com.example.aushadhiplus.presentation.navigation.AppNavGraph
import com.example.aushadhiplus.presentation.navigation.MainScreen
import com.example.aushadhiplus.presentation.user.UserScreen
import com.example.aushadhiplus.presentation.user.UserViewModel
import com.example.aushadhiplus.presentation.viewModel.AppViewModel
import com.example.aushadhiplus.ui.theme.AushadhiPlusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AppViewModel = hiltViewModel()
            val authState by viewModel.authState.collectAsState()

            AushadhiPlusTheme {

                when (authState) {
                    AuthState.Authenticated -> {
                        MainScreen()
                    }

                    AuthState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    AuthState.Unauthenticated -> {
                        AppNavGraph(
                            startDestination = "login",
                        )
                    }
                }
            }
        }
    }
}

