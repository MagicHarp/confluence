package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.ILavaImmune;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoltenCharm extends BaseCurioItem implements IFireImmune, ILavaImmune {
    public MoltenCharm() {
        super(ModRarity.PINK);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IFireImmune.TOOLTIP);
        list.add(ILavaImmune.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.molten_charm.info"),
                Component.translatable("item.confluence.molten_charm.info2"),
                Component.translatable("item.confluence.molten_charm.info3"),
                Component.translatable("item.confluence.molten_charm.info4"),
                Component.translatable("item.confluence.molten_charm.info5"),
                Component.translatable("item.confluence.molten_charm.info6"),
                Component.translatable("item.confluence.molten_charm.info7")
        };
    }
}
