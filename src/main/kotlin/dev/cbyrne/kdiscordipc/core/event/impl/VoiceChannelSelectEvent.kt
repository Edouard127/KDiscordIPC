package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.VoiceChannelSelectData

data class VoiceChannelSelectEvent(val data: VoiceChannelSelectData) : Event
