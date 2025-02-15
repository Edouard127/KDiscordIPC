package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.OpenActivityInvitePacket
import dev.cbyrne.kdiscordipc.data.overlay.ActivityActionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenActivityInvitePacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "OPEN_OVERLAY_ACTIVITY_INVITE",
    override val args: Arguments? = null,
    override var nonce: String = "0"
) : CommandPacket() {
    constructor(type: ActivityActionType, pid: Long) : this(args = Arguments(type.value, pid))

    @Serializable
    data class Arguments(
        @SerialName("type") val value: Int,
        @SerialName("pid") val pid: Long,
    ) : OutboundPacket.Arguments()
}

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
