package dev.cbyrne.kdiscordipc.manager.impl

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.util.currentPid
import dev.cbyrne.kdiscordipc.data.activity.Activity
import dev.cbyrne.kdiscordipc.data.activity.activity
import dev.cbyrne.kdiscordipc.manager.Manager
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.CloseActivityRequestPacket as InboundCloseActivity
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.CloseActivityRequestPacket as OutboundCloseActivity
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.ActivityJoinInvitePacket as InboundAcceptActivity
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.ActivityJoinInvitePacket as OutboundActivityJoin
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.SetActivityPacket as InboundSetActivity
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.SetActivityPacket as OutboundSetActivity

/**
 * This manager allows you to set the current user's activity (a.k.a. rich presence)
 */
class ActivityManager(override val ipc: KDiscordIPC) : Manager() {
    var activity: Activity? = null
        private set

    /**
     * Sets a user's presence in Discord to a new activity. This has a rate limit of 5 updates per 20 seconds.
     */
    suspend fun setActivity(activity: Activity?) {
        ipc.sendPacket<InboundSetActivity>(OutboundSetActivity(currentPid, activity))
        this.activity = activity
    }

    /**
     * Sets a user's presence in Discord to a new activity. This has a rate limit of 5 updates per 20 seconds.
     */
    suspend fun setActivity(details: String? = null, state: String? = null, init: Activity.() -> Unit) =
        setActivity(activity(details, state, init))

    /**
     * Accepts an 'Ask to Join' request from a user
     *
     * @return Whether the command was successful
     */
    suspend fun acceptJoinRequest(user: String): Boolean =
        ipc.sendPacket<InboundAcceptActivity>(OutboundActivityJoin(user)).data == null

    /**
     * Refuses an 'Ask to Join' request from a user
     *
     * @return Whether the command was successful
     */
    suspend fun refuseJoinRequest(user: String): Boolean =
        ipc.sendPacket<InboundCloseActivity>(OutboundCloseActivity(user)).data == null

    /**
     * Clear's a user's presence in Discord to make it show nothing.
     */
    suspend fun clear() = setActivity(null)
}
