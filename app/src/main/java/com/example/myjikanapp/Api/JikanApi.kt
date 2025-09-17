package com.example.myjikanapp.Api
import com.example.myjikanapp.Modal.AnimaDatamodal
import com.example.myjikanapp.Modal.CharactersResponse
import com.example.myjikanapp.Modal.IndividualAnimaDatamodal
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int = 1): AnimaDatamodal

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") animeId: Int): IndividualAnimaDatamodal

    // Characters for an anime (for Main Cast)
    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(@Path("id") animeId: Int): CharactersResponse

}