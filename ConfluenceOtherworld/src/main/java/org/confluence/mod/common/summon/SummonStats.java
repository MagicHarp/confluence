package org.confluence.mod.common.summon;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.common.LibAttributes;
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
        return new SummonStats(baseDamage, (float) PrefixUtils.heldItemContribution(stack, 1.0D, LibAttributes.getSummonDamage().value()));
    }

    /// 召唤伤害 = 基础伤害 × 不含手持武器贡献的召唤加成 × 召唤武器自身倍率。
    public float damage(ServerPlayer owner) {
        double ownerMultiplier = PrefixUtils.attributeWithoutHeldItem(owner, LibAttributes.getSummonDamage(), owner.getMainHandItem());
        return (float) (baseDamage * ownerMultiplier * weaponDamageMultiplier);
    }
}
