package hu.bme.vik.plugins

import hu.bme.vik.clients.DiscordClient
import hu.bme.vik.models.Notification
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/manualSend") {
            val notification = call.receive<Notification>()
            DiscordClient.sendMessage(notification)
        }
    }
}
