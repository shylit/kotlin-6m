//---
package com.example.nobelapi.presentation

import com.example.nobelapi.data.database.PrizesTable
import com.example.nobelapi.data.database.UserPrizesTable
import com.example.nobelapi.data.database.UsersTable
import com.example.nobelapi.dto.FavoritePrizeResponse
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.favoriteRoutes() {

    authenticate("auth-jwt") {

        get("/users/me/prizes") {
            val username = call.principal<JWTPrincipal>()
                ?.payload
                ?.getClaim("username")
                ?.asString()

            if (username == null) {
                call.respond(HttpStatusCode.Unauthorized, "No username in token")
                return@get
            }

            val prizes: List<FavoritePrizeResponse> = transaction {
                val userId = UsersTable
                    .selectAll()
                    .where { UsersTable.username eq username }
                    .map { it[UsersTable.id] }
                    .firstOrNull()

                if (userId == null) {
                    emptyList()
                } else {
                    val favoritePrizeIds = UserPrizesTable
                        .selectAll()
                        .where { UserPrizesTable.userId eq userId }
                        .map { it[UserPrizesTable.prizeId] }

                    if (favoritePrizeIds.isEmpty()) {
                        emptyList()
                    } else {
                        PrizesTable
                            .selectAll()
                            .where { PrizesTable.id inList favoritePrizeIds }
                            .map {
                                FavoritePrizeResponse(
                                    id = it[PrizesTable.id],
                                    awardYear = it[PrizesTable.awardYear],
                                    category = it[PrizesTable.category],
                                    fullName = it[PrizesTable.fullName],
                                    motivation = it[PrizesTable.motivation],
                                    detailLink = it[PrizesTable.detailLink]
                                )
                            }
                    }
                }
            }

            call.respond(prizes)
        }

        post("/users/me/prizes/{prizeId}") {
            val username = call.principal<JWTPrincipal>()
                ?.payload
                ?.getClaim("username")
                ?.asString()

            val prizeId = call.parameters["prizeId"]?.toIntOrNull()

            if (username == null || prizeId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data")
                return@post
            }

            val added = transaction {
                val userId = UsersTable
                    .selectAll()
                    .where { UsersTable.username eq username }
                    .map { it[UsersTable.id] }
                    .firstOrNull()

                val prizeExists = PrizesTable
                    .selectAll()
                    .where { PrizesTable.id eq prizeId }
                    .count() > 0

                if (userId == null || !prizeExists) {
                    false
                } else {
                    UserPrizesTable.insertIgnore {
                        it[UserPrizesTable.userId] = userId
                        it[UserPrizesTable.prizeId] = prizeId
                    }
                    true
                }
            }

            if (added) {
                call.respondText("Prize added to favorites")
            } else {
                call.respond(HttpStatusCode.NotFound, "User or prize not found")
            }
        }

        delete("/users/me/prizes/{prizeId}") {
            val username = call.principal<JWTPrincipal>()
                ?.payload
                ?.getClaim("username")
                ?.asString()

            val prizeId = call.parameters["prizeId"]?.toIntOrNull()

            if (username == null || prizeId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data")
                return@delete
            }

            val deletedRows = transaction {
                val userId = UsersTable
                    .selectAll()
                    .where { UsersTable.username eq username }
                    .map { it[UsersTable.id] }
                    .firstOrNull()

                if (userId == null) {
                    0
                } else {
                    UserPrizesTable.deleteWhere {
                        (UserPrizesTable.userId eq userId) and
                                (UserPrizesTable.prizeId eq prizeId)
                    }
                }
            }

            if (deletedRows > 0) {
                call.respondText("Prize removed from favorites")
            } else {
                call.respond(HttpStatusCode.NotFound, "Favorite prize not found")
            }
        }
    }
}
