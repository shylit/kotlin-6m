package com.example.authapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponseDto(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val image: String = "",

    @SerialName("accessToken")
    val token: String = ""
)

@Serializable
data class UsersResponseDto(
    val users: List<UserDto> = emptyList()
)

@Serializable
data class UserDto(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val image: String = ""
)
