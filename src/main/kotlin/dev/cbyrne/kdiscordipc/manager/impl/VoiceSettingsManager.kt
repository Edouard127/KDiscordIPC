package dev.cbyrne.kdiscordipc.manager.impl

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.DiscordEvent
import dev.cbyrne.kdiscordipc.core.event.impl.VoiceSettingsUpdateEvent
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.GetVoiceSettingsPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.SetVoiceSettingsPacket
import dev.cbyrne.kdiscordipc.data.voiceSettings.VoiceSettings
import dev.cbyrne.kdiscordipc.manager.Manager
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.GetVoiceSettingsPacket as InboundGetVoiceSettingsPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.SetVoiceSettingsPacket as InboundSetVoiceSettingsPacket

class VoiceSettingsManager(override val ipc: KDiscordIPC) : Manager() {
    var currentVoiceSettings: VoiceSettings? = null
        private set

    suspend fun getVoiceSettings(): VoiceSettings =
        ipc.sendPacket<InboundGetVoiceSettingsPacket>(GetVoiceSettingsPacket()).data

    suspend fun setVoiceSettings(settings: SetVoiceSettingsPacket.VoiceSettingArguments) =
        ipc.sendPacket<InboundSetVoiceSettingsPacket>(SetVoiceSettingsPacket(args = settings))

    override suspend fun init() {
        ipc.on<VoiceSettingsUpdateEvent> {
            currentVoiceSettings = this.data
        }
    }
}
