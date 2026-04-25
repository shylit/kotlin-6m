package com.example.nobelapi.presentation

import com.example.nobelapi.dto.LoginRequest
import com.example.nobelapi.dto.LoginResponse
import com.example.nobelapi.security.JwtService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    post("/auth/login") {
        val request = call.receive<LoginRequest>()

        if (request.username == "admin" && request.password == "1234") {
            val token = JwtService.generateToken(request.username)
            call.respond(LoginResponse(token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Неверный логин или пароль")
        }
    }
}
