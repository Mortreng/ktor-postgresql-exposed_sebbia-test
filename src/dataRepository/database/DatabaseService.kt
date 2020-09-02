package com.sebbia.dataRepository.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

//Pretty self-explanatory, an object, which uses the "table" class from exposed library
object CategoriesTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = text("name")
    override val primaryKey = PrimaryKey(id)
}

object NewsTable: Table(){
    val id: Column<Int> = integer("id").autoIncrement()
    val title: Column<String> = text ("name")
    val date : Column<String> = varchar ("date", 30)
    val shortDescription : Column<String> = text("Short description")
    val fullDescription : Column<String> = text("Full description")
    val categoryId : Column<Int> = reference("id", CategoriesTable.id)
    override val primaryKey = PrimaryKey(id)
}
interface DatabaseService {

    fun <T> runWithTransaction(block: Transaction.() -> T): T
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

    override fun <T> runWithTransaction(block: Transaction.() -> T): T {
        return transaction {
            block()
        }
    }
}