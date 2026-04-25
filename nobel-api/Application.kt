package com.example.nobelapi

import com.example.nobelapi.plugins.configureAuthentication
import com.example.nobelapi.plugins.configureRouting
import com.example.nobelapi.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(CallLogging)

    configureSerialization()
    configureAuthentication()
    configureRouting()
}
