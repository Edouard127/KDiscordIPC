package dev.cbyrne.kdiscordipc.core.event.impl

import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.data.ErrorData

data class ErrorEvent(val data: ErrorData) : Event
