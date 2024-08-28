package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class LightningBoots extends BaseSpeedBoots implements IMayFly {
    public static final UUID SPEED_UUID = UUID.fromString("6270BA48-067E-4093-1366-E01CDB4888F3");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "Lightning Boots", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Vector3f START_COLOR = FloatRGB.fromInteger(0xfcd60e).toVector();
    public static final Vector3f END_COLOR = FloatRGB.fromInteger(0xfcd60e).toVector();

    public LightningBoots(Rarity rarity) {
        super(rarity);
    }

    public LightningBoots() {
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
    public int getFlyTicks() {
        return ModConfigs.LIGHTNING_BOOTS_FLY_TICKS.get();
    }

    @Override
    public double getFlySpeed() {
        return ModConfigs.LIGHTNING_BOOTS_FLY_SPEED.get();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ImmutableMultimap.of(
            Attributes.MOVEMENT_SPEED, SPEED_MODIFIER,
            Attributes.MOVEMENT_SPEED, getSpeedModifier(stack)
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.lightning_boots.tooltip"));
    }
}
