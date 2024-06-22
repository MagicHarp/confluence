package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CloudInABottle extends BaseCurioItem implements IMultiJump {
    public CloudInABottle(Rarity rarity) {
        super(rarity);
    }

    public CloudInABottle() {
        super();
    }

    @Override
    public double getJumpSpeed() {
        return ModConfigs.CLOUD_IN_A_BOTTLE_JUMP_SPEED.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.cloud_in_a_bottle.info"),
            Component.translatable("item.confluence.cloud_in_a_bottle.info2"),
            Component.translatable("item.confluence.cloud_in_a_bottle.info3")
        };
    }
}
