package org.confluence.mod.item.curio.expert;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.ShieldOfCthulhuPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.IEntity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ShieldOfCthulhu extends BaseCurioItem implements ModRarity.Expert, ICriticalHit {
    public static final UUID ARMOR_UUID = UUID.fromString("C99AA305-E0CF-9E8F-06AB-8F61C28EAF51");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ARMOR = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Shield Of Cthulhu", 2, AttributeModifier.Operation.ADDITION)
    );

    public ShieldOfCthulhu() {
        super(ModRarity.EXPERT);
    }

    @Override
    public double getChance() {
        return 0.04;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        sendMsg(slotContext.entity(), true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        sendMsg(slotContext.entity(), false);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ARMOR;
    }

    public static void apply(LivingEntity living) {
        float f = living.getYRot() * (Mth.PI / 180F);
        float factor = living.onGround() ? 1.6F : 1.2F;
        living.setDeltaMovement(living.getDeltaMovement().add(-Mth.sin(f) * factor, 0.0D, Mth.cos(f) * factor));
    }

    public static boolean isInvul(LivingEntity living) {
        if (CuriosUtils.noSameCurio(living, CurioItems.SHIELD_OF_CTHULHU.get())) return false;
        return ((IEntity) living).c$isOnCthulhuSprinting();
    }

    private static void sendMsg(LivingEntity living, boolean has) {
        if (living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new ShieldOfCthulhuPacketS2C(has)
            );
        }
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.shield_of_cthulhu.info"),
            Component.translatable("item.confluence.shield_of_cthulhu.info2"),
            Component.translatable("item.confluence.shield_of_cthulhu.info3")
        };
    }
}
