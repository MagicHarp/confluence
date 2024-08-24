package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
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

public class FrostsparkBoots extends LightningBoots {
    public static final UUID SPEED_UUID = UUID.fromString("FF5835B7-FA33-A02D-A91F-E0043403CE69");
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "Frostspark Boots", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final Vector3f START_COLOR = FloatRGB.fromInteger(0x69cffc).toVector();
    private static final Vector3f END_COLOR = FloatRGB.fromInteger(0x69cffc).toVector();

    public FrostsparkBoots() {
        super(ModRarity.LIME);
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
        return ModConfigs.FROSTSPARK_BOOTS_FLY_TICKS.get();
    }

    @Override
    public double getFlySpeed() {
        return ModConfigs.FROSTSPARK_BOOTS_FLY_SPEED.get();
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        IceSkates.tick(slotContext.entity(), stack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living != null) {
            if (stack.getOrCreateTag().getBoolean("onPosIsIce")) {
                return ImmutableMultimap.of(
                    Attributes.MOVEMENT_SPEED, SPEED_MODIFIER,
                    Attributes.MOVEMENT_SPEED, getSpeedModifier(stack),
                    Attributes.MOVEMENT_SPEED, IceSkates.MODIFIER
                );
            } else {
                AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null) attribute.removeModifier(IceSkates.SPEED_UUID);
            }
        }
        return super.getAttributeModifiers(slotContext, uuid, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.frostspark_boots.tooltip"));
    }
}
