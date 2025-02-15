package dev.cbyrne.kdiscordipc

import dev.cbyrne.kdiscordipc.core.event.DiscordEvent
import dev.cbyrne.kdiscordipc.core.event.Event
import dev.cbyrne.kdiscordipc.core.event.impl.*
import dev.cbyrne.kdiscordipc.core.packet.inbound.InboundPacket
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.DispatchEventPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.HandshakePacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.SubscribePacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.UnsubscribePacket
import dev.cbyrne.kdiscordipc.core.packet.pipeline.MessageToByteEncoder
import dev.cbyrne.kdiscordipc.core.socket.Socket
import dev.cbyrne.kdiscordipc.core.socket.SocketProvider
import dev.cbyrne.kdiscordipc.core.socket.handler.SocketHandler
import dev.cbyrne.kdiscordipc.manager.impl.ActivityManager
import dev.cbyrne.kdiscordipc.manager.impl.ApplicationManager
import dev.cbyrne.kdiscordipc.manager.impl.VoiceSettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.*
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.SubscribePacket as InboundSubscribePacket

/**
 * KDiscordIPC is the main class for interacting with Discord's IPC (Inter-Process Communication) system.
 * It manages the connection to Discord's IPC server and provides access to various managers
 * for handling activities, applications, relationships, users, and voice settings.
 *
 * @property clientID The client ID of the Discord application.
 * @property scope The coroutine scope in which the IPC operations will run. Defaults to IO dispatcher.
 * @param socketSupplier A supplier function for creating the Socket instance. Defaults to system default socket.
 */
class KDiscordIPC(
    private val clientID: String,
    socketSupplier: () -> Socket = SocketProvider::systemDefault,
    val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
) {
    private val socketHandler = SocketHandler(scope, socketSupplier) {
        scope.launch {
            this@KDiscordIPC._events.emit(DisconnectedEvent())
        }
    }

    /**
     * Indicates whether the IPC connection is currently established.
     */
    val connected: Boolean
        get() = socketHandler.connected

    val activityManager = ActivityManager(this)
    val applicationManager = ApplicationManager(this)
    val voiceSettingsManager = VoiceSettingsManager(this)

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    private val _packets = MutableSharedFlow<InboundPacket>()
    val packets = _packets.asSharedFlow()

    /**
     * Connects to the Discord IPC server
     *
     * @param index The discord IPC socket to connect to. i.e. $TMPDIR/discord-ipc-{index}
     * @see SocketHandler.connect
     */
    suspend fun connect(index: Int = 0) {
        activityManager.init()
        applicationManager.init()
        voiceSettingsManager.init()

        socketHandler.connect(index)
        writePacket(HandshakePacket(1, clientID))

        socketHandler.events.collect {
            when (it) {
                is DispatchEventPacket.Error -> _events.emit(ErrorEvent(it.data))
                is DispatchEventPacket.Ready -> _events.emit(ReadyEvent(it.data))
                is DispatchEventPacket.ActivityJoin -> _events.emit(ActivityJoinEvent(it.data))
                is DispatchEventPacket.ActivitySpectate -> _events.emit(ActivitySpectateEvent(it.data))
                is DispatchEventPacket.ActivityJoinRequest -> _events.emit(ActivityJoinRequestEvent(it.data))
                is DispatchEventPacket.SpeakingStart -> _events.emit(SpeakingStartEvent(it.data))
                is DispatchEventPacket.SpeakingStop -> _events.emit(SpeakingStopEvent(it.data))
                is DispatchEventPacket.VoiceChannelSelect -> _events.emit(VoiceChannelSelectEvent(it.data))
                is DispatchEventPacket.VoiceConnectionStatus -> _events.emit(VoiceConnectionStatusEvent(it.data))
                is DispatchEventPacket.VoiceSettingsUpdate -> _events.emit(VoiceSettingsUpdateEvent(it.data))
                else -> _packets.emit(it)
            }
        }
    }

    /**
     * Registers a consumer for a specific event type.
     *
     * @param T The type of event to listen for.
     * @param consumer The consumer function to invoke when the event is received.
     */
    suspend inline fun <reified T : Any> on(noinline consumer: suspend T.() -> Unit) =
        events.filterIsInstance<T>().onEach { event ->
            scope.launch { consumer(event) }
        }.launchIn(scope)

    /**
     * Disconnects from the Discord IPC server
     *
     * @see SocketHandler.disconnect
     */
    fun disconnect() {
        socketHandler.disconnect()
    }

    /**
     * Subscribe to an [Event]
     *
     * @param event The event to subscribe to
     * @return The subscription packet
     * @see SubscribePacket
     */
    suspend fun subscribe(event: DiscordEvent) =
        sendPacket<InboundSubscribePacket>(SubscribePacket(event))

    /**
     * Unsubscribe to an [Event]
     *
     * @param event The event to subscribe to
     * @return The subscription packet
     * @see SubscribePacket
     */
    suspend fun unsubscribe(event: DiscordEvent) =
        sendPacket<InboundSubscribePacket>(UnsubscribePacket(event))

    /**
     * Write a packet to the Discord IPC server
     *
     * @param packet The packet to write
     * @param nonce The nonce to use for the packet
     * @see MessageToByteEncoder.encode
     */
    private suspend fun writePacket(packet: OutboundPacket, nonce: String? = null) =
        socketHandler.write(MessageToByteEncoder.encode(packet, nonce))

    /**
     * Send a packet to the Discord IPC server and wait for a response
     *
     * @param packet The packet to send
     * @return The response packet
     * @see MessageToByteEncoder.encode
     */
    internal suspend inline fun <reified T : InboundPacket> sendPacket(packet: OutboundPacket): T {
        val nonce = UUID.randomUUID().toString()
        writePacket(packet, nonce)

        return packets.filterIsInstance<T>().first { it.nonce == nonce }
    }

    companion object {
        internal val logger = LoggerFactory.getLogger("KDiscordIPC")
    }
}
