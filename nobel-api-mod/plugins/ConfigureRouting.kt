package com.example.nobelapi.plugins

import com.example.nobelapi.data.NobelPrizeRepositoryImpl
import com.example.nobelapi.domain.usecase.GetAllPrizesUseCase
import com.example.nobelapi.domain.usecase.GetPrizeDetailUseCase
import com.example.nobelapi.domain.usecase.GetPrizeLaureatesUseCase
import com.example.nobelapi.presentation.authRoutes
import com.example.nobelapi.presentation.prizeRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.nobelapi.presentation.userRoutes
import com.example.nobelapi.presentation.favoriteRoutes

fun Application.configureRouting() {
    val repository = NobelPrizeRepositoryImpl()

    val getAllPrizesUseCase = GetAllPrizesUseCase(repository)
    val getPrizeDetailUseCase = GetPrizeDetailUseCase(repository)
    val getPrizeLaureatesUseCase = GetPrizeLaureatesUseCase(repository)

    routing {
        get("/") {
            call.respondText("Nobel Prize API is running")
        }

        userRoutes()/--
        favoriteRoutes()/--

        authRoutes()

        authenticate("auth-jwt") {
            prizeRoutes(
                getAllPrizesUseCase,
                getPrizeDetailUseCase,
                getPrizeLaureatesUseCase
            )
        }
    }
}
