//---
package com.example.nobelapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nobelapp.presentation.NobelUiState

@Composable
fun FavoritePrizesScreen(
    state: NobelUiState,
    onDeleteFavoriteClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = onBackClick) {
            Text("Назад")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Избранные премии")

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = it)
        }

        LazyColumn {
            items(state.favoritePrizes) { prize ->
                PrizeItem(
                    prize = prize,
                    buttonText = "Удалить из избранного",
                    onButtonClick = {
                        onDeleteFavoriteClick(prize.id)
                    }
                )
            }
        }
    }
}
