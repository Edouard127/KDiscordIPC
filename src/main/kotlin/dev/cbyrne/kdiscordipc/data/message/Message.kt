package dev.cbyrne.kdiscordipc.data.message

import dev.cbyrne.kdiscordipc.data.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    @SerialName("channel_id") val channelId: String,
    val author: User? = null,
    val content: String,
    val timestamp: String,
    @SerialName("edited_timestamp") val editedTimestamp: String,
    val tts: Boolean,
    @SerialName("mention_everyone") val mentionEveryone: Boolean,
    @SerialName("mentions") val mentions: List<User>,
    @SerialName("mention_roles") val mentionRoles: List<Role>,
    @SerialName("mention_channels") val mentionChannels: List<ChannelMention>,
    @SerialName("author_color") val authorColor: String,
    val attachments: List<Attachment>?,
    val embeds: List<Embed>?,
    val reactions: List<Reaction>?,
    val nonce: String?,
    val pinned: Boolean,
    @SerialName("webhook_id") val webhookId: String?,
    @SerialName("type") val messageType: MessageType,
    val activity: MessageActivity?,
) {
    @Serializable
    enum class MessageType(val value: Int) {
        DEFAULT(0),
        RECIPIENT_ADD(1),
        RECIPIENT_REMOVE(2),
        CALL(3),
        CHANNEL_NAME_CHANGE(4),
        CHANNEL_ICON_CHANGE(5),
        CHANNEL_PINNED_MESSAGE(6),
        GUILD_MEMBER_JOIN(7),
        USER_PREMIUM_GUILD_SUBSCRIPTION(8),
        USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1(9),
        USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2(10),
        USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3(11),
        CHANNEL_FOLLOW_ADD(12),
        GUILD_DISCOVERY_DISQUALIFIED(14),
        GUILD_DISCOVERY_REQUALIFIED(15),
        GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING(16),
        GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING(17),
        THREAD_CREATED(18),
        REPLY(19),
        CHAT_INPUT_COMMAND(20),
        THREAD_STARTER_MESSAGE(21),
        GUILD_INVITE_REMINDER(22),
        CONTEXT_MENU_COMMAND(23),
        AUTO_MODERATOR_ACTION(24),
        ROLE_SUBSCRIPTION_PURCHASE(25),
        INTERACTION_PREMIUM_UPSELL(26),
        STAGE_START(27),
        STAGE_END(28),
        STAGE_SPEAKER(29),
        STAGE_TOPIC(31),
        GUILD_APPLICATION_PREMIUM_SUBSCRIPTION(32),
        GUILD_INCIDENT_ALERT_MODE_ENABLED(36),
        GUILD_INCIDENT_ALERT_MODE_DISABLED(37),
        GUILD_INCIDENT_REPORT_RAID(38),
        GUILD_INCIDENT_REPORT_FALSE_ALARM(39),
        PURCHASE_NOTIFICATION(44),
    }

    @Serializable
    data class MessageActivity(
        val type: ActivityType,
        val partyId: String?,
    ) {
        @Serializable
        enum class ActivityType(val value: Int) {
            JOIN(1),
            SPECTATE(2),
            LISTEN(3),
            JOIN_REQUEST(5),
        }
    }
}

@Serializable
data class Role(
    val id: String,
    val name: String,
    val color: Int,
    val hoist: Boolean,
    val position: Int,
    val permissions: Int,
    val managed: Boolean,
    val mentionable: Boolean,
    val tags: RoleTags,
) {
    @Serializable
    data class RoleTags(
        @SerialName("bot_id") val botId: String?,
        @SerialName("integration_id") val integrationId: String?,
        @SerialName("premium_subscriber") val premiumSubscriber: Boolean?,
        @SerialName("subscription_listing_id") val subscriptionListingId: String?,
        @SerialName("available_for_purchase") val availableForPurchase: Boolean?,
        @SerialName("guild_connections") val guildConnections: Boolean?,
    )
}

@Serializable
data class ChannelMention(
    val id: String,
    @SerialName("guild_id") val guildId: String,
    val type: Int,
    val name: String,
) {
    @Serializable
    enum class ChannelType(val value: Int) {
        GUILD_TEXT(0),
        DM(1),
        GUILD_VOICE(2),
        GROUP_DM(3),
        GUILD_CATEGORY(4),
        GUILD_ANNOUNCEMENT(5),
        ANNOUNCEMENT_THREAD(10),
        PUBLIC_THREAD(11),
        PRIVATE_THREAD(12),
        GUILD_STAGE_VOICE(13),
        GUILD_DIRECTORY(14),
        GUILD_FORUM(15),
        GUILD_MEDIA(16),
    }
}

@Serializable
data class Attachment(
    val id: String,
    @SerialName("filename") val fileName: String,
    @SerialName("description") val fileDescription: String?,
    @SerialName("content_type") val contentType: String?,
    @SerialName("size") val fileSize: Int,
    @SerialName("url") val fileUrl: String,
    @SerialName("proxy_url") val proxyUrl: String,
    @SerialName("height") val imageHeight: Int?,
    @SerialName("width") val imageWidth: Int?,
    val ephemeral: Boolean?,
    @SerialName("duration_secs") val duration: Float?,
    val waveform: String?,
    val flags: Int?,
) {
    @Serializable
    data class AttachmentDimensions(
        val height: Int,
        val width: Int,
    )
}

@Serializable
data class Embed(
    val title: String?,
    val type: String,
    val description: String?,
    val url: String?,
    val timestamp: String?,
    val color: Int?,
    val footer: EmbedFooter?,
    val image: EmbedImage?,
    val thumbnail: EmbedThumbnail?,
    val video: EmbedVideo?,
    val provider: EmbedProvider?,
    val author: EmbedAuthor?,
    val fields: List<EmbedField>,
) {
    @Serializable
    data class EmbedFooter(
        val text: String,
        val iconUrl: String?,
        val proxyIconUrl: String?,
    )

    @Serializable
    data class EmbedImage(
        val url: String,
        val proxyUrl: String,
        val height: Int,
        val width: Int,
    )

    @Serializable
    data class EmbedThumbnail(
        val url: String,
        val proxyUrl: String,
        val height: Int,
        val width: Int,
    )

    @Serializable
    data class EmbedVideo(
        val url: String,
        val height: Int,
        val width: Int,
    )

    @Serializable
    data class EmbedProvider(
        val name: String,
        val url: String,
    )

    @Serializable
    data class EmbedAuthor(
        val name: String,
        val url: String?,
        val iconUrl: String?,
        val proxyIconUrl: String?,
    )

    @Serializable
    data class EmbedField(
        val name: String,
        val value: String,
        val inline: Boolean,
    )
}

@Serializable
data class Reaction(
    val count: Int,
    val me: Boolean,
    val emoji: Emoji,
) {
    @Serializable
    data class Emoji(
        val id: String?,
        val name: String,
        val animated: Boolean?,
    )
}
