package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.UnsubscribePacket.Arguments
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGuildPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_GUILD",
    override val args: Arguments,
    override var nonce: String = "0",
): CommandPacket() {
    @Serializable
    data class Arguments(
        @SerialName("guild_id") val guildId: String
    ) : OutboundPacket.Arguments()
}

@Serializable
data class GetGuildsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_GUILDS",
    override val args: Arguments,
    override var nonce: String = "0",
): CommandPacket() {
    @Serializable
    data class Arguments(
        @SerialName("guild_id") val guildId: String
    ) : OutboundPacket.Arguments()
}
