package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenGuildInvitePacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "OPEN_OVERLAY_GUILD_INVITE",
    override val args: Arguments? = null,
    override var nonce: String = "0"
) : CommandPacket() {
    constructor(inviteCode: String, pid: Long) : this(args = Arguments(inviteCode, pid))

    @Serializable
    data class Arguments(
        @SerialName("code") val inviteCode: String,
        @SerialName("pid") val pid: Long,
    ) : OutboundPacket.Arguments()
}
