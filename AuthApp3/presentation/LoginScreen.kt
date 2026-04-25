package com.example.authapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    isLoading: Boolean,
    errorMessage: String?,
    onLoginClick: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("emilys") }
    var password by remember { mutableStateOf("emilyspass") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Вход") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    onLoginClick(username, password)
                },
                enabled = !isLoading,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Войти")
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
