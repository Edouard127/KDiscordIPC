package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloseActivityRequestPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "CLOSE_ACTIVITY_REQUEST",
    override val args: Arguments,
    override var nonce: String = "0"
) : CommandPacket() {
    constructor(id: String) : this(args = Arguments(id))

    @Serializable
    data class Arguments(
        @SerialName("user_id") val userId: String,
    ) : OutboundPacket.Arguments()
}
