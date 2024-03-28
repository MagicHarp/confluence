package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.SpeedBootsNBTPacketC2S;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class BaseSpeedBoots extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("EE6FAFF5-A69D-6101-F82A-93E55A01F65E");

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return EMPTY_TOOLTIP;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        speedUp(slotContext, stack.getOrCreateTag(), 1, 40);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        stack.getOrCreateTag().putInt("speed", 0);
    }

    protected void speedUp(SlotContext slotContext, CompoundTag nbt, int addition, int max) {
        LivingEntity living = slotContext.entity();
        if (living.level().isClientSide) {
            int speed = nbt.getInt("speed");
            if (living.zza > 0) {
                int actually = Math.min(max - speed, addition);
                if (actually > 0) {
                    NetworkHandler.CHANNEL.sendToServer(new SpeedBootsNBTPacketC2S(slotContext.index(), actually));
                }
            } else if (speed != 0) {
                NetworkHandler.CHANNEL.sendToServer(new SpeedBootsNBTPacketC2S(slotContext.index(), 0));
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ImmutableMultimap.of(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Speed Boots", stack.getOrCreateTag().getInt("speed") * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), (Predicate<ItemStack>) itemStack -> itemStack.getItem() instanceof BaseSpeedBoots);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), (Predicate<ItemStack>) itemStack -> itemStack.getItem() instanceof BaseSpeedBoots);
    }
}
