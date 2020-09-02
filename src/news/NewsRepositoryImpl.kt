package com.sebbia.news

import com.sebbia.dataRepository.database.DatabaseService
import com.sebbia.dataRepository.database.NewsTable
import org.jetbrains.exposed.sql.select

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




