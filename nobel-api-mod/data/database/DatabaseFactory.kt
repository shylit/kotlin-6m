//---
package com.example.nobelapi.data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://ep-bitter-voice-antrarvd.c-6.us-east-1.aws.neon.tech/neondb?sslmode=require"
            driverClassName = "org.postgresql.Driver"
            username = "neondb_owner"
            password = "npg_18uQFPhXjaCr"

            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(
                UsersTable,
                PrizesTable,
                LaureatesTable,
                UserPrizesTable
            )

            UsersTable.insertIgnore {
                it[username] = "admin"
                it[password] = "1234"
                it[role] = "user"
            }

            if (PrizesTable.selectAll().count() == 0L) {
                val prizeId = PrizesTable.insert {
                    it[awardYear] = 2023
                    it[category] = "peace"
                    it[fullName] = "Nobel Peace Prize 2023"
                    it[motivation] = "For outstanding contribution"
                    it[detailLink] = "https://nobelprize.org"
                }[PrizesTable.id]

                LaureatesTable.insert {
                    it[LaureatesTable.prizeId] = prizeId
                    it[fullName] = "Test Laureate"
                    it[portion] = "1/1"
                    it[motivation] = "Test motivation"
                    it[portraitUrl] = null
                }
            }
        }
    }
}
