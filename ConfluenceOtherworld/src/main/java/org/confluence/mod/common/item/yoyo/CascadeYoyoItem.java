package org.confluence.mod.common.item.yoyo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.mod.common.entity.yoyo.YoyoEntity;

public final class CascadeYoyoItem extends YoyoItem {
    public CascadeYoyoItem(ModRarity rarity, YoyoDefinition definition) {
        super(new Item.Properties().unbreakable(), rarity, definition);
    }

    @Override
    protected void onHitTarget(YoyoEntity yoyo, ServerPlayer owner, LivingEntity target) {
        target.setRemainingFireTicks(100);
    }
}
