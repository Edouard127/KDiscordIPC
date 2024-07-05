package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.MessageData

data class MessageCreateEvent(val data: MessageData) : Event

data class MessageUpdateEvent(val data: MessageData) : Event

data class MessageDeleteEvent(val data: MessageData) : Event
