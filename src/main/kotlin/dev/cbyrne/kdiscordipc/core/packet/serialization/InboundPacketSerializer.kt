package dev.cbyrne.kdiscordipc.core.packet.serialization

import dev.cbyrne.kdiscordipc.core.packet.inbound.InboundPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.DispatchEventPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.SetActivityPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

object InboundPacketSerializer : JsonContentPolymorphicSerializer<InboundPacket>(InboundPacket::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out InboundPacket> =
        when (val evt = element.contentOrNull("evt")) {
            "READY" -> DispatchEventPacket.Ready.serializer()
            "ERROR" -> DispatchEventPacket.Error.serializer()
            "GUILD_STATUS" -> DispatchEventPacket.GuildStatus.serializer()
            "GUILD_CREATE" -> DispatchEventPacket.GuildCreate.serializer()
            "VOICE_SETTINGS_UPDATE" -> DispatchEventPacket.VoiceSettingsUpdate.serializer()
            "VOICE_CONNECTION_STATUS" -> DispatchEventPacket.VoiceConnectionStatus.serializer()
            "SPEAKING_START" -> DispatchEventPacket.SpeakingStart.serializer()
            "SPEAKING_STOP" -> DispatchEventPacket.SpeakingStop.serializer()
            "MESSAGE_CREATE" -> DispatchEventPacket.MessageCreate.serializer()
            "MESSAGE_UPDATE" -> DispatchEventPacket.MessageUpdate.serializer()
            "MESSAGE_DELETE" -> DispatchEventPacket.MessageDelete.serializer()
            "NOTIFICATION_CREATE" -> DispatchEventPacket.Notification.serializer()
            "ACTIVITY_JOIN" -> DispatchEventPacket.ActivityJoin.serializer()
            "ACTIVITY_SPECTATE" -> error("ACTIVITY_SPECTATE is not supported.")
            "ACTIVITY_JOIN_REQUEST" -> DispatchEventPacket.ActivityJoinRequest.serializer()
            else -> when (val command = element.contentOrNull("cmd")) {
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
                else -> error("Unknown packet command: $command | Event: $evt")
            }
        }

    private fun JsonElement.contentOrNull(key: String) =
        jsonObject[key]?.jsonPrimitive?.contentOrNull
}
