package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
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
        return ModConfigs.MAGIC_QUIVER_PROJECTILE_BONUS.get().floatValue();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip"));
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip2"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.magic_quiver.info"),
            Component.translatable("item.confluence.magic_quiver.info2"),
            Component.translatable("item.confluence.magic_quiver.info3"),
            Component.translatable("item.confluence.magic_quiver.info4"),
            Component.translatable("item.confluence.magic_quiver.info5"),
            Component.translatable("item.confluence.magic_quiver.info6"),
            Component.translatable("item.confluence.magic_quiver.info7")

        };
    }
}
