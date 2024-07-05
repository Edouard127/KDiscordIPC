package dev.cbyrne.kdiscordipc.core.event

import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
/**
 * Represents a Discord event.
 * @param eventName The name of the event.
 *
 * Note that some events cannot be used without the required scope.
 * Take also note that some scopes require a verified application.
 * @see <a href="https://github.com/discord/discord-api-docs/blob/4c29f142415710e1f2b5628a199ae192f25aefe9/docs/topics/OAuth2.md#oauth2-scopes">Discord OAuth2 Scopes</a>
 * @see <a href="https://discord.com/developers/docs/topics/rpc#commands-and-events>Discord RPC Documentation</a>
 */
enum class DiscordEvent(val eventName: String) {
    /**
     * Sent when a subscribed server's state changes
     */
    GuildStatus("GUILD_STATUS"),

    /**
     * 	Sent when a guild is created/joined on the client
     */
    GuildCreate("GUILD_CREATE"),

    /**
     * Sent when a channel is created/joined on the client
     */
    ChannelCreate("CHANNEL_CREATE"),

    /**
     * Sent when the client joins a voice channel
     */
    VoiceChannelSelect("VOICE_CHANNEL_SELECT"),

    /**
     * Sent when a user joins a subscribed voice channel
     */
    VoiceStateCreate("VOICE_STATE_CREATE"),

    /**
     * Sent when a user's voice state changes in a subscribed voice channel (mute, volume, etc.)
     */
    VoiceStateUpdate("VOICE_STATE_UPDATE"),

    /**
     * Sent when a user parts a subscribed voice channel
     */
    VoiceStateDelete("VOICE_STATE_DELETE"),

    /**
     * Sent when the client's voice settings update
     */
    VoiceSettingsUpdate("VOICE_SETTINGS_UPDATE"),

    /**
     * Sent when the client's voice connection status changes
     */
    VoiceConnectionStatus("VOICE_CONNECTION_STATUS"),

    /**
     * Sent when a user in a subscribed voice channel speaks
     */
    SpeakingStart("SPEAKING_START"),

    /**
     * Sent when a user in a subscribed voice channel stops speaking
     */
    SpeakingStop("SPEAKING_STOP"),

    /**
     * Sent when a message is created in a subscribed text channel
     */
    MessageCreate("MESSAGE_CREATE"),

    /**
     * Sent when a message is updated in a subscribed text channel
     */
    MessageUpdate("MESSAGE_UPDATE"),

    /**
     * Sent when a message is deleted in a subscribed text channel
     */
    MessageDelete("MESSAGE_DELETE"),

    /**
     * Sent when the client receives a notification (mention or new message in eligible channels)
     */
    NotificationCreate("NOTIFICATION_CREATE"),

    /**
     * Sent when the user clicks a Rich Presence join invite in chat to join a game
     *
     * If your application is not verified, you will not receive dispatches for this event.
     * You must add the user you want to test with into Developer Portal > Applications > Your Application > App Testers
     */
    ActivityJoin("ACTIVITY_JOIN"),

    /**
     * Sent when the user clicks a Rich Presence spectate invite in chat to spectate a game
     *
     * If your application is not verified, you will not receive dispatches for this event.
     * You must add the user you want to test with into Developer Portal > Applications > Your Application > App Testers
     */
    ActivitySpectate("ACTIVITY_SPECTATE"),

    /**
     * Sent when the user receives a Rich Presence Ask to Join request
     *
     * If your application is not verified, you will not receive dispatches for this event.
     * You must add the user you want to test with into Developer Portal > Applications > Your Application > App Testers
     */
    ActivityJoinRequest("ACTIVITY_JOIN_REQUEST"),
}
