package org.confluence.mod.item.curio;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.prefix.PrefixProvider;
import org.confluence.mod.capability.prefix.PrefixType;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.IScope;
import org.confluence.mod.item.curio.construction.IRightClickSubtractor;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.item.curio.movement.ITabi;
import org.confluence.mod.item.curio.movement.IWallClimb;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.FlushPlayerAbilityPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseCurioItem extends Item implements ICurioItem {
    protected static final List<Component> EMPTY_TOOLTIP = List.of();
    protected static final ImmutableMultimap<Attribute, AttributeModifier> EMPTY_ATTRIBUTE = ImmutableMultimap.of();

    public BaseCurioItem(Rarity rarity) {
        super(rarity != ModRarity.GRAY && rarity != ModRarity.WHITE ? new Properties().rarity(rarity).fireResistant().stacksTo(1) : new Properties().rarity(rarity).stacksTo(1));
    }

    public BaseCurioItem() {
        this(ModRarity.BLUE);
    }

    protected void freshAbility(Item item, LivingEntity living) {
        living.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> playerAbility.flushAbility(living));
        living.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> manaStorage.flushAbility(living));
        if (living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new FlushPlayerAbilityPacketS2C()
            );
            if (item instanceof IMayFly) IMayFly.sendMsg(serverPlayer);
            if (item instanceof IMultiJump) IMultiJump.sendMsg(serverPlayer);
            if (item instanceof IAutoAttack) IAutoAttack.sendMsg(serverPlayer);
            if (item instanceof IScope) IScope.sendMsg(serverPlayer);
            if (item instanceof IRightClickSubtractor) IRightClickSubtractor.sendMsg(serverPlayer);
            if (item instanceof IWallClimb) IWallClimb.sendMsg(serverPlayer);
            if (item instanceof ITabi) ITabi.sendMsg(serverPlayer);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        freshAbility(stack.getItem(), living);
        PrefixProvider.create(living.level().random, stack, PrefixType.CURIO);
        PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.applyCurioPrefix(living));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        freshAbility(stack.getItem(), living);
        PrefixProvider.getPrefix(stack).ifPresent(itemPrefix -> itemPrefix.expireCurioPrefix(living));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return !(stack.getItem() instanceof IFunctionCouldEnable) && canEquip(slotContext, stack);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), this);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence." + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)).getPath() + ".tooltip"));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        if (this instanceof ModRarity.Special special) {
            return special.withColor(getDescriptionId(itemStack));
        }
        return super.getName(itemStack);
    }

    public Component[] getInformation() {
        return new Component[]{};
    }

    public String[] getModifierValues(Attribute... attributes) {
        Multimap<Attribute, AttributeModifier> modifiers = getAttributeModifiers(new SlotContext(null, null, -1, false, false), null, null);
        return Arrays.stream(attributes).map(attribute -> ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(((AttributeModifier) modifiers.get(attribute).toArray()[0]).getAmount() * 100.0)).toArray(String[]::new);
    }
}
