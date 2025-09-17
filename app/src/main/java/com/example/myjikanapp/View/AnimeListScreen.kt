package com.example.myjikanapp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myjikanapp.Viewmodel.AnimeViewModel
import com.example.myjikanapp.roomDB.AnimeEntity

@Composable
fun AnimeGridScreen(viewModel: AnimeViewModel, navController: NavController) {
    val animeList by viewModel.animeList.collectAsState()
    val isLoading by viewModel.isHomeLoading.collectAsState()
    val homeError by viewModel.homeErrorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshAnime()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home",) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                elevation = 4.dp ,
                modifier = Modifier.statusBarsPadding()
            )
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    // Show loader
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                homeError != null && animeList.isEmpty() -> {
                    // Show error message with retry
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = homeError ?: "Unknown Error")
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.refreshAnime() }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    // Show anime list
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(animeList) { anime ->
                            AnimeCard(anime) {
                                navController.navigate("detail/${anime.malId}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeCard(anime: AnimeEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(anime.imageUrl),
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = anime.title)
            Text(text = "Episodes: ${anime.episodes ?: "?"}")
            Text(text = "Rating : ${anime.score ?: "?"}")
        }
    }
}
