package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.SpeakingData

data class SpeakingStopEvent(val data: SpeakingData) : Event
