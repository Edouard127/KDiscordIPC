package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.ActivityJoinRequestData
import kotlinx.serialization.Serializable

@Serializable
data class ActivityJoinRequestEvent(val data: ActivityJoinRequestData) : Event
