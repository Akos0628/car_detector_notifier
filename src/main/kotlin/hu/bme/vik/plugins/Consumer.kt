package hu.bme.vik.plugins

import hu.bme.vik.clients.DiscordClient
import hu.bme.vik.models.Notification
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.rabbitConsumer

fun Application.configureConsumer() {
    rabbitConsumer {
        consume<Notification>("notification_queue") { body ->
            runBlocking {
                DiscordClient.sendMessage(body)
            }
        }
    }
}