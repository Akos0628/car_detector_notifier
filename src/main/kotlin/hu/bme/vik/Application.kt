package hu.bme.vik

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import hu.bme.vik.plugins.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pl.jutupe.ktor_rabbitmq.RabbitMQ

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Config.discordUrl = environment.config.property("application.discordRoute").getString()
    Config.rabbitUri = environment.config.property("application.rabbitmq.uri").getString()
    Config.rabbitConnectionName = environment.config.property("application.rabbitmq.connectionName").getString()
    Config.rabbitExchange = environment.config.property("application.rabbitmq.exchange").getString()
    Config.rabbitQueue = environment.config.property("application.rabbitmq.queue").getString()
    Config.rabbitRoutingKey = environment.config.property("application.rabbitmq.routingKey").getString()

    install(RabbitMQ) {
        uri = Config.rabbitUri
        connectionName = Config.rabbitConnectionName

        enableLogging()

        //serialize and deserialize functions are required
        serialize { jacksonObjectMapper().writeValueAsBytes(it) }
        deserialize { bytes, type -> jacksonObjectMapper().readValue(bytes, type.javaObjectType) }

        initialize {
            queueBind(
                /* queue = */ Config.rabbitQueue,
                /* exchange = */ Config.rabbitExchange,
                /* routingKey = */ Config.rabbitRoutingKey
            )
        }
    }

    configureRouting()
    configureSerialization()
    configureConsumer()

    routing {
        get("/health") {
            call.respond("Up and running.")
        }
    }
}
