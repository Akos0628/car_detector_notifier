package hu.bme.vik.clients

import hu.bme.vik.Config
import hu.bme.vik.models.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

const val DISCORD_FIELD_VALUE_LIMIT = 1024

object DiscordClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun sendMessage(notification: Notification): DiscordWebhook {
        val message = messageTemplate(notification.carNum.toString(), notification.imageDescription)

        client.post(Config.discordUrl) {
            contentType(ContentType.Application.Json)
            setBody(message)
        }
         return message
    }

    private fun messageTemplate(numberOfCars: String, description: String): DiscordWebhook {
        val fields = if (description.length >= DISCORD_FIELD_VALUE_LIMIT) {
            listOf(
                Field(
                    "Description: ",
                    description.substring(0, DISCORD_FIELD_VALUE_LIMIT-3).plus("..."),
                    true
                ),
                Field(
                    "Number of cars: ",
                    numberOfCars,
                    true
                ),
                Field(
                    "Warning: ",
                    "Content of the description was too long"
                )
            )
        } else {
            listOf(
                Field(
                    "Description: ",
                    description,
                    true
                ),
                Field(
                    "Number of cars: ",
                    numberOfCars,
                    true
                )
            )
        }

        return DiscordWebhook(
            listOf(
                Embed(
                    color = 16711680,
                    description = "Photo upload",
                    fields = fields
                )
            )
        )
    }
}