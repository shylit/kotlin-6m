package com.example.nobelapi.presentation

import com.example.nobelapi.domain.usecase.GetAllPrizesUseCase
import com.example.nobelapi.domain.usecase.GetPrizeDetailUseCase
import com.example.nobelapi.domain.usecase.GetPrizeLaureatesUseCase
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prizeRoutes(
    getAllPrizesUseCase: GetAllPrizesUseCase,
    getPrizeDetailUseCase: GetPrizeDetailUseCase,
    getPrizeLaureatesUseCase: GetPrizeLaureatesUseCase
) {
    get("/prizes") {
        call.respond(getAllPrizesUseCase())
    }

    get("/prizes/{year}/{category}") {
        val year = call.parameters["year"]?.toIntOrNull()
        val category = call.parameters["category"]

        if (year == null || category == null) {
            call.respond(HttpStatusCode.BadRequest, "Неверные параметры")
            return@get
        }

        val prize = getPrizeDetailUseCase(year, category)

        if (prize == null) {
            call.respond(HttpStatusCode.NotFound, "Премия не найдена")
        } else {
            call.respond(prize)
        }
    }

    get("/prizes/{year}/{category}/laureates") {
        val year = call.parameters["year"]?.toIntOrNull()
        val category = call.parameters["category"]

        if (year == null || category == null) {
            call.respond(HttpStatusCode.BadRequest, "Неверные параметры")
            return@get
        }

        val laureates = getPrizeLaureatesUseCase(year, category)

        if (laureates == null) {
            call.respond(HttpStatusCode.NotFound, "Лауреаты не найдены")
        } else {
            call.respond(laureates)
        }
    }
}
