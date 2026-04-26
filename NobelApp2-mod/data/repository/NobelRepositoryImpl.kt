//изменили
package com.example.nobelapp.data.repository

import com.example.nobelapp.data.mapper.toDomain
import com.example.nobelapp.data.remote.NobelApi
import com.example.nobelapp.domain.model.Prize
import com.example.nobelapp.domain.repository.NobelRepository

class NobelRepositoryImpl(
    private val api: NobelApi
) : NobelRepository {

    override suspend fun login(username: String, password: String): String {
        return api.login(username, password).token
    }

    override suspend fun getPrizes(token: String): List<Prize> {
        return api.getPrizes(token).map { it.toDomain() }
    }

    override suspend fun getFavoritePrizes(token: String): List<Prize> {
        return api.getFavoritePrizes(token).map { it.toDomain() }
    }

    override suspend fun addFavoritePrize(token: String, prizeId: Int) {
        api.addFavoritePrize(token, prizeId)
    }

    override suspend fun deleteFavoritePrize(token: String, prizeId: Int) {
        api.deleteFavoritePrize(token, prizeId)
    }
}
