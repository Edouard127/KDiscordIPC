package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import kotlinx.serialization.Serializable

@Serializable
data class GetVoiceSettingsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_VOICE_SETTINGS",
    override val args: Arguments? = null,
    override var nonce: String = "0"
) : CommandPacket()
