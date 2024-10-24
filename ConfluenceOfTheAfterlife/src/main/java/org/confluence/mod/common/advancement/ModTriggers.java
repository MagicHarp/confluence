package org.confluence.mod.common.advancement;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;

import java.util.function.Supplier;

public final class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TYPES = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Confluence.MODID);

    public final static Supplier<ShimmerTransmutationTrigger> SHIMMER_TRANSMUTATION = TYPES.register("shimmer_transmutation", ShimmerTransmutationTrigger::new);
}
