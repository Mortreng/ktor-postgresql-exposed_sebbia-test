package com.sebbia

import com.sebbia.categories.CategoriesService
import com.sebbia.categories.CategoryRepository
import com.sebbia.categories.CategoryRepositoryImpl
import com.sebbia.dataRepository.database.DatabaseService
import com.sebbia.dataRepository.database.DatabaseServiceImpl
import com.sebbia.news.NewsRepository
import com.sebbia.news.NewsRepositoryImpl
import com.sebbia.news.NewsService
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import org.koin.dsl.module

val configurationData = HoconApplicationConfig(ConfigFactory.load())


val dbport: String = configurationData
    .property("db.port").getString()
val dbhost: String = configurationData
    .property("db.host").getString()
val database: String = configurationData
    .property("db.database").getString()
val user: String = configurationData
    .property("db.user").getString()
val password: String = configurationData
    .property("db.password").getString()

val databaseServiceModule = module {
    single<DatabaseService> {
        DatabaseServiceImpl(
            "jdbc:postgresql://${dbhost}:${dbport}/${database}",
            "org.postgresql.Driver",
            user,
            password
        )
    }
}

val categoryServiceModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single { CategoriesService(get()) }
}
val newsServiceModule = module {
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single { NewsService(get()) }
}


