//---
package com.example.nobelapi.data.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 100).uniqueIndex()
    val password = varchar("password", 100)
    val role = varchar("role", 50)

    override val primaryKey = PrimaryKey(id)
}

object PrizesTable : Table("prizes") {
    val id = integer("id").autoIncrement()
    val awardYear = integer("award_year")
    val category = varchar("category", 100)
    val fullName = varchar("full_name", 255)
    val motivation = text("motivation")
    val detailLink = varchar("detail_link", 500).nullable()

    override val primaryKey = PrimaryKey(id)
}

object LaureatesTable : Table("laureates") {
    val id = integer("id").autoIncrement()
    val prizeId = integer("prize_id").references(PrizesTable.id)
    val fullName = varchar("full_name", 255)
    val portion = varchar("portion", 50)
    val motivation = text("motivation")
    val portraitUrl = varchar("portrait_url", 500).nullable()

    override val primaryKey = PrimaryKey(id)
}

object UserPrizesTable : Table("user_prizes") {
    val userId = integer("user_id").references(UsersTable.id)
    val prizeId = integer("prize_id").references(PrizesTable.id)
    val addedAt = datetime("added_at").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(userId, prizeId)
}
