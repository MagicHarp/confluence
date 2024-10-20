package org.confluence.mod.common.init;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.component.LootComponent;
import org.confluence.mod.common.component.RarityComponent;

public final class ModDataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPE = DeferredRegister.createDataComponents(Confluence.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LootComponent>> LOOT = DATA_COMPONENT_TYPE.registerComponentType(
            "loot", builder -> builder.persistent(LootComponent.CODEC).networkSynchronized(LootComponent.STREAM_CODEC)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RarityComponent>> RARITY = DATA_COMPONENT_TYPE.registerComponentType(
            "rarity", builder -> builder.persistent(RarityComponent.CODEC).networkSynchronized(RarityComponent.STREAM_CODEC)
    );
}
