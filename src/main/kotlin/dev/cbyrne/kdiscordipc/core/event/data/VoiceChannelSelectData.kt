package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.Serializable

@Serializable
data class VoiceChannelSelectData(
    val guildId: String? = null,
    val channelId: String? = null,
) : EventData()
