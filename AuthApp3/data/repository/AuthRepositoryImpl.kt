package com.example.authapp.data.repository

import com.example.authapp.data.local.TokenDataStore
import com.example.authapp.data.remote.AuthApiService
import com.example.authapp.data.remote.LoginRequestDto
import com.example.authapp.data.remote.UserDto
import com.example.authapp.domain.model.AppUser
import com.example.authapp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun login(username: String, password: String) {
        val response = api.login(
            LoginRequestDto(
                username = username,
                password = password
            )
        )

        tokenDataStore.saveToken(response.token)
    }

    override suspend fun getUsers(): List<AppUser> {
        val token = tokenDataStore.getToken()
            ?: throw Exception("Токен не найден")

        return api.getUsers(token).users.map { it.toDomain() }
    }

    override suspend fun getUserById(id: Int): AppUser {
        val token = tokenDataStore.getToken()
            ?: throw Exception("Токен не найден")

        return api.getUserById(id, token).toDomain()
    }

    override suspend fun logout() {
        tokenDataStore.clearToken()
    }
}

private fun UserDto.toDomain(): AppUser {
    return AppUser(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username,
        email = email,
        image = image
    )
}
