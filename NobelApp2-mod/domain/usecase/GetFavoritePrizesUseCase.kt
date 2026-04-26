//---
package com.example.nobelapp.domain.usecase

import com.example.nobelapp.domain.model.Prize
import com.example.nobelapp.domain.repository.NobelRepository

class GetFavoritePrizesUseCase(
    private val repository: NobelRepository
) {
    suspend operator fun invoke(token: String): List<Prize> {
        return repository.getFavoritePrizes(token)
    }
}
