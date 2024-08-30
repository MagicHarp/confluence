package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SharkToothNecklace extends BaseCurioItem implements IArmorPass {
    @Override
    public int getPassValue() {
        return ModConfigs.SHARK_TOOTH_NECKLACE_ARMOR_PASS.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(getArmorPassToolTip());
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.shark_tooth_necklace.info")
        };
    }
}
