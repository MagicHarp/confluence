package org.confluence.mod.common.summon;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.common.LibAttributes;
import org.confluence.mod.common.component.prefix.PrefixComponent;
import org.confluence.mod.util.PrefixUtils;

public record SummonStats(float baseDamage, float weaponDamageMultiplier) {
    public SummonStats {
        if (baseDamage < 0.0F) {
            throw new IllegalArgumentException("Summon damage must be non-negative");
        }
        if (weaponDamageMultiplier < 0.0F) {
            throw new IllegalArgumentException("Summon weapon damage multiplier must be non-negative");
        }
    }

    public static SummonStats from(ItemStack stack, float baseDamage) {
        return new SummonStats(baseDamage, prefixDamageMultiplier(stack));
    }

    public float damage(ServerPlayer owner) {
        float ownerMultiplier = (float) owner.getAttributeValue(LibAttributes.getSummonDamage());
        float heldMultiplier = prefixDamageMultiplier(owner.getMainHandItem());
        if (heldMultiplier > 0.0F) {
            ownerMultiplier /= heldMultiplier;
        }
        return baseDamage * ownerMultiplier * weaponDamageMultiplier;
    }

    private static float prefixDamageMultiplier(ItemStack stack) {
        PrefixComponent prefix = PrefixUtils.getPrefix(stack);
        if (prefix == null) return 1.0F;
        float addition = 0.0F;
        float multiplyBase = 0.0F;
        float multiplyTotal = 1.0F;
        for (AttributeModifier modifier : prefix.modifiers().get().get(LibAttributes.getSummonDamage().value())) {
            switch (modifier.getOperation()) {
                case ADDITION -> addition += (float) modifier.getAmount();
                case MULTIPLY_BASE -> multiplyBase += (float) modifier.getAmount();
                case MULTIPLY_TOTAL -> multiplyTotal *= 1.0F + (float) modifier.getAmount();
            }
        }
        return (1.0F + addition + multiplyBase) * multiplyTotal;
    }
}
