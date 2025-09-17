package com.example.myjikanapp.Modal

import com.google.gson.annotations.SerializedName

data class IndividualAnimaDatamodal(
    @SerializedName("data") val data: ParticularAnimaData
)

data class ParticularAnimaData(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: ImagesWrapper,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("score") val score: Double?,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("trailer") val trailer: Trailer?
)

data class Genre(
    @SerializedName("name") val name: String
)

data class Trailer(
    @SerializedName("embed_url") val embedUrl: String?,
    @SerializedName("youtube_id") val youtubeId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: TrailerImages?
)

data class TrailerImages(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("small_image_url") val smallImageUrl: String?,
    @SerializedName("medium_image_url") val mediumImageUrl: String?,
    @SerializedName("large_image_url") val largeImageUrl: String?,
    @SerializedName("maximum_image_url") val maximumImageUrl: String?
)
