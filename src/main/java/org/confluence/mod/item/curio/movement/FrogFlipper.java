package org.confluence.mod.item.curio.movement;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.misc.ModConfigs;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class FrogFlipper extends FrogLeg {
    @Override
    public int getFallResistance() {
        return ModConfigs.FROG_FLIPPER_FALL_RESISTANCE.get();
    }

    @Override
    public double getBoost() {
        return ModConfigs.FROG_FLIPPER_JUMP_BOOST.get();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return Flipper.SWIM_SPEED;
    }
}
