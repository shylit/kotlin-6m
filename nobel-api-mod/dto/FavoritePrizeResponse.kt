//---
package com.example.nobelapi.dto

import kotlinx.serialization.Serializable

@Serializable
data class FavoritePrizeResponse(
    val id: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String?
)
