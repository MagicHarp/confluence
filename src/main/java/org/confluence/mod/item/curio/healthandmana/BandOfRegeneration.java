package org.confluence.mod.item.curio.healthandmana;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class BandOfRegeneration extends BaseCurioItem {
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.level().getGameTime() % 20 == 0) {
            int amplifier = living.hasEffect(ModEffects.HONEY.get()) ? 2 : 1;
            living.heal(amplifier);
        }
    }
}
