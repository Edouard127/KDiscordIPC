package dev.cbyrne.kdiscordipc.core.packet.inbound.impl

import dev.cbyrne.kdiscordipc.core.event.data.ErrorData
import dev.cbyrne.kdiscordipc.core.packet.inbound.CommandPacket
import kotlinx.serialization.Serializable

@Serializable
data class CloseActivityRequestPacket(
    override val data: ErrorData? = null,
    override val cmd: String = "CLOSE_ACTIVITY_INVITE",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
) : CommandPacket()
