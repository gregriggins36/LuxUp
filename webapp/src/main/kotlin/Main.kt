import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
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

    install(Routing) {
        get("/") {
            call.respondText("Hello !", ContentType.Text.Html)
        }

        post("/articles") {
            val request = call.receive<Article>()
            db.addArticle(request)
        }

        get("/articles") {
            call.respond(db.articles())
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("MainKt"), module = Application::module,
            host = "127.0.0.1").start()
}