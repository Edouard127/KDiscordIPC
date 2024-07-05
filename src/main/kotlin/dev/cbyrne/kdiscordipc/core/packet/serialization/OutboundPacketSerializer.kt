package dev.cbyrne.kdiscordipc.core.packet.serialization

import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.SetUserVoiceSettingsPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.SetVoiceSettingsPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

object OutboundPacketSerializer : JsonContentPolymorphicSerializer<OutboundPacket>(OutboundPacket::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<OutboundPacket> {
        return when (element.contentOrNull("command")) {
            "AUTHORIZE" -> AuthorizePacket.serializer()
            "AUTHENTICATE" -> AuthenticatePacket.serializer()
            "GET_GUILD" -> GetGuildPacket.serializer()
            "GET_GUILDS" -> GetGuildsPacket.serializer()
            "GET_CHANNEL" -> GetChannelPacket.serializer()
            "GET_CHANNELS" -> GetChannelsPacket.serializer()
            "SUBSCRIBE" -> SubscribePacket.serializer()
            "UNSUBSCRIBE" -> UnsubscribePacket.serializer()
            "SET_USER_VOICE_SETTINGS" -> SetUserVoiceSettingsPacket.serializer()
            "SELECT_VOICE_CHANNEL" -> SelectVoiceChannelPacket.serializer()
            "GET_SELECTED_VOICE_CHANNEL" -> GetVoiceChannelPacket.serializer()
            "SELECT_TEXT_CHANNEL" -> SelectTextChannelPacket.serializer()
            "GET_VOICE_SETTINGS" -> GetVoiceSettingsPacket.serializer()
            "SET_VOICE_SETTINGS" -> SetVoiceSettingsPacket.serializer()
            "SET_ACTIVITY" -> SetActivityPacket.serializer()
            "SEND_ACTIVITY_JOIN_INVITE" -> ActivityJoinInvitePacket.serializer()
            "CLOSE_ACTIVITY_REQUEST" -> CloseActivityRequestPacket.serializer()
            else -> HandshakePacket.serializer()
        }
    }

    private fun JsonElement.contentOrNull(key: String) =
        jsonObject[key]?.jsonPrimitive?.contentOrNull
}
