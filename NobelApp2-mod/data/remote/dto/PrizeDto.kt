package com.example.nobelapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrizeDto(
    val id: Int = 0,

    val year: Int = 0,

    @SerialName("awardYear")
    val awardYear: String = "",

    val category: String = "",

    val title: String = "",

    val laureates: List<LaureateDto> = emptyList(),

    val fullName: String = "",

    val motivation: String = "",

    val detailLink: String? = null
)

@Serializable
data class LaureateDto(
    val id: Int = 0,
    val fullName: String = "",
    val motivation: String = ""
)
