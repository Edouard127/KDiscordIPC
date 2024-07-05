package dev.cbyrne.kdiscordipc.core.packet.inbound.impl

import dev.cbyrne.kdiscordipc.core.packet.inbound.CommandPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.InboundPacket
import dev.cbyrne.kdiscordipc.data.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGuildPacket(
    override val data: Data,
    override val cmd: String = "GET_GUILD",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
): CommandPacket() {
    @Serializable
    data class Data(
        val id: String,
        val name: String,
        @SerialName("icon_name") val iconName: String,
        val members: List<User>
    ) : InboundPacket.Data()
}

@Serializable
data class GetGuildsPacket(
    override val data: Data,
    override val cmd: String = "GET_GUILDS",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
): CommandPacket() {
    @Serializable
    data class Data(
        val guilds: List<GetGuildPacket.Data>
    ) : InboundPacket.Data()
}
