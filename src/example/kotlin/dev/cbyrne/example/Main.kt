package dev.cbyrne.example

import dev.cbyrne.kdiscordipc.KDiscordIPC
import dev.cbyrne.kdiscordipc.core.event.DiscordEvent
import dev.cbyrne.kdiscordipc.core.event.impl.*
import dev.cbyrne.kdiscordipc.core.event.impl.DisconnectedEvent
import dev.cbyrne.kdiscordipc.data.activity.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

val logger: Logger = LogManager.getLogger("Example")

suspend fun main() {
    val ipc = KDiscordIPC("945428344806183003")
    logger.info("Starting example!")

    ipc.on<ReadyEvent> {
        logger.info("Ready! (${data.user.username}#${data.user.discriminator})")

        // Spawn new coroutine
        ipc.scope.launch {
            while(true) {
                // Set the user's activity (a.k.a. rich presence)
                ipc.activityManager.setActivity("Hello") {
                    largeImage("https://avatars.githubusercontent.com/u/71222289?v=4", "KDiscordIPC")
                    smallImage("https://avatars.githubusercontent.com/u/71222289?v=4", "Testing")

                    party(UUID.randomUUID().toString(), 1, 2)
                    secrets(UUID.randomUUID().toString())
                    //button("Click me", "https://google.com") // Buttons cannot be used with secrets (parties)
                }

                delay(5000)
            }
        }

        ipc.applicationManager.authenticate() // Required to listen for party updates

        // Subscribe to some events
        //ipc.subscribe(DiscordEvent.GuildStatus)
        //ipc.subscribe(DiscordEvent.VoiceStateUpdate)
        //ipc.subscribe(DiscordEvent.VoiceSettingsUpdate)
        ipc.subscribe(DiscordEvent.ActivityJoin)
        ipc.subscribe(DiscordEvent.ActivityJoinRequest)
        ipc.subscribe(DiscordEvent.ActivitySpectate)
    }

    ipc.on<DisconnectedEvent> {
        logger.error("Disconnected!")
    }

    ipc.on<ErrorEvent> {
        logger.error("IPC communication error (${data.code}): ${data.message}")
    }

    ipc.on<CurrentUserUpdateEvent> {
        logger.info("Current user updated!")
    }

    ipc.on<ActivityJoinEvent> {
        logger.info("The user has joined someone else's party! ${data.secret}")
    }

    ipc.on<ActivityInviteEvent> {
        logger.info("We have been invited to join ${data.user.username}'s party! (${data.activity.party.id})")

        ipc.activityManager.acceptInvite(data)
    }

    ipc.on<VoiceSettingsUpdateEvent> {
        logger.info("Voice settings updated! User is now ${if (this.data.mute) "" else "not "}muted")
    }

    ipc.connect()
}
