package org.confluence.mod.common.advancement;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;

public final class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Confluence.MODID);

    public final static DeferredHolder<CriterionTrigger<?>, CuriosEquippedTrigger> CURIOS_EQUIPPED = TRIGGERS.register("curios_equipped", CuriosEquippedTrigger::new);
    public final static DeferredHolder<CriterionTrigger<?>, ShimmerTransmutationTrigger> SHIMMER_TRANSMUTATION = TRIGGERS.register("shimmer_transmutation", ShimmerTransmutationTrigger::new);
}
