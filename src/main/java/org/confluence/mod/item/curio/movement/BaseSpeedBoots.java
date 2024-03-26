package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.PlayerUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;
import java.util.function.Predicate;

public class BaseSpeedBoots extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("EE6FAFF5-A69D-6101-F82A-93E55A01F65E");

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        speedUp(slotContext.entity(), stack.getOrCreateTag(), 2, 40);
    }

    protected void speedUp(LivingEntity living, CompoundTag nbt, int tick, int max) {
        if (max > 70) return;
        if (living.getDeltaMovement().length() > 0) {
            if (tick == 1 || living.level().getGameTime() % tick == 0) {
                int speed = nbt.getInt("speed");
                if (speed < max) {
                    nbt.putInt("speed", speed);
                }
            }
        } else {
            nbt.putInt("speed", 0);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ImmutableMultimap.of(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Speed Boots", stack.getOrCreateTag().getInt("speed") * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return PlayerUtils.noSameCurio(slotContext.entity(), (Predicate<ItemStack>) itemStack -> itemStack.getItem() instanceof BaseSpeedBoots);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return PlayerUtils.noSameCurio(slotContext.entity(), (Predicate<ItemStack>) itemStack -> itemStack.getItem() instanceof BaseSpeedBoots);
    }
}
