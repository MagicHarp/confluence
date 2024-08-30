package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class ReconScope extends SniperScope implements IAggroAttach {
    public static final UUID CRIT_UUID = UUID.fromString("9F9D07C4-B7EF-F6F3-A9F5-2D6833E32C28");
    public static final UUID RANGED_UUID = UUID.fromString("057340AC-3837-20ED-E89F-B171F71EA00C");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public ReconScope() {
        super(ModRarity.PINK);
    }

    @Override
    public int getAggro() {
        return ModConfigs.RECON_SCOPE_AGGRO.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.putrid_scent.tooltip"));
        list.add(Component.translatable("item.confluence.recon_scope.tooltip"));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Recon Scope", 0.1, AttributeModifier.Operation.ADDITION),
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Recon Scope", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }
}
