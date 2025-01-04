package spaghetti.remarkablerats.network

import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import spaghetti.remarkablerats.id

// @JvmRecord
data class EntityIdPayload(val entityId: Int) : CustomPayload {
    override fun getId(): CustomPayload.Id<out CustomPayload?> {
        return ID
    }

    companion object {
        val ID: CustomPayload.Id<EntityIdPayload?> = CustomPayload.Id<EntityIdPayload?>(
                id("entity_id"))
        val PACKET_CODEC: PacketCodec<RegistryByteBuf, EntityIdPayload> = PacketCodec.tuple(PacketCodecs.INTEGER,
                EntityIdPayload::entityId) { entityId: Int -> EntityIdPayload(entityId) }
    }
}
