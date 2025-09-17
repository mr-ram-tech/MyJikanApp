package com.example.myjikanapp.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjikanapp.Repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    val animeList = repository.getTopAnime()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val isHomeLoading = MutableStateFlow(false)
    val homeErrorMessage = MutableStateFlow<String?>(null)

    fun refreshAnime() {
        viewModelScope.launch {
            try {
                isHomeLoading.value = true
                homeErrorMessage.value = null
                repository.refreshAnime()
            } catch (e: Exception) {
                e.printStackTrace()
                homeErrorMessage.value = e.message ?: "Failed to refresh"
            } finally {
                isHomeLoading.value = false
            }
        }
    }

    data class DetailUiState(
        val isLoading: Boolean = false,
        val title: String = "",
        val posterUrl: String? = null,
        val trailerEmbedUrl: String? = null,
        val trailerThumbnailUrl: String? = null,
        val trailerYoutubeId: String? = null,
        val trailerUrl: String? = null,
        val synopsis: String? = null,
        val genres: List<String> = emptyList(),
        val episodes: Int? = null,
        val score: Double? = null,
        val cast: List<String> = emptyList(),
        val errorMessage: String? = null
    )

    val detailState = MutableStateFlow(DetailUiState())

    fun loadDetail(id: Int) {
        viewModelScope.launch {
            detailState.value = detailState.value.copy(isLoading = true, errorMessage = null)
            try {
                val detail = repository.fetchAnimeDetail(id)
                val characters = repository.fetchAnimeCharacters(id)
                detailState.value = DetailUiState(
                    isLoading = false,
                    title = detail.data.title,
                    posterUrl = detail.data.images.jpg.imageUrl,
                    trailerEmbedUrl = detail.data.trailer?.embedUrl,
                    trailerYoutubeId = detail.data.trailer?.youtubeId,
                    trailerUrl = detail.data.trailer?.url,
                    trailerThumbnailUrl = detail.data.trailer?.images?.maximumImageUrl
                        ?: detail.data.trailer?.images?.largeImageUrl
                        ?: detail.data.trailer?.images?.mediumImageUrl
                        ?: detail.data.trailer?.images?.smallImageUrl
                        ?: detail.data.trailer?.images?.imageUrl,
                    synopsis = detail.data.synopsis,
                    genres = detail.data.genres.map { it.name },
                    episodes = detail.data.episodes,
                    score = detail.data.score,
                    cast = characters
                )
            } catch (e: Exception) {
                detailState.value = DetailUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load details"
                )
            }
        }
    }
}
