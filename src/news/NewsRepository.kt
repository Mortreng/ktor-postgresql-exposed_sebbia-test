package com.sebbia.news

import java.lang.Integer.min

interface NewsRepository {
    fun getNewsByCategory(id: Int, page: Int, limit: Int): List<Article>
    fun getFullArticle(id: Int): Article
}

/**
This class handles operations on News mock data.
Will probably move the mock data to here, or make a separate file entirely.
 **/
class NewsRepositoryMock : NewsRepository {
    val news = listOf(
        Article(
            1,
            "Парочка туристов случайно воспользовалась «личным самолетом»",
            "26.08.2020",
            "Семейная пара из Великобритании ощутила прелести полета на личном самолете, оказавшись единственными пассажирами на обычном пассажирском рейсе.",
            "<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>",
            1
        ),
        Article(
            2,
            "Манулу очень грустно :(",
            "20.08.2020",
            "Вот веселый был, а потом вдруг загрустил ;((((",
            "<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>",
            1
        ),
        Article(
            3,
            "Одна новость на сегодня",
            "20.08.2020",
            "Одна новость, вот и все",
            "<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>",
            1
        ),
        Article(
            4,
            "Ворьби вымерли",
            "20.12.2042",
            "Вымер последний вид птиц на земле",
            "<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>",
            2
        ),
        Article(
            5,
            "Дайте денег",
            "24.05.2020",
            "Очень денег хочу",
            "<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>",
            1
        )
    )

    override fun getNewsByCategory(id: Int, page: Int, limit: Int): List<Article> {
        val list = news.filter { it.categoryId == id }
        val total = list.size

        val start = page * limit
        val end = min(start + limit, total)
        if (start > total || end > total)
            return emptyList()
        return list.subList(start, end)
    }

    override fun getFullArticle(id: Int): Article =
        news.find { it.id == id } ?: throw InternalError("article with id $id not found")
}
