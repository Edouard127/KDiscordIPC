package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenVoiceSettingsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "OPEN_OVERLAY_VOICE_SETTINGS",
    override val args: Arguments? = null,
    override var nonce: String = "0"
) : CommandPacket() {
    constructor(pid: Long) : this(args = Arguments(pid))

    @Serializable
    data class Arguments(
        @SerialName("pid") val pid: Long,
    ) : OutboundPacket.Arguments()
}
