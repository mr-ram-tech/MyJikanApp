package com.example.myjikanapp.View

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myjikanapp.Viewmodel.AnimeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun AnimeDetailScreen(viewModel: AnimeViewModel, id: Int, navController: NavController) {
    val state by viewModel.detailState.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadDetail(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.title.ifEmpty { "Details" }) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },

                 modifier = Modifier.statusBarsPadding()
            )
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
            state.errorMessage != null -> Box(Modifier.fillMaxSize().padding(paddingValues)) {
                Text(text = state.errorMessage ?: "Error")
            }
            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                var wantsToPlay by remember(state.trailerEmbedUrl) { mutableStateOf(false) }
                if (state.trailerEmbedUrl != null && !wantsToPlay) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(state.trailerThumbnailUrl ?: state.posterUrl),
                            contentDescription = "Trailer thumbnail",
                            modifier = Modifier.matchParentSize()
                        )
                        FloatingActionButton(
                            onClick = { wantsToPlay = true },
                            modifier = Modifier.align(Alignment.Center),
                            elevation = FloatingActionButtonDefaults.elevation(0.dp)
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
                        }
                    }
                } else if (state.trailerEmbedUrl != null && wantsToPlay) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        if (state.trailerYoutubeId != null) {
                            YouTubePlayerComposable(state.trailerYoutubeId!!)
                        } else {
                            YouTubeIntentPlayer(
                                youtubeId = state.trailerYoutubeId,
                                webFallbackUrl = state.trailerEmbedUrl!!
                            )
                        }
                    }
                } else if (state.posterUrl != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(state.posterUrl),
                            contentDescription = state.title,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                Text(text = state.title, style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(8.dp))
                Text(text = "Episodes: ${state.episodes ?: "?"}")
                Text(text = "Rating: ${state.score ?: "?"}")

                Spacer(Modifier.height(12.dp))
                if (!state.synopsis.isNullOrBlank()) {
                    Text(text = state.synopsis ?: "")
                }

                Spacer(Modifier.height(12.dp))
                if (state.genres.isNotEmpty()) {
                    Text(text = "Genres: ${state.genres.joinToString()} ")
                }

                if (state.cast.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(text = "Main Cast")
                    LazyRow {
                        items(state.cast) { c ->
                            Text(text = c)
                            Spacer(Modifier.width(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun TrailerPlayer(embedUrl: String) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        AndroidView(
            modifier = Modifier.matchParentSize(),
            factory = {
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.cacheMode = WebSettings.LOAD_DEFAULT
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                            loading = true
                        }
                        override fun onPageFinished(view: WebView?, url: String?) {
                            loading = false
                        }
                    }
                    loadUrl(embedUrl)
                }
            },
            update = { it.loadUrl(embedUrl) }
        )
        if (loading) {
            Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun YouTubeIntentPlayer(youtubeId: String?, webFallbackUrl: String) {
    val context = LocalContext.current
    LaunchedEffect(youtubeId, webFallbackUrl) {
        val appIntent = if (youtubeId != null) {
            android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("vnd.youtube:" + youtubeId))
        } else null
        val webIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(webFallbackUrl))
        try {
            if (appIntent != null) context.startActivity(appIntent) else context.startActivity(webIntent)
        } catch (e: Exception) {
            // Fallback to WebView inline
        }
    }
    // Inline fallback WebView
    TrailerPlayer(webFallbackUrl)
}

@Composable
private fun YouTubePlayerComposable(youtubeId: String) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        factory = {
            YouTubePlayerView(context).apply {
                enableAutomaticInitialization = false
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(youtubeId, 0f)
                    }
                })
            }
        },
        update = { view ->
            // no-op
        }
    )
}


