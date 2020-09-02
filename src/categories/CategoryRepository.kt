package com.sebbia.categories

interface CategoryRepository {
    fun getList(): List<Category>
    fun insert(category: Category): Category
}
// Here we store our mock category data for testing, along with the functions that manipulate said data

class CategoryRepositoryMock : CategoryRepository {
    private val categories = mutableListOf(
        Category(1, "Много новостей"),
        Category(2, "Мало новостей"),
        Category(3, "Нет новостей")
    )

    override fun getList(): List<Category> = categories
    override fun insert(category: Category): Category {
        TODO("Not yet implemented")
    }
}