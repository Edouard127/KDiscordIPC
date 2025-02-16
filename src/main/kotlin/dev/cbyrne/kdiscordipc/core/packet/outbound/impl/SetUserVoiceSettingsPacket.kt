package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.data.voiceSettings.VoiceStateData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetUserVoiceSettingsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "SET_USER_VOICE_SETTINGS",
    override val args: Arguments,
    override var nonce: String = "0"
): CommandPacket() {
    @Serializable
    data class Arguments(
        @SerialName("user_id") val userId: String,
        val pan: VoiceStateData.Pan,
        val volume: Int,
        val mute: Boolean,
    ) : OutboundPacket.Arguments()
}
