package com.sebbia.categories

import com.sebbia.dataRepository.mockup.categories


interface CategoryRepository {
    fun getList(): List<Category>
}

class CategoryRepositoryMock : CategoryRepository {
    override fun getList(): List<Category> = categories
}