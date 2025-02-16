package dev.cbyrne.example

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.DiscordEvent
import dev.cbyrne.kdiscordipc.core.event.impl.*
import dev.cbyrne.kdiscordipc.core.event.impl.DisconnectedEvent
import dev.cbyrne.kdiscordipc.data.activity.*
import dev.cbyrne.kdiscordipc.data.overlay.ActivityActionType
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
//        ipc.applicationManager.authorize(
//            scopes = arrayOf("identify", "rpc.notifications.read"),
//            clientId = "945428344806183003"
//        )

        logger.info("Ready! (${data.user.username}#${data.user.discriminator})")

        // Subscribe to some events
        //ipc.subscribe(DiscordEvent.LobbyUpdate)
        ipc.subscribe(DiscordEvent.ActivityJoin)
        ipc.subscribe(DiscordEvent.ActivitySpectate)
        ipc.subscribe(DiscordEvent.ActivityJoinRequest)
        ipc.subscribe(DiscordEvent.OverlayUpdate)

        // Set the user's activity (a.k.a. rich presence)
        ipc.activityManager.setActivity("Hello") {
            largeImage("https://avatars.githubusercontent.com/u/71222289?v=4", "KDiscordIPC")
            smallImage("https://avatars.githubusercontent.com/u/71222289?v=4", "Testing")

            party(UUID.randomUUID().toString(), 1, 2)
            secrets(UUID.randomUUID().toString())
            //button("Click me", "https://google.com") // Buttons cannot be used with secrets (parties)
        }

        println(ipc.activityManager.invitePlayer("295974862646804480", ActivityActionType.Join))

        ipc.applicationManager.authenticate()
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
