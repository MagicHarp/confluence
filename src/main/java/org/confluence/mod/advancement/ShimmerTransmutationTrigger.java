package org.confluence.mod.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import org.confluence.mod.Confluence;

public class ShimmerTransmutationTrigger extends SimpleCriterionTrigger<ShimmerTransmutationTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(Confluence.MODID, "shimmer_transmutation");

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(contextAwarePredicate, EntityPredicate.fromJson(jsonObject.get("entity")));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer pPlayer, ItemEntity entity) {
        trigger(pPlayer, instance -> instance.matches(pPlayer, entity));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final EntityPredicate entityPredicate;

        public TriggerInstance(ContextAwarePredicate pPlayer, EntityPredicate entityPredicate) {
            super(ID, pPlayer);
            this.entityPredicate = entityPredicate;
        }

        public boolean matches(ServerPlayer serverPlayer, ItemEntity entity) {
            return entityPredicate.matches(serverPlayer, entity);
        }
    }
}
