package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.data.activity.Activity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityJoinInvitePacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "SEND_ACTIVITY_JOIN_INVITE",
    override val args: Arguments,
    override var nonce: String = "0"
) : OutboundPacket() {
    constructor(
        userId: String,
    ) : this(args = Arguments(userId))

    @Serializable
    data class Arguments(
        @SerialName("user_id") val userId: String,
    ) : OutboundPacket.Arguments()
}

@Serializable
data class CloseActivityRequestPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "CLOSE_ACTIVITY_REQUEST",
    override val args: Arguments,
    override var nonce: String = "0"
) : CommandPacket() {
    @Serializable
    data class Arguments(
        val userId: String,
    ) : OutboundPacket.Arguments()
}

@Serializable
data class SetActivityPacket(
    override val args: Arguments,
    override val cmd: String = "SET_ACTIVITY",
    override val opcode: Int = 0x01,
    override var nonce: String = "0"
) : CommandPacket() {
    constructor(pid: Long, activity: Activity?, nonce: String = "0") : this(Arguments(pid, activity), nonce = nonce)

    @Serializable
    data class Arguments(
        val pid: Long,
        val activity: Activity?
    ) : OutboundPacket.Arguments()
}
