package dev.cbyrne.kdiscordipc.core.packet.inbound.impl

import dev.cbyrne.kdiscordipc.core.event.data.*
import dev.cbyrne.kdiscordipc.core.packet.inbound.CommandPacket
import dev.cbyrne.kdiscordipc.data.voiceSettings.VoiceSettings
import kotlinx.serialization.Serializable

@Serializable
abstract class DispatchEventPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "DISPATCH",
    override val nonce: String? = null
) : CommandPacket() {
    abstract override val data: EventData

    @Serializable
    data class Error(
        override val data: ErrorData
    ) : DispatchEventPacket()

    @Serializable
    data class Ready(
        override val data: ReadyData
    ) : DispatchEventPacket()

    @Serializable
    data class ActivityJoin(
        override val data: ActivityJoinData
    ) : DispatchEventPacket()

    @Serializable
    data class ActivitySpectate(
        override val data: ActivitySpectateData
    ) : DispatchEventPacket()

    @Serializable
    data class ActivityJoinRequest(
        override val data: ActivityJoinRequestData
    ) : DispatchEventPacket()

    @Serializable
    data class SpeakingStart(
        override val data: SpeakingData
    ) : DispatchEventPacket()

    @Serializable
    data class SpeakingStop(
        override val data: SpeakingData
    ) : DispatchEventPacket()

    @Serializable
    data class VoiceChannelSelect(
        override val data: VoiceChannelSelectData
    ) : DispatchEventPacket()

    @Serializable
    data class VoiceConnectionStatus(
        override val data: VoiceConnectionStatusData
    ) : DispatchEventPacket()

    @Serializable
    data class VoiceSettingsUpdate(
        override val data: VoiceSettings
    ) : DispatchEventPacket()
}
