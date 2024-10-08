package org.confluence.mod.item.sword;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.Image24x;
import org.confluence.mod.datagen.limit.ReversalImage16x;
import org.confluence.mod.entity.projectile.IceBladeSwordProjectile;
import org.confluence.mod.entity.projectile.SwordProjectile;
import org.confluence.mod.misc.ModSoundEvents;

public class IceBladeSwordItem extends SwordItem implements ISwordProjectile, ReversalImage16x {
    public IceBladeSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public SoundEvent getSound() {
        return ModSoundEvents.FROZEN_ARROW.get();
    }

    @Override
    public SwordProjectile getProjectile(Player player) {
        return new IceBladeSwordProjectile(player);
    }

    @Override
    public float getBaseVelocity() {
        return 2.5F;
    }
}
