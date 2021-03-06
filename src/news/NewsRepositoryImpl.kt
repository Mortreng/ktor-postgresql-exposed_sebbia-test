package com.sebbia.news

import com.sebbia.categories.CategoriesTable
import com.sebbia.dataRepository.database.DatabaseService
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select

object NewsTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val title: Column<String> = text("title")
    val date: Column<String> = varchar("date", 30)
    val shortDescription: Column<String> = text("short_description")
    val fullDescription: Column<String> = text("full_description")
    val categoryId: Column<Int> = reference("category_id", CategoriesTable.id)
    override val primaryKey = PrimaryKey(id)
}

class NewsRepositoryImpl(
    val databaseService: DatabaseService
) : NewsRepository {
    override fun getNewsByCategory(id: Int, page: Int, limit: Int): List<Article> {
        return databaseService.runWithTransaction {
            NewsTable.select { (NewsTable.categoryId eq id) }.limit(limit, offset = page * limit.toLong()).map {
                Article(
                    it[NewsTable.id],
                    it[NewsTable.title],
                    it[NewsTable.date],
                    it[NewsTable.shortDescription],
                    it[NewsTable.fullDescription],
                    it[NewsTable.categoryId]
                )
            }
        }
    }

    override fun getFullArticle(id: Int): Article {
        return databaseService.runWithTransaction {
            NewsTable.select { NewsTable.id eq id }.limit(1).map {
                Article(
                    it[NewsTable.id],
                    it[NewsTable.title],
                    it[NewsTable.date],
                    it[NewsTable.shortDescription],
                    it[NewsTable.fullDescription],
                    it[NewsTable.categoryId]
                )
            }.first()
        }
    }
}




