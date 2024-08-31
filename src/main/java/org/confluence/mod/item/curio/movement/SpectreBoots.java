package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class SpectreBoots extends BaseSpeedBoots implements IMayFly {
    public static final Vector3f START_COLOR = FloatRGB.fromInteger(0x56b8e7).toVector();
    public static final Vector3f END_COLOR = FloatRGB.fromInteger(0x56b8e7).toVector();

    public SpectreBoots() {
        super(ModRarity.LIGHT_RED);
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
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IMayFly.TOOLTIP);
        list.add(BaseSpeedBoots.TOOLTIP);
    }
}
