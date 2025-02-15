package dev.cbyrne.kdiscordipc.manager.impl

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.data.OverlayData
import dev.cbyrne.kdiscordipc.core.event.impl.OverlayEvent
import dev.cbyrne.kdiscordipc.core.util.currentPid
import dev.cbyrne.kdiscordipc.data.overlay.ActivityActionType
import dev.cbyrne.kdiscordipc.manager.Manager
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.ErrorPacket as InboundError
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.OpenActivityInvitePacket as OutboundOpenActivityInvite
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.OpenGuildInvitePacket as OutboundOpenGuildInvite
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.OpenVoiceSettingsPacket as OutboundOpenVoiceSettings

class OverlayManager(override val ipc: KDiscordIPC) : Manager() {
    var overlayState: OverlayData? = null
        private set

    private var onToggle = mutableListOf<(OverlayData) -> Unit>()

    /**
     * Registers a [block] for overlay data updates
     */
    fun onToggle(block: (OverlayData) -> Unit) {
        onToggle += block
    }

    /**
     * Opens the overlay modal for sending game invitations to users, channels, and servers
     */
    suspend fun openActivityInvite(action: ActivityActionType) =
        ipc.sendPacket<InboundError>(OutboundOpenActivityInvite(action, currentPid))

    /**
     * Opens the overlay modal for joining a Discord guild, given its invite code
     */
    suspend fun openGuildInvite(code: String) =
        ipc.sendPacket<InboundError>(OutboundOpenGuildInvite(code, currentPid))

    /**
     * Opens the overlay widget for voice settings for the currently connected application
     */
    suspend fun openVoiceSettings() =
        ipc.sendPacket<InboundError>(OutboundOpenVoiceSettings(currentPid))

    override suspend fun init() {
        ipc.on<OverlayEvent> {
            overlayState = data
            onToggle.forEach { fn -> fn(data) }
        }
    }
}
