package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class StalkersQuiver extends MagicQuiver implements IAggroAttach, CustomName {
    public static final UUID RANGED_DAMAGE_UUID = UUID.fromString("B2071F24-9C45-35EC-A75E-96DABF0EEA9F");
    public static final UUID RANGED_VELOCITY_UUID = UUID.fromString("D03F9003-CB2E-7164-96C4-9C0E29FC4244");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public StalkersQuiver() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_DAMAGE_UUID, "Stalker's Quiver", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
                ModAttributes.getRangedVelocity(), new AttributeModifier(RANGED_VELOCITY_UUID, "Stalker's Quiver", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public int getAggro() {
        return ModConfigs.STALKERS_QUIVER_AGGRO.get();
    }

    @Override
    public String getGenName() {
        return "Stalker's Quiver";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.putrid_scent.tooltip"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.stalkers_quiver.info"),
            Component.translatable("item.confluence.stalkers_quiver.info2"),
            Component.translatable("item.confluence.stalkers_quiver.info3")
        };
    }
}
