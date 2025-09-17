package com.example.myjikanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myjikanapp.Api.Api_client

import com.example.myjikanapp.Navigation.AppNavHost
import com.example.myjikanapp.Repository.AnimeRepository
import com.example.myjikanapp.Repository.AnimeViewModelFactory
import com.example.myjikanapp.Viewmodel.AnimeViewModel
import com.example.myjikanapp.roomDB.AnimeDatabase
import com.example.myjikanapp.ui.theme.MyJikanAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AnimeDatabase.getDatabase(applicationContext).animeDao()
        val repository = AnimeRepository(Api_client, dao)
        enableEdgeToEdge()
        setContent {
            MyJikanAppTheme {
                val viewModel: AnimeViewModel = viewModel(
                    factory = AnimeViewModelFactory(repository)
                )
                AppNavHost(viewModel)
            }
        }
    }
}

