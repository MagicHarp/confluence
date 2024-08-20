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

public class AmbhipianBoots extends BaseSpeedBoots implements IJumpBoost, IFallResistance {

    public AmbhipianBoots() {
        super(ModRarity.PINK);
    }

    @Override
    public Vector3f getParticleColorStart() {
        return FloatRGB.fromInteger(0x39b64e).toVector();
    }

    @Override
    public Vector3f getParticleColorEnd() {
        return FloatRGB.fromInteger(0x39b64e).toVector();
    }

    @Override
    public double getBoost() {
        return 1.6;
    }

    @Override
    public int getFallResistance() {
        return 7;
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
            Component.translatable("item.confluence.amphibian_boots.info")
        };
    }
}
