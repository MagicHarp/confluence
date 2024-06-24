package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ObsidianSkullRose extends BaseCurioItem implements IFireImmune, ILavaHurtReduce {
    public ObsidianSkullRose() {
        super(ModRarity.PINK);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFireImmune.TOOLTIP);
        list.add(ILavaHurtReduce.TOOLTIP);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.obsidian_skull_rose.info")
        };
    }
}
