package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.ActivitySpectateData
import kotlinx.serialization.Serializable

@Serializable
data class ActivitySpectateEvent(val data: ActivitySpectateData) : Event
