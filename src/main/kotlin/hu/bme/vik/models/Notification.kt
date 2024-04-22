package hu.bme.vik.models

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val imageDescription: String,
    val carNum: Int,
    val timestamp: String
)
