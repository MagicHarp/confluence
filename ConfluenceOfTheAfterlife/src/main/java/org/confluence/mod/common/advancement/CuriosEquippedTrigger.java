package org.confluence.mod.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CuriosEquippedTrigger extends SimpleCriterionTrigger<CuriosEquippedTrigger.TriggerInstance> {
    public static final ResourceLocation ID = Confluence.asResource("curios_equipped");

    public void trigger(ServerPlayer pPlayer, ItemStack itemStack) {
        trigger(pPlayer, instance -> instance.matches(itemStack));
    }

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item)
        ).apply(instance, TriggerInstance::new));

        public boolean matches(ItemStack itemStack) {
            return item.isEmpty() || item.get().test(itemStack);
        }

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return player;
        }
    }
}
