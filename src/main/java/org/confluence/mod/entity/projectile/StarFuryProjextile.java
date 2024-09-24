package org.confluence.mod.entity.projectile;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.confluence.mod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StarFuryProjextile extends SwordProjectile {
    @Override
    protected int getBaseDamage() {
        return 10;
    }

    @Override
    protected float getBaseKnockBack() {
        return 1;
    }

    public int hitCount = 2;//可穿透两个目标


    public StarFuryProjextile(EntityType<? extends SwordProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public StarFuryProjextile(Player owner) {
        super(ModEntities.STAR_FURY_PROJECTILE.get(),owner.level());
        this.setOwner(owner);
    }



    @Override//添加减速药水效果
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if(!this.level().isClientSide()) {

            Entity entity1 = pResult.getEntity();
            Entity entity = this.getOwner();
            entity1.hurt(this.damageSources().mobProjectile(this, entity instanceof LivingEntity livingentity ? livingentity : null), getBaseDamage());
            if(--hitCount == 0)
                discard();
        }
    }
    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide())
            discard();
    }




}
