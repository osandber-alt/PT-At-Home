package com.example.labb3

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ptathome.TouchGLSurfaceView
import com.example.ptathome.ui.screens.HomeScreen
import com.example.ptathome.ui.screens.VideoScreen
import com.example.ptathome.ui.screens.WikipediaScreen
import com.example.ptathome.ui.viewmodel.ViewModel

@Composable
fun PtAtHomeApp(viewModel: ViewModel = hiltViewModel()){
    val navController = rememberNavController()

    // Set up the navigation host
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController, viewModel)
        }
        composable(Screen.VideoScreen.route) {
            VideoScreen(navController, viewModel)
        }

        composable(Screen.WikipediaScreen.route) {
            WikipediaScreen(navController, viewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object VideoScreen : Screen("video")
    object WikipediaScreen : Screen("wikipedia")
}