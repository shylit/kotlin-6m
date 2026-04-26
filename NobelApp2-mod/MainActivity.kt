//изменили
package com.example.nobelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import com.example.nobelapp.presentation.NobelViewModel
import com.example.nobelapp.presentation.screens.FavoritePrizesScreen
import com.example.nobelapp.presentation.screens.LoginScreen
import com.example.nobelapp.presentation.screens.PrizeListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: NobelViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            var currentScreen = androidx.compose.runtime.remember {
                androidx.compose.runtime.mutableStateOf("login")
            }

            LaunchedEffect(state.token) {
                if (state.token != null) {
                    currentScreen.value = "prizes"
                }
            }

            when (currentScreen.value) {
                "login" -> {
                    LoginScreen(
                        state = state,
                        onLoginClick = { username, password ->
                            viewModel.login(username, password)
                        }
                    )
                }

                "prizes" -> {
                    PrizeListScreen(
                        state = state,
                        onAddFavoriteClick = { prizeId ->
                            viewModel.addFavoritePrize(prizeId)
                        },
                        onFavoritesClick = {
                            viewModel.loadFavoritePrizes()
                            currentScreen.value = "favorites"
                        },
                        onLogoutClick = {
                            viewModel.logout()
                            currentScreen.value = "login"
                        }
                    )
                }

                "favorites" -> {
                    FavoritePrizesScreen(
                        state = state,
                        onDeleteFavoriteClick = { prizeId ->
                            viewModel.deleteFavoritePrize(prizeId)
                        },
                        onBackClick = {
                            currentScreen.value = "prizes"
                        }
                    )
                }
            }
        }
    }
}
