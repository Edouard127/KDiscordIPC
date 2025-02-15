package dev.cbyrne.example

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.DiscordEvent
import dev.cbyrne.kdiscordipc.core.event.impl.*
import dev.cbyrne.kdiscordipc.core.event.impl.DisconnectedEvent
import dev.cbyrne.kdiscordipc.data.activity.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

val logger: Logger = LogManager.getRootLogger()

@Serializable
data class TokenAuthorizeResponse(
    @SerialName("rpc_token") val rpcToken: String,
)

suspend fun main() {
    val ipc = KDiscordIPC("945428344806183003")
    logger.info("Starting example!")

    ipc.on<ReadyEvent> {
        logger.info("Ready! (${data.user.username}#${data.user.discriminator})")

        // Set the user's activity (a.k.a. rich presence)
        ipc.activityManager.setActivity("Hello") {
            largeImage("https://avatars.githubusercontent.com/u/71222289?v=4", "KDiscordIPC")
            smallImage("https://avatars.githubusercontent.com/u/71222289?v=4", "Testing")

            party(UUID.randomUUID().toString(), 1, 2)
            secrets(UUID.randomUUID().toString())
            //button("Click me", "https://google.com") // Buttons cannot be used with secrets (parties)
        }

        ipc.applicationManager.authorize(
            scopes = arrayOf("identify", "rpc.notifications.read"),
            clientId = "945428344806183003"
        )

        ipc.applicationManager.authenticate()

        // Subscribe to some events
        ipc.subscribe(DiscordEvent.ActivityJoin)
        ipc.subscribe(DiscordEvent.ActivitySpectate)
        ipc.subscribe(DiscordEvent.ActivityJoinRequest)
    }

    ipc.on<DisconnectedEvent> {
        logger.error("Disconnected!")
    }

    ipc.on<ErrorEvent> {
        logger.error("IPC communication error (${data.code}): ${data.message}")
    }

    ipc.on<ActivityJoinEvent> {
        logger.info("The user has joined someone else's party! ${data.secret}")
    }

    ipc.on<ActivityJoinRequestEvent> {
        logger.info("We have been invited to join ${data.userId}'s party!")

        ipc.activityManager.acceptJoinRequest(data.userId)
    }

    ipc.connect()
}
