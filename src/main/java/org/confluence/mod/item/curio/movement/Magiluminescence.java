package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerLightPacketS2C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class Magiluminescence extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("F0E9149C-E146-5D87-A319-A45CE63A2C65");
    private static final ImmutableMultimap<Attribute, AttributeModifier> SPEED = ImmutableMultimap.of(
        Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Magiluminescence", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        send(slotContext.entity(), true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        send(slotContext.entity(), false);
    }

    private static void send(LivingEntity living, boolean enable) {
        if (living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.ALL.noArg(),
                new PlayerLightPacketS2C(serverPlayer.getUUID(), enable)
            );
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magiluminescence.tooltip"));
        list.add(Component.translatable("item.confluence.magiluminescence.tooltip2"));
        list.add(Component.translatable("item.confluence.magiluminescence.tooltip3"));
    }
}
