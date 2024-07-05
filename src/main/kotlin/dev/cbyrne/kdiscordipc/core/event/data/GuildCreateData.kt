package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.Serializable

@Serializable
data class GuildCreateData(
    val id: String,
    val name: String,
) : EventData()
