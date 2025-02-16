package dev.cbyrne.kdiscordipc.manager.impl

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.data.ErrorData
import dev.cbyrne.kdiscordipc.core.event.data.OverlayData
import dev.cbyrne.kdiscordipc.core.event.impl.OverlayEvent
import dev.cbyrne.kdiscordipc.core.packet.inbound.InboundPacket
import dev.cbyrne.kdiscordipc.core.util.currentPid
import dev.cbyrne.kdiscordipc.data.overlay.ActivityActionType
import dev.cbyrne.kdiscordipc.manager.Manager
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.ErrorPacket as InboundError
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.OpenActivityInvitePacket as InboundOpenActivityInvite
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.OpenActivityInvitePacket as OutboundOpenActivityInvite
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.OpenGuildInvitePacket as InboundOpenGuildInvite
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.OpenGuildInvitePacket as OutboundOpenGuildInvite
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.OpenVoiceSettingsPacket as InboundOpenVoiceSettings
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
     *
     * @return The [InboundOpenActivityInvite]'s error or null if the client is not in a party
     */
    suspend fun openActivityInvite(action: ActivityActionType): InboundOpenActivityInvite? {
        if (ipc.activityManager.activity != null)
            return ipc.sendPacket<InboundOpenActivityInvite>(OutboundOpenActivityInvite(action, currentPid))

        return null
    }

    /**
     * Opens the overlay modal for joining a Discord guild, given its invite code
     *
     * WARNING: If the user is already in the server, this will hang
     */
    suspend fun openGuildInvite(code: String) =
        ipc.sendPacket<InboundOpenGuildInvite>(OutboundOpenGuildInvite(code, currentPid))

    /**
     * Opens the overlay widget for voice settings for the currently connected application
     *
     * WARNING: There is a glitch with the client where if you close the prompt by clicking
     * outside the box, the rpc never receives a response, and thus gets stuck waiting for
     * an answer
     */
    suspend fun openVoiceSettings() =
        ipc.sendPacket<InboundOpenVoiceSettings>(OutboundOpenVoiceSettings(currentPid))

    override suspend fun init() {
        ipc.on<OverlayEvent> {
            overlayState = data
            onToggle.forEach { fn -> fn(data) }
        }
    }
}
