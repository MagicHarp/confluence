package org.confluence.mod.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;

public class CuriosEquippedTrigger extends SimpleCriterionTrigger<CuriosEquippedTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(Confluence.MODID, "curios_equipped");
    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        return new TriggerInstance(pPredicate, ItemPredicate.fromJson(pJson.get("item")));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer pPlayer, ItemStack itemStack) {
        trigger(pPlayer, instance -> instance.matches(itemStack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate predicate;

        public TriggerInstance(ContextAwarePredicate pPlayer, ItemPredicate predicate) {
            super(ID, pPlayer);
            this.predicate = predicate;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonObject = super.serializeToJson(pConditions);
            jsonObject.add("item", predicate.serializeToJson());
            return jsonObject;
        }

        public boolean matches(ItemStack itemStack) {
            return predicate.matches(itemStack);
        }
    }
}
