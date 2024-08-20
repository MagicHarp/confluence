package org.confluence.mod.item.curio.movement;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class DuneriderBoots extends BaseSpeedBoots {
    public DuneriderBoots() {
        super(ModRarity.BLUE);
    }

    @Override
    public Vector3f getParticleColorStart() {
        return FloatRGB.fromInteger(0xab603f).toVector();
    }

    @Override
    public Vector3f getParticleColorEnd() {
        return FloatRGB.fromInteger(0xab603f).toVector();
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        CompoundTag nbt = stack.getOrCreateTag();
        if (living.level().getBlockState(living.getOnPos()).is(BlockTags.SAND)) {
            speedUp(slotContext, nbt, 2, 70);
        } else {
            speedUp(slotContext, nbt, 1, 40);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.dunerider_boots.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.dunerider_boots.info"),
            Component.translatable("item.confluence.dunerider_boots.info2"),
            Component.translatable("item.confluence.dunerider_boots.info3"),
            Component.translatable("item.confluence.dunerider_boots.info4")
        };
    }
}
