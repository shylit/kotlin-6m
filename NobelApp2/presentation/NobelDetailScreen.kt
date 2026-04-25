package com.example.nobelapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nobelapp.domain.model.NobelPrizeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelDetailScreen(
    item: NobelPrizeItem,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (item.portraitUrl != null) {
                AsyncImage(
                    model = item.portraitUrl,
                    contentDescription = item.fullName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            Text("Полное имя: ${item.fullName}")
            Text("Год: ${item.year}")
            Text("Категория: ${item.category}")
            Text("Описание: ${item.motivationFull}")
            Text("Страна: ${item.birthCountry}")
            Text("Место рождения: ${item.birthPlace}")
        }
    }
}
