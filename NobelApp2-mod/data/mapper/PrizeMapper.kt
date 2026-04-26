package com.example.nobelapp.data.mapper

import com.example.nobelapp.data.remote.dto.PrizeDto
import com.example.nobelapp.domain.model.Prize

fun PrizeDto.toDomain(): Prize {
    val firstLaureate = laureates.firstOrNull()

    return Prize(
        id = id,
        awardYear = if (year != 0) year.toString() else awardYear,
        category = category,
        fullName = firstLaureate?.fullName ?: fullName.ifBlank { title },
        motivation = firstLaureate?.motivation ?: motivation,
        detailLink = detailLink,
        portraitUrl = null
    )
}
