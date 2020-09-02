package com.sebbia.news
import com.fasterxml.jackson.annotation.JsonIgnore
import java.lang.Integer.min
//TODO next, this whole file needs some cleanup along with some implementations
data class Article(
    val id: Int,
    val title: String,
    val date: String,
    val shortDescription: String,
    val fullDescription: String,
    @JsonIgnore
    val categoryId: Int
)

class NewsService(
    private val newsRepository: NewsRepository,
    private val defaultLimit: Int = 1
) {
    fun getNewsByCategory(id: Int, page: Int,limit: Int): List<Article> = newsRepository.getNewsByCategory(id, page,limit)


    fun getFullArticle(id: Int) = newsRepository.getFullArticle(id)
}