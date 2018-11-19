import com.fasterxml.jackson.databind.SerializationFeature
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun Application.module() {
    val db = DatabaseConfig()

    install(StatusPages) {
        exception<Throwable> {
            call.respondText(it.localizedMessage,  ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this@module.javaClass.classLoader, "templates")
    }

    install(Routing) {
        get("/") {
            call.respondText("Hello !", ContentType.Text.Html)
        }

        post("/articles") {
            val request = call.receive<Article>()

            call.respond(HttpStatusCode.OK, db.addArticle(request))
        }

        get("/articles") {
            call.respond(db.articles())
        }

        get("/admins/articles") {
            call.respond(
                    FreeMarkerContent("admin_articles.ftl", mapOf(
                            "articles" to db.articles()
                    ))
            )
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("MainKt"), module = Application::module,
            host = "127.0.0.1").start()
}