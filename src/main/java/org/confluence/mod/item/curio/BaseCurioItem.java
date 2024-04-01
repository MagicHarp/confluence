package org.confluence.mod.item.curio;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;

public class BaseCurioItem extends Item implements ICurioItem {
    protected static final List<Component> EMPTY_TOOLTIP = List.of();
    protected static final ImmutableMultimap<Attribute, AttributeModifier> EMPTY_ATTRIBUTE = ImmutableMultimap.of();

    public BaseCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public BaseCurioItem() {
        this(new Properties());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Item item = stack.getItem();
        LivingEntity living = slotContext.entity();
        if (item instanceof ICriticalHit iCriticalHit) {
            iCriticalHit.freshChance(living);
        }
        if (item instanceof IFireImmune iFireImmune) {
            iFireImmune.freshFireImmune(living);
        }
        if (item instanceof ILavaHurtReduce iLavaHurtReduce) {
            iLavaHurtReduce.freshLavaReduce(living);
        }
        if (item instanceof ILavaImmune iLavaImmune) {
            iLavaImmune.freshLavaImmuneTicks(living);
        }
        if (item instanceof IInvulnerableTime iInvulnerableTime) {
            iInvulnerableTime.freshInvulnerableTime(living);
        }
        if (item instanceof IAggroAttach iAggroAttach) {
            iAggroAttach.freshAggro(living);
        }
        if (item instanceof IFallResistance iFallResistance) {
            iFallResistance.freshFallResistance(living);
        }
        if (item instanceof IJumpBoost iJumpBoost) {
            iJumpBoost.freshJumpBoost(living);
        }
        if (item instanceof IMayFly && living instanceof ServerPlayer serverPlayer) {
            IMayFly.sendMsg(serverPlayer);
        }
        if (item instanceof IMultiJump && living instanceof ServerPlayer serverPlayer) {
            IMultiJump.sendMsg(serverPlayer);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onEquip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), this);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return canEquipFromUse(slotContext, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence." + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)).getPath() + ".tooltip"));
    }
}
