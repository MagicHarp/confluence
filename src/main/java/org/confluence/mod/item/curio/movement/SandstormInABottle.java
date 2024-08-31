package org.confluence.mod.item.curio.movement;

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

public class SandstormInABottle extends BaseCurioItem implements IOneTimeJump {
    public static final int TICKS = 20;
    public static final float SPEED = 0.45F;

    public SandstormInABottle(Rarity rarity) {
        super(rarity);
    }

    public SandstormInABottle() {
        super(ModRarity.GREEN);
    }

    @Override
    public int getJumpTicks() {
        return TICKS;
    }

    @Override
    public float getJumpSpeed() {
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IOneTimeJump.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sandstorm_in_a_bottle.info")
        };
    }
}
