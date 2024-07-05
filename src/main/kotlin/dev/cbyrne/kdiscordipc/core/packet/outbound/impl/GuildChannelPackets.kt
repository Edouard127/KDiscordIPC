package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.SelectVoiceChannelPacket.Arguments
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetChannelPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_CHANNELS",
    override val args: Arguments,
    override var nonce: String = "0"
): OutboundPacket() {
    @Serializable
    data class Arguments(
        @SerialName("channel_id") val channelId: String
    ) : OutboundPacket.Arguments()
}

@Serializable
data class GetChannelsPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_CHANNELS",
    override val args: Arguments,
    override var nonce: String = "0"
): OutboundPacket() {
    @Serializable
    data class Arguments(
        @SerialName("guild_id") val guildId: String
    ) : OutboundPacket.Arguments()
}

@Serializable
data class SelectVoiceChannelPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "SELECT_VOICE_CHANNEL",
    override val args: Arguments,
    override var nonce: String = "0"
) : CommandPacket() {
    @Serializable
    data class Arguments(
        @SerialName("guild_id") val guildId: String
    ) : OutboundPacket.Arguments()
}

@Serializable
data class GetVoiceChannelPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "GET_SELECTED_VOICE_CHANNEL",
    override val args: Arguments = Arguments(),
    override var nonce: String = "0"
) : CommandPacket() {
    @Serializable
    class Arguments : OutboundPacket.Arguments()
}

@Serializable
data class SelectTextChannelPacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "SELECT_TEXT_CHANNEL",
    override val args: Arguments,
    override var nonce: String = "0"
) : CommandPacket() {
    @Serializable
    data class Arguments(
        @SerialName("guild_id") val guildId: String,
        val timeout: Int
    ) : OutboundPacket.Arguments()
}
