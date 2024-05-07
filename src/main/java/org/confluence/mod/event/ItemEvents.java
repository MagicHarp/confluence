package org.confluence.mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.capability.prefix.PrefixType;
import org.confluence.mod.effect.harmful.CursedEffect;
import org.confluence.mod.effect.harmful.SilencedEffect;
import org.confluence.mod.effect.harmful.StonedEffect;
import org.confluence.mod.misc.ModTags;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ItemEvents {
    @SubscribeEvent
    public static void itemAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND) return;
        ItemStack itemStack = event.getItemStack();
        PrefixProvider.getPrefix(itemStack).ifPresent(itemPrefix -> {
            if (itemPrefix.type != PrefixType.UNIVERSAL && itemPrefix.type != PrefixType.MELEE) return;
            if (itemPrefix.attackDamage != 0.0) {
                event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(ItemPrefix.ATTACK_DAMAGE_UUID_HAND,
                    "Item Prefix", itemPrefix.attackDamage, MULTIPLY_TOTAL));
            }
            if (itemPrefix.type == PrefixType.MELEE && itemPrefix.attackSpeed != 0.0) {
                event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ItemPrefix.ATTACK_SPEED_UUID_HAND,
                    "Item Prefix", itemPrefix.attackSpeed, MULTIPLY_TOTAL));
            }
            if (itemPrefix.knockBack != 0.0) {
                event.addModifier(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ItemPrefix.KNOCK_BACK_UUID_HAND,
                    "Item Prefix", itemPrefix.knockBack, MULTIPLY_TOTAL));
            }
            if (itemPrefix.size != 0.0) {
                event.addModifier(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ItemPrefix.ENTITY_REACH_UUID_HAND,
                    "Item Prefix", itemPrefix.size, MULTIPLY_TOTAL));
            }
        });
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        SilencedEffect.onRightClick(player, event);
        CursedEffect.onRightClick(player, event::setCanceled);
        StonedEffect.onRightClick(player, event::setCanceled);
    }

    @SubscribeEvent
    public static void entityItemPickup(EntityItemPickupEvent event) {
        ItemEntity itemEntity = event.getItem();
        ItemStack itemStack = itemEntity.getItem();
        if (itemStack.is(ModTags.PROVIDE_MANA)) {
            event.getEntity().getCapability(ManaProvider.CAPABILITY)
                .ifPresent(manaStorage -> manaStorage.receiveMana(() -> itemStack.getCount() * 100));
            itemEntity.discard();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() != EquipmentSlot.MAINHAND) return;
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PrefixProvider.initPrefix(serverPlayer, event.getTo());
        }
    }
}
