package dev.cbyrne.kdiscordipc.core.packet.outbound.impl

import dev.cbyrne.kdiscordipc.core.event.DiscordEvent
import dev.cbyrne.kdiscordipc.core.packet.outbound.OutboundPacket
import kotlinx.serialization.Serializable

@Serializable
/**
 * A packet to subscribe to an RPC event.
 * @param evt The event to subscribe to.
 * @see <a href="https://discord.com/developers/docs/topics/rpc#subscribe">Discord RPC Documentation</a>
 */
data class SubscribePacket(
    override val opcode: Int = 0x01,
    override val cmd: String = "SUBSCRIBE",
    override val args: Arguments = Arguments(),
    override var nonce: String = "0",
    val evt: String
) : CommandPacket() {
    constructor(name: DiscordEvent) : this(evt = name.eventName)

    @Serializable
    class Arguments : OutboundPacket.Arguments()
}
