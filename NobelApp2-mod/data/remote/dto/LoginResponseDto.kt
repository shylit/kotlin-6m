//----
package com.example.nobelapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
