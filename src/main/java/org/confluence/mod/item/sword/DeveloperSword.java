package org.confluence.mod.item.sword;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.entity.projectile.IceBladeSwordProjectile;
import org.confluence.mod.entity.projectile.SwordProjectile;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;

public class DeveloperSword extends IceBladeSwordItem implements ModRarity.Master {
    public DeveloperSword() {
        super(ModTiers.TITANIUM, Short.MAX_VALUE, 500.0f,
                new Item.Properties().rarity(ModRarity.MASTER));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return withColor(getDescriptionId());
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.AMETHYST_BLOCK_PLACE;
    }
}
