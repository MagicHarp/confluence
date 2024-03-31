package org.confluence.mod.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class OwnerMobEffectInstance extends MobEffectInstance {
    private final Player owner;

    public OwnerMobEffectInstance(MobEffectInstance instance, Player owner) {
        super(instance);
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
