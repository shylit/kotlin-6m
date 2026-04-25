package com.example.nobelapi.plugins

import com.example.nobelapi.security.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuthentication() {

    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtService.realm

            verifier(
                com.auth0.jwt.JWT
                    .require(JwtService.getAlgorithm())
                    .withAudience(JwtService.audience)
                    .withIssuer(JwtService.issuer)
                    .build()
            )

            validate { credential ->
                val username = credential.payload.getClaim("username").asString()

                if (!username.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
