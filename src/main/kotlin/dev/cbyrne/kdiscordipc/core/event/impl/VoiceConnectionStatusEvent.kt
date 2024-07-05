package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.VoiceConnectionStatusData

data class VoiceConnectionStatusEvent(val data: VoiceConnectionStatusData) : Event
