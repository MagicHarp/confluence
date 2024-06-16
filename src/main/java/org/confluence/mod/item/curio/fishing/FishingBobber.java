package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.entity.fishing.CurioFishingHook;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class FishingBobber extends BaseCurioItem implements IFishingPower {
    public final CurioFishingHook.Variant variant;

    public FishingBobber(CurioFishingHook.Variant variant) {
        this.variant = variant;
    }

    @Override
    public float getFishingBonus() {
        return 10.0F;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), FishingBobber.class);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.fishing_bobber.info")
        };
    }
}
