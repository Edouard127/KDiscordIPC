package dev.cbyrne.kdiscordipc.core.packet.inbound.impl

import dev.cbyrne.kdiscordipc.core.event.data.ErrorData
import dev.cbyrne.kdiscordipc.core.packet.inbound.CommandPacket
import kotlinx.serialization.Serializable

@Serializable
data class OpenGuildInvitePacket(
    override val data: ErrorData?,
    override val cmd: String = "OPEN_OVERLAY_GUILD_INVITE",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
) : CommandPacket()
