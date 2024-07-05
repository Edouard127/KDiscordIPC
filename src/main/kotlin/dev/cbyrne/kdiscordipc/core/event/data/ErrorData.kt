package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    val code: Int,
    val message: String
) : EventData()
