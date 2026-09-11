package org.confluence.mod.common.item.yoyo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.mod.common.entity.yoyo.YoyoEntity;
import org.confluence.terra_curio.common.entity.BeeProjectile;

public final class HiveFiveYoyoItem extends YoyoItem {
    public HiveFiveYoyoItem(ModRarity rarity, YoyoDefinition definition) {
        super(new Properties().unbreakable(), rarity, definition);
    }

    @Override
    protected void onHitTarget(YoyoEntity yoyo, ServerPlayer owner, LivingEntity target) {
        if (owner.getRandom1211().nextFloat() >= 0.33F) return;
        BeeProjectile bee = new BeeProjectile(owner.level(), owner, false);
        bee.setBaseDamage(yoyo.getDamage() * 0.5F);
        bee.setPos(target.position().add(target.getRandom1211().nextFloat() * 0.2F, target.getEyeHeight() * 0.5F, target.getRandom1211().nextFloat() * 0.2F));
        owner.level().addFreshEntity(bee);
    }
}
