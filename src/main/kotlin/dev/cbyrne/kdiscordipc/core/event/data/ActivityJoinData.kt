package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.Serializable

@Serializable
data class ActivityJoinData(
    val secret: String
) : EventData()
