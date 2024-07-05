package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.Serializable

@Serializable
data class ChannelCreateData(
    val id: String,
    val name: String,
    val type: Type,
) : EventData() {
    @Serializable
    enum class Type(val value: Int) {
        GUILD_TEXT(0),
        DM(1),
        GUILD_VOICE(2),
        GROUP_DM(3),
    }
}
