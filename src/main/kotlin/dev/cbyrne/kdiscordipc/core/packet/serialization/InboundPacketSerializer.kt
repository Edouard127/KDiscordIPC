package dev.cbyrne.kdiscordipc.core.packet.serialization

import dev.cbyrne.kdiscordipc.core.packet.inbound.InboundPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.*
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.OpenActivityInvitePacket
import dev.cbyrne.kdiscordipc.data.activity.Activity
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

object InboundPacketSerializer : JsonContentPolymorphicSerializer<InboundPacket>(InboundPacket::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<InboundPacket> =
        when (val evt = element.contentOrNull("evt")) {
            "READY" -> DispatchEventPacket.Ready.serializer()
            "ERROR" -> DispatchEventPacket.Error.serializer()
            "VOICE_SETTINGS_UPDATE" -> DispatchEventPacket.VoiceSettingsUpdate.serializer()
            "VOICE_CONNECTION_STATUS" -> DispatchEventPacket.VoiceConnectionStatus.serializer()
            "SPEAKING_START" -> DispatchEventPacket.SpeakingStart.serializer()
            "SPEAKING_STOP" -> DispatchEventPacket.SpeakingStop.serializer()
            "ACTIVITY_JOIN" -> DispatchEventPacket.ActivityJoin.serializer()
            "ACTIVITY_SPECTATE" -> error("ACTIVITY_SPECTATE is not supported.")
            "ACTIVITY_JOIN_REQUEST" -> DispatchEventPacket.ActivityJoinRequest.serializer()
            "OVERLAY_UPDATE" -> DispatchEventPacket.OverlayUpdate.serializer()
            else -> when (val command = element.contentOrNull("cmd")) {
                "AUTHORIZE" -> AuthorizePacket.serializer()
                "AUTHENTICATE" -> AuthenticatePacket.serializer()
                "SUBSCRIBE" -> SubscribePacket.serializer()
                "UNSUBSCRIBE" -> UnsubscribePacket.serializer()
                "SET_USER_VOICE_SETTINGS" -> SetUserVoiceSettingsPacket.serializer()
                "GET_VOICE_SETTINGS" -> GetVoiceSettingsPacket.serializer()
                "SET_VOICE_SETTINGS" -> SetVoiceSettingsPacket.serializer()
                "GET_USER" -> GetUserPacket.serializer()
                "SET_ACTIVITY" -> SetActivityPacket.serializer()
                "SEND_ACTIVITY_INVITE" -> ActivitySendInvitePacket.serializer()
                "SEND_ACTIVITY_JOIN_INVITE" -> ActivityJoinInvitePacket.serializer()
                "CLOSE_ACTIVITY_REQUEST" -> CloseActivityRequestPacket.serializer()
                "OPEN_OVERLAY_ACTIVITY_INVITE" -> OpenActivityInvitePacket.serializer()
                "OPEN_OVERLAY_GUILD_INVITE" -> OpenGuildInvitePacket.serializer()
                "OPEN_OVERLAY_VOICE_SETTINGS" -> OpenVoiceSettingsPacket.serializer()
                else -> error("Unknown packet command: $command | Event: $evt")
            }
        }

    private fun JsonElement.contentOrNull(key: String) =
        jsonObject[key]?.jsonPrimitive?.contentOrNull
}
