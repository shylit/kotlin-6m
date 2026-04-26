//---
package com.example.nobelapi.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureOpenAPI() {
    routing {

        get("/openapi.json") {
            call.respondText(
                text = """
                {
                  "openapi": "3.0.0",
                  "info": {
                    "title": "Nobel Prize API",
                    "version": "1.0.0",
                    "description": "API для работы с Нобелевскими премиями, пользователями и избранными премиями"
                  },
                  "paths": {
                    "/auth/login": {
                      "post": {
                        "summary": "Авторизация пользователя",
                        "description": "Принимает username и password, возвращает JWT-токен"
                      }
                    },
                    "/prizes": {
                      "get": {
                        "summary": "Получить список премий",
                        "description": "Возвращает список Нобелевских премий"
                      }
                    },
                    "/users/me": {
                      "get": {
                        "summary": "Профиль пользователя",
                        "description": "Возвращает информацию о текущем пользователе по JWT-токену"
                      }
                    },
                    "/users/me/prizes": {
                      "get": {
                        "summary": "Избранные премии",
                        "description": "Возвращает список избранных премий пользователя"
                      }
                    },
                    "/users/me/prizes/{prizeId}": {
                      "post": {
                        "summary": "Добавить премию в избранное"
                      },
                      "delete": {
                        "summary": "Удалить премию из избранного"
                      }
                    }
                  }
                }
                """.trimIndent(),
                contentType = ContentType.Application.Json
            )
        }

        get("/docs") {
            call.respondText(
                text = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <title>Nobel Prize API Docs</title>
        </head>
        <body>
            <h1>Nobel Prize API</h1>
            <p>Документация серверного API.</p>

            <h2>Авторизация</h2>
            <p><b>POST /auth/login</b> — авторизация пользователя, возвращает JWT-токен.</p>

            <h2>Премии</h2>
            <p><b>GET /prizes</b> — список премий.</p>

            <h2>Пользователь</h2>
            <p><b>GET /users/me</b> — профиль текущего пользователя.</p>

            <h2>Избранные премии</h2>
            <p><b>GET /users/me/prizes</b> — список избранных премий.</p>
            <p><b>POST /users/me/prizes/{prizeId}</b> — добавить премию в избранное.</p>
            <p><b>DELETE /users/me/prizes/{prizeId}</b> — удалить премию из избранного.</p>

            <h2>OpenAPI JSON</h2>
            <p><a href="/openapi.json">Открыть /openapi.json</a></p>
        </body>
        </html>
        """.trimIndent(),
                contentType = ContentType.Text.Html
            )
        }
    }
}
