package com.example.myjikanapp.Modal

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("data") val data: List<CharacterEdge>
)

data class CharacterEdge(
    @SerializedName("character") val character: Character,
    @SerializedName("role") val role: String
)

data class Character(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: ImagesWrapper
)


