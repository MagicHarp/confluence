package org.confluence.mod.item.fishing;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.fishing.LavaFishingHook;
import org.confluence.mod.misc.ModRarity;

public class HotlineFishingHook extends AbstractFishingRod {
    public HotlineFishingHook() {
        super(new Properties().rarity(ModRarity.ORANGE).fireResistant().durability(256));
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 4;
    }

    @Override
    protected FishingHook getHook(ItemStack itemStack, Player player, Level level, int luckBonus, int speedBonus) {
        return new LavaFishingHook(player, level, luckBonus, speedBonus);
    }
}
