package com.sebbia.dataRepository.database

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface DatabaseService {

    fun <T> runWithTransaction(block: Transaction.() -> T): T
    fun  databaseMigration()
}

// Here is the class that initialises an actual connection to our database and feeds the data to our classes and functions

class DatabaseServiceImpl(
    url: String,
    driver: String,
    user: String,
    password: String
) : DatabaseService {
    // Инициализировать соединение на старте
    init {
        Database.connect(
            url,
            driver,
            user,
            password
        )
    }

    private val flyway = Flyway.configure().dataSource(url, user, password).load()

    override fun <T> runWithTransaction(block: Transaction.() -> T): T {
        return transaction {
            block()
        }
    }

    override fun databaseMigration() {
        flyway.migrate()
    }
}