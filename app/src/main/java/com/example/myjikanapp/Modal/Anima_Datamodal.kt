package com.example.myjikanapp.Modal

import com.google.gson.annotations.SerializedName

data class AnimaDatamodal(
    @SerializedName("data") val data: List<AnimaData>
)

data class AnimaData(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: ImagesWrapper,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("score") val score: Double?
)

data class ImagesWrapper(
    @SerializedName("jpg") val jpg: JpgImage
)

data class JpgImage(
    @SerializedName("image_url") val imageUrl: String
)
