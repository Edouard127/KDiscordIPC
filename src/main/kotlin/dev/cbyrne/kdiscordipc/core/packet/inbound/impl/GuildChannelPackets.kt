package dev.cbyrne.kdiscordipc.core.packet.inbound.impl

import dev.cbyrne.kdiscordipc.core.packet.inbound.CommandPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.InboundPacket
import dev.cbyrne.kdiscordipc.data.message.ChannelMention
import dev.cbyrne.kdiscordipc.data.message.Message
import dev.cbyrne.kdiscordipc.data.voiceSettings.VoiceStateData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetChannelPacket(
    override val data: Data,
    override val cmd: String = "GET_CHANNEL",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
): CommandPacket() {
    @Serializable
    data class Data(
        val id: String,
        @SerialName("guild_id") val guildId: String,
        val name: String,
        val type: ChannelMention.ChannelType,
        val topic: String,
        val bitrate: Int,
        @SerialName("user_limit") val userLimit: Int,
        val position: Int,
        @SerialName("voice_states") val voiceStates: List<VoiceStateData.VoiceState>,
        val messages: List<Message>
    ) : InboundPacket.Data()
}

@Serializable
data class GetChannelsPacket(
    override val data: Data,
    override val cmd: String = "GET_CHANNELS",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
): CommandPacket() {
    @Serializable
    data class Data(
        val channels: List<GetChannelPacket.Data>
    ) : InboundPacket.Data()
}

@Serializable
data class SelectVoiceChannelPacket(
    override val data: GetChannelPacket.Data,
    override val cmd: String = "SELECT_VOICE_CHANNEL",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
) : CommandPacket()

@Serializable
data class GetVoiceChannelPacket(
    override val data: GetChannelPacket.Data,
    override val cmd: String = "GET_SELECTED_VOICE_CHANNEL",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
) : CommandPacket()

@Serializable
data class SelectTextChannelPacket(
    override val data: GetChannelPacket.Data,
    override val cmd: String = "SELECT_TEXT_CHANNEL",
    override val opcode: Int = 0x01,
    override val nonce: String = "0"
) : CommandPacket()
