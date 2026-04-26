//---
package com.example.nobelapp.domain.usecase

import com.example.nobelapp.domain.repository.NobelRepository

class AddFavoritePrizeUseCase(
    private val repository: NobelRepository
) {
    suspend operator fun invoke(token: String, prizeId: Int) {
        repository.addFavoritePrize(token, prizeId)
    }
}
