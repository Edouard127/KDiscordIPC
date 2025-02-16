package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.SpeakingData

data class SpeakingStartEvent(val data: SpeakingData) : Event
