package com.sebbia

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import com.sebbia.categories.CategoriesService
import com.sebbia.categories.CategoryRepositoryMock
import com.sebbia.news.NewsRepositoryMock
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
        exception<Throwable> { cause ->
            call.respond(StatusResponse(code = 500, message = cause.localizedMessage))
        }
    }

    val newsService = NewsService(NewsRepositoryMock())
    val categoriesService = CategoriesService(CategoryRepositoryMock())

    routing {
        route("v1") {
            route("news") {
                get("details{id}") {
                    val id = call.request.queryParameters["id"]?.toInt()
                    if (id == null) {
                        call.respond(StatusResponse(code = 14, message = "Id not found"))
                    } else {
                        call.respond(ObjectResponse(code = 0, `object` = newsService.getFullArticle(id)))
                    }
                }
                route("categories") {
                    get {
                        call.respond(ListResponse(code = 0, list = categoriesService.getList()))
                    }
                   get("/{id}/news{page}") {
                        val page = call.request.queryParameters["page"]?.toInt() ?: 0
                        val id = call.parameters["id"]?.toInt()
                        if (id == null){
                            call.respond(StatusResponse(code = 14, message = "id not found"))
                        } else {
                            call.respond(ListResponse(code = 0, list = newsService.getNewsByCategory(id, page)))
                        }
                   }
                }
            }
        }
    }
}


