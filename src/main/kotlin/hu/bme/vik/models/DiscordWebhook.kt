package hu.bme.vik.models

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val TIMESTAMP_FORMAT = "yyyy-mm-ddThh:mm:ss.msZ"

@Serializable
data class DiscordWebhook(
    val embeds: List<Embed>,
    val content: String? = null,
    val username: String? = null,
    val avatar_url: String? = null,
    val tts: String? = null, // don't know the type
    val allowed_mentions: AllowedMentions? = null
)

@Serializable
data class Embed(
    val color: Int? = null,
    val author: Author? = null,
    val title: String? = null,
    val url: String? = null,
    val description: String? = null,
    val fields: List<Field> = emptyList(),
    val thumbnail: Thumbnail? = null,
    val image: Image? = null,
    val footer: Footer? = null,
    val timestamp: String? = null,
) {
    fun getTimestampOfInstant(time: Instant) = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT).withZone(ZoneOffset.UTC).format(time)
}

@Serializable
data class AllowedMentions(
    val parse: List<String> = listOf("everyone"),
    val roles: List<Int> = emptyList(),
    val users: List<Int> = emptyList()
)

@Serializable
data class Author(
    val name: String,
    val url: String? = null,
    val icon_url: String? = null
)

@Serializable
data class Field(
    val name: String,
    val value : String,
    val inline : Boolean? = null
)

@Serializable
data class Thumbnail(
    val url: String
)

@Serializable
data class Image(
    val url: String
)

@Serializable
data class Footer(
    val text: String,
    val icon_url : String
)

// according to https://birdie0.github.io/discord-webhooks-guide/discord_webhook.html
private val example = DiscordWebhook(
    listOf(
        Embed(
            16711680,
            Author(
                "Birdie♫",
                "https://www.reddit.com/r/cats/",
                "https://i.imgur.com/R66g1Pe.jpg"
            ),
            "Title",
            "https://google.com/",
            "Text message. You can use Markdown here. *Italic* **bold** __underline__ ~~strikeout~~ [hyperlink](https://google.com) `code`",
            listOf(
                Field(
                    "Text",
                    "More text",
                    true
                ),
                Field(
                    "Even more text",
                    "Yup",
                    true
                ),
                Field(
                    "Use `\"inline\": true` parameter, if you want to display fields in the same line.",
                    "okay..."
                ),
                Field(
                    "Thanks!",
                    "You're welcome :wink:"
                )
            ),
            Thumbnail("https://i0.wp.com/thevirtualfield.org/staging/7416/wp-content/uploads/2020/08/dave-hoefler-YWcy3iq1HqQ-unsplash-scaled.jpg?resize=1200%2C800&ssl=1"),
            Image("https://banfflakelouise.bynder.com/m/3d04f19979f432ec/2000x1080_jpg-2022_MoraineLake_TravelAlberta_RothandRamberg%20(3).jpg"),
            Footer(
                "Woah! So cool! :smirk:",
                "https://i.imgur.com/fKL31aD.jpg"
            )
        )
    ),
    "Text message. Up to 2000 characters.",
    "Webhook",
    "https://i.imgur.com/4M34hi2.png",
)