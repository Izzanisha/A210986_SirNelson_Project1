package com.example.a210986_sirnelson_lab1

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: TrashViewModel) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Home Screen
        composable("home") {
            TrashTrackHome(navController, viewModel)
        }
        // Schedule Screen
        composable("schedule") {
            ScheduleScreen(viewModel)
        }

        // Report Screen
        composable("report") {
            ReportScreen(viewModel)
        }

        // Profile Screen
        composable("profile") {
            ProfileScreen(viewModel)
        }
    }
}