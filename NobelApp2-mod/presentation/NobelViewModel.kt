//изменили
package com.example.nobelapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nobelapp.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NobelViewModel : ViewModel() {

    private val _state = MutableStateFlow(NobelUiState())
    val state: StateFlow<NobelUiState> = _state

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = null
                )

                val token = AppModule.loginUseCase(username, password)

                _state.value = _state.value.copy(
                    token = token,
                    isLoading = false
                )

                loadPrizes()

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка авторизации: ${e.message}"
                )
            }
        }
    }

    fun loadPrizes() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = null
                )

                val token = _state.value.token ?: return@launch
                val prizes = AppModule.getPrizesUseCase(token)

                _state.value = _state.value.copy(
                    prizes = prizes,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки премий: ${e.message}"
                )
            }
        }
    }

    fun loadFavoritePrizes() {
        val token = _state.value.token ?: return

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = null
                )

                val favoritePrizes = AppModule.getFavoritePrizesUseCase(token)

                _state.value = _state.value.copy(
                    favoritePrizes = favoritePrizes,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки избранного: ${e.message}"
                )
            }
        }
    }

    fun addFavoritePrize(prizeId: Int) {
        val token = _state.value.token ?: return

        viewModelScope.launch {
            try {
                AppModule.addFavoritePrizeUseCase(token, prizeId)
                loadFavoritePrizes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Ошибка добавления в избранное: ${e.message}"
                )
            }
        }
    }

    fun deleteFavoritePrize(prizeId: Int) {
        val token = _state.value.token ?: return

        viewModelScope.launch {
            try {
                AppModule.deleteFavoritePrizeUseCase(token, prizeId)
                loadFavoritePrizes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Ошибка удаления из избранного: ${e.message}"
                )
            }
        }
    }

    fun logout() {
        _state.value = NobelUiState()
    }
}
