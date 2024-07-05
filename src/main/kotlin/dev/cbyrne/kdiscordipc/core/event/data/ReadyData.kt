package dev.cbyrne.kdiscordipc.core.event.data

import dev.cbyrne.kdiscordipc.data.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadyData(
    @SerialName("v")
    val version: Int? = null,
    val config: Configuration,
    val user: User,
) : EventData() {
    @Serializable
    data class Configuration(
        @SerialName("cdn_host") val cdnHost: String,
        @SerialName("api_endpoint") val apiEndpoint: String,
        val environment: String
    )
}
