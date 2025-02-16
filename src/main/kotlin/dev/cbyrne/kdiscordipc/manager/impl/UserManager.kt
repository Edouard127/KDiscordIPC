package dev.cbyrne.kdiscordipc.manager.impl

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.impl.ReadyEvent
import dev.cbyrne.kdiscordipc.data.user.User
import dev.cbyrne.kdiscordipc.manager.Manager
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.GetUserPacket as InboundGetUser
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.GetUserPacket as OutboundGetUser

class UserManager(override val ipc: KDiscordIPC) : Manager() {
    /**
     * Returns the user connected to the pipe
     * Should never be null unless the client is not connected
     */
    lateinit var currentUser: User; private set

    /**
     * Send a command to get a user from its id
     * This function will hang if any unexpected errors happen
     */
    suspend fun getUser(id: String) =
        ipc.sendPacket<InboundGetUser>(OutboundGetUser(id)).data

    override suspend fun init() {
        ipc.on<ReadyEvent> { currentUser = data.user }
    }
}
