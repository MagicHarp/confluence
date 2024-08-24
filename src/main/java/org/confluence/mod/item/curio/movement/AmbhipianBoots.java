package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class AmbhipianBoots extends BaseSpeedBoots implements IJumpBoost, IFallResistance {
    private static final Vector3f START_COLOR = FloatRGB.fromInteger(0x39b64e).toVector();
    private static final Vector3f END_COLOR = FloatRGB.fromInteger(0x39b64e).toVector();

    public AmbhipianBoots() {
        super(ModRarity.PINK);
    }

    @Override
    public Vector3f getParticleColorStart() {
        return START_COLOR;
    }

    @Override
    public Vector3f getParticleColorEnd() {
        return END_COLOR;
    }

    @Override
    public double getBoost() {
        return ModConfigs.AMBHIPIAN_BOOTS_JUMP_BOOST.get();
    }

    @Override
    public int getFallResistance() {
        return ModConfigs.AMBHIPIAN_BOOTS_FALL_RESISTANCE.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(IJumpBoost.TOOLTIP);
        list.add(IFallResistance.TOOLTIP);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.amphibian_boots.info"),
            Component.translatable("item.confluence.amphibian_boots.info2"),
            Component.translatable("item.confluence.amphibian_boots.info3"),
            Component.translatable("item.confluence.amphibian_boots.info4"),
            Component.translatable("item.confluence.amphibian_boots.info5"),
            Component.translatable("item.confluence.amphibian_boots.info6")
        };
    }
}
