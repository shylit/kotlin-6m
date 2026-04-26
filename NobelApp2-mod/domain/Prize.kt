//---
package com.example.nobelapp.domain.model

data class Prize(
    val id: Int,
    val awardYear: String,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String?,
    val portraitUrl: String?
)
