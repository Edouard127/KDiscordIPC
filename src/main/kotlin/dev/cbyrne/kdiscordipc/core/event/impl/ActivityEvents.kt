package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.ActivityJoinData
import dev.cbyrne.kdiscordipc.core.event.data.ActivityJoinRequestData
import dev.cbyrne.kdiscordipc.core.event.data.ActivitySpectateData
import kotlinx.serialization.Serializable

@Serializable
data class ActivityJoinEvent(val data: ActivityJoinData) : Event

@Serializable
data class ActivityJoinRequestEvent(val data: ActivityJoinRequestData) : Event

@Serializable
data class ActivitySpectateEvent(val data: ActivitySpectateData) : Event
