package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicQuiver extends BaseCurioItem implements IProjectileAttack, IMagicQuiver {
    public MagicQuiver() {
        super(ModRarity.LIGHT_RED);
    }

    public MagicQuiver(Rarity rarity) {
        super(rarity);
    }

    @Override
    public float getProjectileBonus() {
        return 0.1F;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip"));
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip2"));
    }
}
