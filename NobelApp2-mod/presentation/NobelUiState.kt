//изменили
package com.example.nobelapp.presentation

import com.example.nobelapp.domain.model.Prize

data class NobelUiState(
    val token: String? = null,
    val prizes: List<Prize> = emptyList(),
    val favoritePrizes: List<Prize> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
