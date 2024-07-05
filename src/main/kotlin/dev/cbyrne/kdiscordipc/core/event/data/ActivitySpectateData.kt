package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.Serializable

@Serializable
data class ActivitySpectateData(
    val secret: String
) : EventData()
