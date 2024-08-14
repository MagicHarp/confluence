package org.confluence.mod.mixin.entity;

import net.minecraft.world.entity.player.Player;
import org.confluence.mod.entity.projectile.FlailEntity;
import org.confluence.mod.mixinauxiliary.IPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayer {
    @Unique private FlailEntity confluence$flailEntity;

    @Override
    public FlailEntity confluence$flailEntity(FlailEntity... entity){
        if(entity.length > 0){
            confluence$flailEntity = entity[0];
        }
        return confluence$flailEntity;
    }
}
