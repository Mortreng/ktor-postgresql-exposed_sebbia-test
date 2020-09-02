package com.sebbia.categories

data class Category(val id: Int, val name: String)
//This is the class that actually handles
class CategoriesService(
    private val categoriesRepository: CategoryRepository
) {
    fun getList() : List<Category> = categoriesRepository.getList()
}


