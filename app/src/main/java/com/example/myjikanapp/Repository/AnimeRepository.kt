package com.example.myjikanapp.Repository

import com.example.myjikanapp.Api.Api_client
import com.example.myjikanapp.Api.JikanApi
import com.example.myjikanapp.Modal.CharactersResponse
import com.example.myjikanapp.Modal.IndividualAnimaDatamodal
import com.example.myjikanapp.roomDB.AnimeDao
import com.example.myjikanapp.roomDB.AnimeEntity
import kotlinx.coroutines.flow.Flow

class AnimeRepository(
    private val api: Api_client,
    private val dao: AnimeDao
) {
    fun getTopAnime(): Flow<List<AnimeEntity>> {
        return dao.getAllAnime()
    }

    suspend fun refreshAnime() {
        val response = api.apiService.getTopAnime()
        val animeList = response.data.map { anime ->
            AnimeEntity(
                malId = anime.malId,
                title = anime.title,
                imageUrl = anime.images.jpg.imageUrl,
                episodes = anime.episodes,
                score = anime.score
            )
        }
        dao.clearAll()
        dao.insertAll(animeList)
    }

    suspend fun fetchAnimeDetail(id: Int): IndividualAnimaDatamodal {
        return api.apiService.getAnimeDetail(id)
    }

    suspend fun fetchAnimeCharacters(id: Int): List<String> {
        val resp: CharactersResponse = api.apiService.getAnimeCharacters(id)
        // return top few names as main cast
        return resp.data.take(10).map { it.character.name }
    }
}
