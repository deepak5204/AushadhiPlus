package com.example.aushadhiplus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aushadhiplus.presentation.auth.LoginScreen
import com.example.aushadhiplus.presentation.dashboard.DashboardScreen
import com.example.aushadhiplus.presentation.medicine.MedicineScreen
import com.example.aushadhiplus.presentation.user.UserScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier, startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                })
        }

        composable("main") {
            MainScreen()
        }
    }
}