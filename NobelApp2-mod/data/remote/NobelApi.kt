//---
package com.example.nobelapp.data.remote

import com.example.nobelapp.data.remote.dto.LoginRequestDto
import com.example.nobelapp.data.remote.dto.LoginResponseDto
import com.example.nobelapp.data.remote.dto.PrizeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class NobelApi(
    private val client: HttpClient
) {
    private val baseUrl = "http://10.0.2.2:8080"

    suspend fun login(username: String, password: String): LoginResponseDto {
        return client.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username, password))
        }.body()
    }

    suspend fun getPrizes(token: String): List<PrizeDto> {
        val response: HttpResponse = client.get("$baseUrl/prizes") {
            header("Authorization", "Bearer $token")
        }

        val text = response.bodyAsText()

        println("RAW PRIZES JSON LENGTH = ${text.length}")
        text.chunked(500).forEachIndexed { index, part ->
            println("RAW PART $index = $part")
        }

        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString(
            ListSerializer(PrizeDto.serializer()),
            text
        )
    }
    suspend fun getFavoritePrizes(token: String): List<PrizeDto> {
        return client.get("$baseUrl/users/me/prizes") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun addFavoritePrize(token: String, prizeId: Int) {
        client.post("$baseUrl/users/me/prizes/$prizeId") {
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun deleteFavoritePrize(token: String, prizeId: Int) {
        client.delete("$baseUrl/users/me/prizes/$prizeId") {
            header("Authorization", "Bearer $token")
        }
    }
}
