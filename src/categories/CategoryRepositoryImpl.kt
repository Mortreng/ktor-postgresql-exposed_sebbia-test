package com.sebbia.categories

import com.sebbia.dataRepository.database.CategoriesTable
import com.sebbia.dataRepository.database.DatabaseService
import org.jetbrains.exposed.sql.selectAll

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