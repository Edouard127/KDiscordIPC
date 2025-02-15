package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverlayData(
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("locked") val locked: Boolean,
) : EventData()
