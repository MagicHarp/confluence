package org.confluence.mod.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.prefix.ItemPrefix;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.harmful.CursedEffect;
import org.confluence.mod.effect.harmful.SilencedEffect;
import org.confluence.mod.effect.harmful.StonedEffect;
import org.confluence.mod.fluid.ShimmerItemTransmutationEvent;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.curio.IFunctionCouldEnable;
import org.confluence.mod.item.curio.fishing.IHighTestFishingLine;
import org.confluence.mod.item.curio.fishing.ITackleBox;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.mixin.accessor.ItemEntityAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.SetItemEntityPickupDelayPacketS2C;

import java.util.Collections;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ItemEvents {
    @SubscribeEvent
    public static void itemAttributeModifier(ItemAttributeModifierEvent event) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND) return;
        ItemStack itemStack = event.getItemStack();
        PrefixProvider.getPrefix(itemStack).ifPresent(itemPrefix -> {
            if (itemPrefix.attackDamage != 0.0) {
                if (itemStack.is(ModTags.Items.RANGED_WEAPON)) {
                    event.addModifier(ModAttributes.getRangedDamage(), new AttributeModifier(ItemPrefix.RANGED_DAMAGE_UUID_HAND, "Item Prefix", itemPrefix.attackDamage, MULTIPLY_TOTAL));
                } else {
                    event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(ItemPrefix.ATTACK_DAMAGE_UUID_HAND, "Item Prefix", itemPrefix.attackDamage, MULTIPLY_TOTAL));
                }
            }
            if (itemPrefix.attackSpeed != 0.0) {
                event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ItemPrefix.ATTACK_SPEED_UUID_HAND, "Item Prefix", itemPrefix.attackSpeed, MULTIPLY_TOTAL));
            }
            if (itemPrefix.knockBack != 0.0) {
                event.addModifier(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ItemPrefix.KNOCK_BACK_UUID_HAND, "Item Prefix", itemPrefix.knockBack, ADDITION));
            }
            if (itemPrefix.size != 0.0) {
                event.addModifier(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ItemPrefix.ENTITY_REACH_UUID_HAND, "Item Prefix", itemPrefix.size, MULTIPLY_TOTAL));
            }
            if (itemPrefix.criticalChance != 0.0) {
                event.addModifier(ModAttributes.getCriticalChance(), new AttributeModifier(ItemPrefix.CRIT_CHANCE_UUID_HAND, "Item Prefix", itemPrefix.criticalChance, ADDITION));
            }
            if (itemPrefix.rangedDamage != 0.0) {
                event.addModifier(ModAttributes.getRangedDamage(), new AttributeModifier(ItemPrefix.RANGED_DAMAGE_UUID_HAND, "Item Prefix", itemPrefix.rangedDamage, MULTIPLY_TOTAL));
            }
            if (itemPrefix.velocity != 0.0) {
                event.addModifier(ModAttributes.getRangedVelocity(), new AttributeModifier(ItemPrefix.RANGED_VELOCITY_UUID_HAND, "Item Prefix", itemPrefix.velocity, MULTIPLY_TOTAL));
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
        Player player = event.getEntity();
        if (itemStack.is(ModTags.Items.PROVIDE_MANA)) {
            player.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage ->
                manaStorage.receiveMana(() -> itemStack.getCount() * 100));
            itemEntity.discard();
            event.setCanceled(true);
        } else if (itemStack.is(ModTags.Items.PROVIDE_LIFE)) {
            player.heal(itemStack.getCount() * 4.0F);
            itemEntity.discard();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void itemExpire(ItemExpireEvent event) {
        if (event.getEntity().getItem().getItem() instanceof ModRarity.Special) {
            event.setExtraLife(6000);
        }
    }

    @SubscribeEvent
    public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() != EquipmentSlot.MAINHAND) return;
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PrefixProvider.initPrefix(serverPlayer, event.getTo());
        }
    }

    @SubscribeEvent
    public static void itemStackedOnOther(ItemStackedOnOtherEvent event) {
        ItemStack onSlot = event.getCarriedItem();
        ItemStack carried = event.getStackedOnItem(); // 非常奇怪,但事实如此
        Item item = onSlot.getItem();
        if (event.getClickAction() == ClickAction.SECONDARY) {
            if (carried.isEmpty() && item instanceof IFunctionCouldEnable couldEnable) {
                if (!event.getPlayer().level().isClientSide) {
                    couldEnable.cycleEnable(onSlot);
                }
                event.setCanceled(true);
            }
        } else if (ItemStack.isSameItem(onSlot, carried)) {
            if (item instanceof ColoredItem) {
                ColoredItem.setColor(carried, ColoredItem.getColor(onSlot));
            }
        }
    }

    @SubscribeEvent
    public static void itemFished(ItemFishedEvent event) {
        IHighTestFishingLine.apply(event);
        if (event.isCanceled()) return;
        ITackleBox.apply(event.getHookEntity(), event.getEntity());
    }

    @SubscribeEvent
    public static void arrowLoose(ArrowLooseEvent event) {
        PrefixProvider.getPrefix(event.getBow()).ifPresent(itemPrefix ->
            event.setCharge((int) Math.ceil(event.getCharge() * (1.0 + itemPrefix.attackSpeed))));
    }

    @SubscribeEvent
    public static void shimmerTransmutation$post(ShimmerItemTransmutationEvent.Post event) {
        if (ConfluenceData.get((ServerLevel) event.getSource().level()).isGraduated()) {
            ItemStack itemStack = event.getSource().getItem();
            Item item = itemStack.getItem();
            if (item == ModItems.BOTTOMLESS_WATER_BUCKET.get()) {
                event.setShrink(1);
                event.setTargets(Collections.singletonList(new ItemStack(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get())));
            } else if (item == ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()) {
                event.setShrink(1);
                event.setTargets(Collections.singletonList(new ItemStack(ModItems.BOTTOMLESS_WATER_BUCKET.get())));
            }
        }
    }

    @SubscribeEvent
    public static void livingEntityUseItem$tick(LivingEntityUseItemEvent.Tick event) {
        ItemStack itemStack = event.getItem();
        if (itemStack.is(Tags.Items.TOOLS_BOWS) || itemStack.is(Tags.Items.TOOLS_CROSSBOWS)) {
            CompoundTag tag = itemStack.getTagElement(PrefixProvider.KEY);
            if (tag != null && event.getEntity().level().getGameTime() % (int) (1.0 / tag.getFloat("attackSpeed")) == 0) {
                event.setDuration(event.getDuration() - 1);
            }
        }
    }

    @SubscribeEvent
    public static void itemToss(ItemTossEvent event) {
        ItemEntity itemEntity = event.getEntity();
        if (!itemEntity.level().isClientSide) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.ALL.noArg(),
                new SetItemEntityPickupDelayPacketS2C(itemEntity.getId(), ((ItemEntityAccessor) itemEntity).getPickupDelay())
            );
        }
    }
}
