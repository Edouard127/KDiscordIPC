package dev.cbyrne.kdiscordipc.core.event.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceConnectionStatusData(
    val state: ConnectionStatus,
    val hostname: String,
    val pings: List<Int>,
    @SerialName("average_name") val averagePing: Int,
    @SerialName("last_ping") val lastPing: Int,
) : EventData() {
    @Serializable
    enum class ConnectionStatus {
        DISCONNECTED,
        AWAITING_ENDPOINT,
        AUTHENTICATING,
        CONNECTING,
        CONNECTED,
        VOICE_DISCONNECTED,
        VOICE_CONNECTING,
        VOICE_CONNECTED,
        NO_ROUTE,
        ICE_CHECKING,
    }
}
