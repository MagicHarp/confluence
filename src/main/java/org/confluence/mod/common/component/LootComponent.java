package org.confluence.mod.common.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Map;

public record LootComponent(ResourceLocation lootTable) {
    public static final Codec<LootComponent> CODEC = ResourceLocation.CODEC.xmap(LootComponent::new, LootComponent::lootTable);
    public static final StreamCodec<ByteBuf, LootComponent> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,LootComponent::lootTable,
            LootComponent::new
    );
}
