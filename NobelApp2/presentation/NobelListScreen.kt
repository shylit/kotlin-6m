package com.example.nobelapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelListScreen(
    viewModel: NobelViewModel,
    onItemClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var year by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("All") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "All",
        "phy",
        "che",
        "med",
        "lit",
        "pea",
        "eco"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Нобелевские лауреаты") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Год") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Категория") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                category = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.loadPrizes(
                        year = year.takeIf { it.isNotBlank() },
                        category = category
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Применить фильтр")
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (val state = uiState) {
                is NobelUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is NobelUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                viewModel.loadPrizes(
                                    year = year.takeIf { it.isNotBlank() },
                                    category = category
                                )
                            }
                        ) {
                            Text("Повторить")
                        }
                    }
                }

                is NobelUiState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.items) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemClick(item.id) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text("Год: ${item.year}")
                                    Text("Категория: ${item.category}")
                                    Text("Лауреат: ${item.fullName}")
                                    Text("Описание: ${item.motivationShort}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
