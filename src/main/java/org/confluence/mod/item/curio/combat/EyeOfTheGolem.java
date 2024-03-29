package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

public class EyeOfTheGolem extends BaseCurioItem implements ICriticalHit {
    @Override
    public float getChance() {
        return 0.1F;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            freshChance(player);
        }
    }
}
