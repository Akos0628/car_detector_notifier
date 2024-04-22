package hu.bme.vik

object Config {
    lateinit var discordUrl: String

    lateinit var rabbitUri: String
    lateinit var rabbitConnectionName: String
    lateinit var rabbitExchange: String
    lateinit var rabbitQueue: String
    lateinit var rabbitRoutingKey: String
}