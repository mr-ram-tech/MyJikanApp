package com.example.myjikanapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myjikanapp.View.AnimeGridScreen
import com.example.myjikanapp.View.AnimeDetailScreen
import com.example.myjikanapp.Viewmodel.AnimeViewModel

@Composable
fun AppNavHost(viewModel: AnimeViewModel) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "list") {
        composable("list") { AnimeGridScreen(viewModel, nav) }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            AnimeDetailScreen(viewModel, id, nav)
        }
    }
}
