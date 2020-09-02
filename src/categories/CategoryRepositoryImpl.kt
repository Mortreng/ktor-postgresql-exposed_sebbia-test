package com.sebbia.categories

import com.sebbia.dataRepository.database.DatabaseService
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll

//Pretty self-explanatory, an object, which uses the "table" class from exposed library
object CategoriesTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = text("name")
    override val primaryKey = PrimaryKey(id)
}

class CategoryRepositoryImpl(
    val databaseService: DatabaseService
) :CategoryRepository {

    override fun getList(): List<Category> {
        // Selects all the data from the table named "Categories" and then returns a list of "Category"
        // which are constructed according to the columns of the table
        return databaseService.runWithTransaction {
             CategoriesTable.selectAll().map {
                Category( it[CategoriesTable.id],it[CategoriesTable.name])
            }
        }
    }

    override fun insert(category: Category): Category {
        TODO("Not yet implemented")
    }
}