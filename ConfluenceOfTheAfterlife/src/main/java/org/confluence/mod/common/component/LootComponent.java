package org.confluence.mod.common.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record LootComponent(ResourceLocation lootTable) {
    public static final Codec<LootComponent> CODEC = ResourceLocation.CODEC.xmap(LootComponent::new, LootComponent::lootTable);
    public static final StreamCodec<ByteBuf, LootComponent> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,LootComponent::lootTable,
            LootComponent::new
    );
}
