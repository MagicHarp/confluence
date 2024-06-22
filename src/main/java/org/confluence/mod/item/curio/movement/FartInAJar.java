package org.confluence.mod.item.curio.movement;

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

public class FartInAJar extends BaseCurioItem implements IMultiJump {
    public FartInAJar(Rarity rarity) {
        super(rarity);
    }

    public FartInAJar() {
        super(ModRarity.GREEN);
    }

    @Override
    public double getJumpSpeed() {
        return ModConfigs.FART_IN_A_JAR_JUMP_SPEED.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMultiJump.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.fart_in_a_jar.info"),
            Component.translatable("item.confluence.fart_in_a_jar.info2"),
            Component.translatable("item.confluence.fart_in_a_jar.info3")
        };
    }
}
