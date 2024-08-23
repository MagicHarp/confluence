package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class SniperScope extends RifleScope implements IProjectileAttack {
    public static final UUID CRIT_UUID = UUID.fromString("EF0F0AE1-685D-D312-CD47-78ABA87308A2");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public SniperScope() {
        super(ModRarity.LIME);
    }

    public SniperScope(Rarity rarity) {
        super(rarity);
    }

    @Override
    public float getProjectileBonus() {
        return 0.1F;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.sniper_scope.tooltip"));
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sniper_scope.info"),
            Component.translatable("item.confluence.sniper_scope.info2")
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Sniper Scope", 0.1, AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }
}
