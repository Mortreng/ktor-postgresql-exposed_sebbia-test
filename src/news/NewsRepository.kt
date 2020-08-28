package com.sebbia.news

import com.sebbia.dataRepository.mockup.news
import java.lang.Integer.min

interface NewsRepository {
    fun getNewsByCategory(id:Int, page:Int):List<Article>
    fun getFullArticle(id:Int):Article
}

class NewsRepositoryMock:NewsRepository {
    override fun getNewsByCategory(id: Int, page: Int): List<Article> {
        val defaultLimit = 1
        val list = news.filter { it.categoryId == id }
        val total = list.size

        val start = page * defaultLimit
        val end = min(start + defaultLimit, total)
        if (start > total || end > total)
            return emptyList()
        return list.subList(start, end)
    }

    override fun getFullArticle(id: Int): Article = news.find { it.id == id } ?: throw InternalError("article with id $id not found")
}
