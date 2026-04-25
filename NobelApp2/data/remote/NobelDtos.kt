package com.example.nobelapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NobelResponseDto(
    @SerialName("nobelPrizes")
    val nobelPrizes: List<NobelPrizeDto> = emptyList()
)

@Serializable
data class NobelPrizeDto(
    @SerialName("awardYear")
    val awardYear: String = "",

    @SerialName("category")
    val category: TextDto = TextDto(),

    @SerialName("laureates")
    val laureates: List<LaureateDto> = emptyList()
)

@Serializable
data class LaureateDto(
    @SerialName("id")
    val id: String = "",

    @SerialName("fullName")
    val fullName: TextDto? = null,

    @SerialName("motivation")
    val motivation: TextDto? = null,

    @SerialName("birth")
    val birth: BirthDto? = null
)

@Serializable
data class BirthDto(
    @SerialName("place")
    val place: PlaceDto? = null
)

@Serializable
data class PlaceDto(
    @SerialName("city")
    val city: TextDto? = null,

    @SerialName("country")
    val country: TextDto? = null
)

@Serializable
data class TextDto(
    @SerialName("en")
    val en: String = ""
)
