package org.confluence.mod.item.curio.movement;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.combat.IFireImmune;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ObsidianHorseshoe extends BaseCurioItem implements IFallResistance, IFireImmune {
    public ObsidianHorseshoe() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getFallResistance() {
        return -1;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return LuckyHorseshoe.LUCKY;
    }
}
