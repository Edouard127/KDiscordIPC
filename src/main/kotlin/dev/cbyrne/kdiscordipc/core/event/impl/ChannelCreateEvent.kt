package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.ChannelCreateData

data class ChannelCreateEvent(val data: ChannelCreateData) : Event
