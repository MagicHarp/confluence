package org.confluence.mod.item.gun;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;

public class BaseAmmoItem extends Item {
    private final BaseAmmoEntity.Variant variant;

    public BaseAmmoItem(Rarity rarity, BaseAmmoEntity.Variant variant) {
        super(new Properties().rarity(rarity).fireResistant());
        this.variant = variant;
    }

    public BaseAmmoItem(BaseAmmoEntity.Variant variant, Properties properties) {
        super(properties);
        this.variant = variant;
    }

    public BaseAmmoEntity getAmmoEntity(LivingEntity living, Level level) {
        return new BaseAmmoEntity(living, level, variant);
    }
}
