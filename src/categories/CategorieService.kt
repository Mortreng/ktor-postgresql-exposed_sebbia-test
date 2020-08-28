package com.sebbia.categories

data class Category(val id: Int, val name: String)

class CategoriesService(
    private val categoriesRepository: CategoryRepository
) {
    fun getList() : List<Category> = categoriesRepository.getList()
}


