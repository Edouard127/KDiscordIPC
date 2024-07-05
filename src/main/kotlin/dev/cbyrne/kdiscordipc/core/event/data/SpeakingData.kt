package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpeakingData(
    @SerialName("user_id") val userId: String,
) : EventData()
