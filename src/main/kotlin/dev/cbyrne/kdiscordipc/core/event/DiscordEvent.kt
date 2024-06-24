package dev.cbyrne.kdiscordipc.core.event

import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
/**
 * Represents a Discord event.
 * @param eventName The name of the event.
 *
 * @see <a href="https://discord.com/developers/docs/developer-tools/embedded-app-sdk#sdk-events">Discord SDK Events</a>
 */
enum class DiscordEvent(val eventName: String) {
    /**
     * Received when a user's voice state changes in a subscribed voice channel (mute, volume, etc).
     *
     * Required Scopes:
     * - `rpc.voice.read`
     *
     * Take note that the rpc.* scopes are only available to approved applications.
     */
    VoiceStateUpdate("VOICE_STATE_UPDATE"),

    /**
     * Received when a user in a subscribed voice channel speaks.
     *
     * Required Scopes:
     * - `rpc.voice.read`
     *
     * Take note that the rpc.* scopes are only available to approved applications.
     */
    SpeakingStart("SPEAKING_START"),

    /**
     * Received when a user in a subscribed voice channel stops speaking.
     *
     * Required Scopes:
     * - `rpc.voice.read`
     *
     * Take note that the rpc.* scopes are only available to approved applications.
     */
    SpeakingStop("SPEAKING_STOP"),

    /**
     * Received when a user changes the layout mode in the Discord client.
     *
     * Required Scopes:
     * - None
     *
     * Only available on the mobile Discord Client.
     */
    ActivityLayoutModeUpdate("ACTIVITY_LAYOUT_MODE_UPDATE"),

    /**
     * Received when screen orientation changes.
     *
     * Required Scopes:
     * - None
     *
     * Only available on the mobile Discord Client.
     */
    OrientationUpdate("ORIENTATION_UPDATE"),

    /**
     * Received when the current user object changes.
     *
     * Required Scopes:
     * - `identify`
     */
    CurrentUserUpdate("CURRENT_USER_UPDATE"),

    /**
     * Received when Android or iOS thermal states are surfaced to the Discord mobile app.
     *
     * Required Scopes:
     * - None
     *
     * Only available on the mobile Discord Client.
     */
    ThermalStateUpdate("THERMAL_STATE_UPDATE"),

    /**
     * Received when the number of instance participants changes.
     *
     * Required Scopes:
     * - None
     *
     * Requires to be authenticated
     */
    ActivityInstanceParticipantsUpdate("ACTIVITY_INSTANCE_PARTICIPANTS_UPDATE"),
}
