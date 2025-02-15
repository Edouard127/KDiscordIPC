package dev.cbyrne.kdiscordipc.manager.impl

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.AuthenticatePacket
import dev.cbyrne.kdiscordipc.core.packet.outbound.impl.AuthorizePacket
import dev.cbyrne.kdiscordipc.manager.Manager
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.AuthenticatePacket as InboundAuthenticate
import dev.cbyrne.kdiscordipc.core.packet.inbound.impl.AuthorizePacket as InboundAuthorize

/**
 * This manager gives you access  to a bearer token for the currently connected Discord user, which you can then use against the Discord REST API.
 */
class ApplicationManager(override val ipc: KDiscordIPC) : Manager() {
    /**
     * Authorize the current user for the IPC connection with the specified scopes.
     * You can then use the token on the `https://discord.com/api/oauth2/token/rpc` endpoint to get a bearer token.
     *
     * Note that your application must be in a private beta in order to use this in production.
     */
    suspend fun authorize(
        scopes: Array<String>? = null,
        clientId: String? = null,
        rpcToken: String? = null,
        username: String? = null,
    ): InboundAuthorize.Data {
        val response = ipc.sendPacket<InboundAuthorize>(AuthorizePacket(scopes, clientId, rpcToken, username))
        return response.data
    }

    /**
     * **Make sure that you have `http://127.0.0.1` set as a valid redirect URI for your application in the Developer Portal.**
     *
     * Retrieve an oauth2 bearer token for the current user.
     *   - If your application was launched from Discord and you call this function, you will automatically receive the token.
     *   - If the application was not launched from Discord and this method is called, Discord will focus itself and prompt the user for authorization.
     *
     * These bearer tokens are active for seven days, after which they will expire.
     */
    suspend fun authenticate(token: String? = null): InboundAuthenticate.Data {
        val response = ipc.sendPacket<InboundAuthenticate>(AuthenticatePacket(token))
        return response.data
    }
}
