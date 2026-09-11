package org.confluence.mod.common.item.yoyo;

/// 悠悠球品种共有的不可变数值。具体武器行为由对应的 YoyoItem 子类负责。
public record YoyoDefinition(float attackDamage, float maximumRange, int stringColor,
                             int lifetimeTicks) {
    public YoyoDefinition {
        if (attackDamage < 0.0F)
            throw new IllegalArgumentException("Yoyo attack damage must be non-negative");
        if (maximumRange < 1.0F)
            throw new IllegalArgumentException("Yoyo range must be at least 1.0");
        if (lifetimeTicks <= 0)
            throw new IllegalArgumentException("Yoyo lifetime must be positive");
        stringColor = 0xFF000000 | stringColor & 0x00FFFFFF;
    }

    public static YoyoDefinition of(float attackDamage, float maximumRange, int stringColor, float lifetimeSeconds) {
        return new YoyoDefinition(attackDamage, maximumRange, stringColor, Math.max(1, Math.round(lifetimeSeconds * 20.0F)));
    }
}
