//----
package com.example.nobelapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nobelapp.domain.model.Prize
import com.example.nobelapp.presentation.NobelUiState

@Composable
fun PrizeListScreen(
    state: NobelUiState,
    onAddFavoriteClick: (Int) -> Unit,
    onFavoritesClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onFavoritesClick) {
                Text("Избранное")
            }

            Button(onClick = onLogoutClick) {
                Text("Выйти")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Список Нобелевских премий")

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = it)
        }

        LazyColumn {
            items(state.prizes) { prize ->
                PrizeItem(
                    prize = prize,
                    buttonText = "Добавить в избранное",
                    onButtonClick = {
                        onAddFavoriteClick(prize.id)
                    }
                )
            }
        }
    }
}

@Composable
fun PrizeItem(
    prize: Prize,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = "Год: ${prize.awardYear}")
            Text(text = "Категория: ${prize.category}")
            Text(text = "Лауреат: ${prize.fullName}")
            Text(text = "Мотивация: ${prize.motivation}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onButtonClick) {
                Text(buttonText)
            }
        }
    }
}
