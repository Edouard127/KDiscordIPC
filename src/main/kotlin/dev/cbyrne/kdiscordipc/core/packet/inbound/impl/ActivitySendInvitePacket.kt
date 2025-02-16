package dev.cbyrne.kdiscordipc.core.packet.inbound.impl

import dev.cbyrne.kdiscordipc.core.event.data.ErrorData
import dev.cbyrne.kdiscordipc.core.packet.inbound.CommandPacket
import kotlinx.serialization.Serializable

@Serializable
data class ActivitySendInvitePacket(
    override val data: ErrorData?,
    override val cmd: String = "ACTIVITY_INVITE_USER",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
) : CommandPacket()
