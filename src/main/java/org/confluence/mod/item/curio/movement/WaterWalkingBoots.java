package org.confluence.mod.item.curio.movement;

import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class WaterWalkingBoots extends BaseCurioItem implements IFluidWalk {
    public WaterWalkingBoots() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), IFluidWalk.class);
    }
}