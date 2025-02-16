package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.core.util.currentPid
import dev.cbyrne.kdiscordipc.data.overlay.ActivityActionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivitySendInvitePacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "ACTIVITY_INVITE_USER",
    override val args: Arguments,
    override var nonce: String = "0"
) : OutboundPacket() {
    constructor(user: String, type: ActivityActionType) : this(args = Arguments(currentPid, user, type.value))

    @Serializable
    data class Arguments(
        @SerialName("pid") val pid: Long,
        @SerialName("user_id") val userId: String,
        @SerialName("type") val type: Int,
    ) : OutboundPacket.Arguments()
}
