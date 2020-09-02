package com.sebbia

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import com.sebbia.categories.CategoriesService
import com.sebbia.categories.CategoryRepositoryImpl
import com.sebbia.dataRepository.database.DatabaseServiceImpl
import com.sebbia.news.NewsRepositoryImpl
import com.sebbia.news.NewsService
import io.ktor.jackson.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(DataConversion)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(StatusPages) {
        // Just a generic error response
        exception<Throwable> { cause ->
            call.respond(StatusResponse(code = 500, message = cause.localizedMessage))
        }
    }

    val dbport: String = environment.config
        .property("db.port").getString()
    val dbhost: String = environment.config
        .property("db.host").getString()
    val database: String = environment.config
        .property("db.database").getString()
    val user: String = environment.config
        .property("db.user").getString()
    val password: String = environment.config
        .property("db.password").getString()

    // Here we initialize our Services, to test features, i advise using mockups for testing purposes,
    // to do that change Impls to Mock Repositories
    val databaseService = DatabaseServiceImpl(
        //note to yourself, get those parameters required for the db connection into a separate config file.
        //storing them like that is asking for trouble in the future
        "jdbc:postgresql://${dbhost}:${dbport}/${database}",
        "org.postgresql.Driver",
        user,
        password
    )
    val newsRepository = NewsRepositoryImpl(databaseService)
    val categoryRepository = CategoryRepositoryImpl(databaseService)
    val newsService = NewsService(newsRepository)
    val categoriesService = CategoriesService(categoryRepository)


    routing {
        route("v1") {
            route("news") {
                get("details{id}") {
                    //This get request will provide us with a full article of news, in case if the id is not
                    //provided, it will hit us with a StatusResponse error
                    val id = call.request.queryParameters["id"]?.toInt()
                    if (id == null) {
                        call.respond(StatusResponse(code = 14, message = "Id not found"))
                    } else {
                        call.respond(ObjectResponse(code = 0, `object` = newsService.getFullArticle(id)))
                    }
                }
                route("categories") {
                    get {
                        // A simple get request will return a full list of "Categories"
                        call.respond(ListResponse(code = 0, list = categoriesService.getList()))
                    }
                    get("/{id}/news{page}") {
                        // a get request, which returns a list of news according to their category the list is
                        //then divided into sublists, those lists can be accessed by inserting a number into a page parameter
                        //if id is not provided, it will hit us with a StatusResponse, if page is not provided it will default to 0
                        val page = call.request.queryParameters["page"]?.toInt() ?: 0
                        val id = call.parameters["id"]?.toInt()
                        val limit = call.parameters["limit"]?.toInt()?:
                        if (id == null) {
                            call.respond(StatusResponse(code = 14, message = "id not found"))
                        } else {
                            call.respond(ListResponse(code = 0, list = newsService.getNewsByCategory(id, page,limit = 1 )))
                        }
                    }
                }
            }
        }
    }
}


