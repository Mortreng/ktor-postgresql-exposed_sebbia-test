package com.sebbia.dataRepository.database

import org.jetbrains.exposed.sql.Database
import java.sql.Driver

class appDatabse(url: String, driver: String, user: String, password: String) {
    private val db = Database.connect(
        "jdbc:postgresql://127.0.0.1:5432/newsfeed", driver = "org.postgresql.Driver",
        user = "postgres", password = "qwerty"
    )

    fun getDb() = db
}