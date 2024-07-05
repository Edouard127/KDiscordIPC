package dev.cbyrne.kdiscordipc.core.event.data

import dev.cbyrne.kdiscordipc.data.message.Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageData(
    @SerialName("channel_id") val channelId: String,
    val message: Message,
) : EventData()
