package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoltenSkullRose extends BaseCurioItem implements IFireImmune, ILavaImmune, ILavaHurtReduce {
    public MoltenSkullRose() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFireImmune.TOOLTIP);
        list.add(ILavaImmune.TOOLTIP);
        list.add(ILavaHurtReduce.TOOLTIP);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.molten_skull_rose.info"),
            Component.translatable("item.confluence.molten_skull_rose.info2"),
            Component.translatable("item.confluence.molten_skull_rose.info3")
        };
    }
}
