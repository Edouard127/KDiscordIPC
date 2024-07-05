package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.data.voiceSettings.InputOutput
import dev.cbyrne.kdiscordipc.data.voiceSettings.Mode
import dev.cbyrne.kdiscordipc.data.voiceSettings.VoiceSettingsInterface
import dev.cbyrne.kdiscordipc.data.voiceSettings.VoiceStateData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetVoiceSettingsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_VOICE_SETTINGS",
    override val args: Arguments? = null,
    override var nonce: String = "0"
): CommandPacket()

@Serializable
data class SetVoiceSettingsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "SET_VOICE_SETTINGS",
    override val args: VoiceSettingArguments,
    override var nonce: String = "0"
): CommandPacket() {
    @Serializable
    data class VoiceSettingArguments(
        override val input: InputOutput? = null,
        override val output: InputOutput? = null,
        override val mode: Mode? = null,
        override val automaticGainControl: Boolean? = null,
        override val echoCancellation: Boolean? = null,
        override val noiseSuppression: Boolean? = null,
        override val qos: Boolean? = null,
        override val silenceWarnings: Boolean? = null,
        override val mute: Boolean? = null,
        override val deaf: Boolean? = null
    ): Arguments(), VoiceSettingsInterface
}

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
