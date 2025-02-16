package dev.cbyrne.kdiscordipc.data.user

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = UserFlag.Companion.UserFlagSerializer::class)
enum class UserFlag(val value: Int) {
    /**
     * This user is a Discord Partner
     */
    Partner(2),

    /**
     * This user is a HypeSquad Events participant
     */
    HypeSquadEvents(4),

    /**
     * This user is part of the House Bravery
     */
    HypeSquadHouse1(64),

    /**
     * This user is part of the House Brilliance
     */
    HypeSquadHouse2(128),

    /**
     * This user is part of the House Balance
     */
    HypeSquadHouse3(256);

    companion object {
        open class UserFlagSerializer : KSerializer<UserFlag> {
            override val descriptor: SerialDescriptor =
                PrimitiveSerialDescriptor("dev.cbyrne.kdiscordipc.data.user", PrimitiveKind.INT)

            override fun deserialize(decoder: Decoder) =
                decoder.decodeInt().let { value ->
                    UserFlag.entries.first { it.value == value }
                }

            override fun serialize(encoder: Encoder, value: UserFlag) = encoder.encodeInt(value.value)
        }
    }
}
