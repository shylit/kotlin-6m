//изменили
package com.example.nobelapp.domain.repository

import com.example.nobelapp.domain.model.Prize

interface NobelRepository {

    suspend fun login(username: String, password: String): String

    suspend fun getPrizes(token: String): List<Prize>

    suspend fun getFavoritePrizes(token: String): List<Prize>

    suspend fun addFavoritePrize(token: String, prizeId: Int)

    suspend fun deleteFavoritePrize(token: String, prizeId: Int)
}
