package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildStatusData(
    val guild: Guild,
    val online: Int,
) : EventData() {
    @Serializable
    data class Guild(
        val id: String,
        val name: String,
        @SerialName("icon_url")
        val icon: String? = null,
    )
}
