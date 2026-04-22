package com.example.a210986_sirnelson_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a210986_sirnelson_lab1.ui.theme.A210986_SirNelson_Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            A210986_SirNelson_Lab1Theme {
                // Create NavController and lifecycle-aware ViewModel
                val navController = rememberNavController()
                val viewModel: TrashViewModel = viewModel()

                // Root surface with themed background + content colour
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    // Call the navigation graph
                    AppNavGraph(navController, viewModel)
                }
            }
        }
    }
}