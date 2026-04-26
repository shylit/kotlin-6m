//---
package com.example.nobelapi.presentation

import com.example.nobelapi.data.database.UsersTable
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.userRoutes() {

    authenticate("auth-jwt") {

        get("/users/me") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.payload?.getClaim("username")?.asString()

            if (username == null) {
                call.respond("No username in token")
                return@get
            }

            val user = transaction {
                UsersTable
                    .selectAll()
                    .where { UsersTable.username eq username }
                    .map {
                        mapOf(
                            "id" to it[UsersTable.id],
                            "username" to it[UsersTable.username],
                            "role" to it[UsersTable.role]
                        )
                    }
                    .firstOrNull()
            }

            call.respond(user ?: "User not found")
        }
    }
}
