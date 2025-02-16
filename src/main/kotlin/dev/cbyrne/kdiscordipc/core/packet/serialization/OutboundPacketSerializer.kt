package dev.cbyrne.kdiscordipc.core.packet.serialization

import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.ActivitySendInvitePacket
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
            "SUBSCRIBE" -> SubscribePacket.serializer()
            "UNSUBSCRIBE" -> UnsubscribePacket.serializer()
            "SET_USER_VOICE_SETTINGS" -> SetUserVoiceSettingsPacket.serializer()
            "GET_VOICE_SETTINGS" -> GetVoiceSettingsPacket.serializer()
            "SET_VOICE_SETTINGS" -> SetVoiceSettingsPacket.serializer()
            "SET_ACTIVITY" -> SetActivityPacket.serializer()
            "GET_USER" -> GetUserPacket.serializer()
            "SEND_ACTIVITY_INVITE" -> ActivitySendInvitePacket.serializer()
            "SEND_ACTIVITY_JOIN_INVITE" -> ActivityJoinInvitePacket.serializer()
            "CLOSE_ACTIVITY_REQUEST" -> SetActivityPacket.serializer()
            "OPEN_OVERLAY_ACTIVITY_INVITE" -> OpenActivityInvitePacket.serializer()
            "OPEN_OVERLAY_GUILD_INVITE" -> OpenGuildInvitePacket.serializer()
            "OPEN_OVERLAY_VOICE_SETTINGS" -> OpenVoiceSettingsPacket.serializer()
            else -> HandshakePacket.serializer()
        }
    }

    private fun JsonElement.contentOrNull(key: String) =
        jsonObject[key]?.jsonPrimitive?.contentOrNull
}
