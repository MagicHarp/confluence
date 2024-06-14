package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.miscellaneous.IFlowerBoots;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FairyBoots extends BaseSpeedBoots implements IMayFly, IFlowerBoots {
    public FairyBoots() {
        super(ModRarity.PINK);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMayFly.TOOLTIP);
        list.add(BaseSpeedBoots.TOOLTIP);
        list.add(IFlowerBoots.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.fairy_boots.info"),
                Component.translatable("item.confluence.fairy_boots.info2")
        };
    }
}
