package org.confluence.mod.entity.boss.geoEntity;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KingSkullHand extends Monster implements GeoEntity {

    public KingSkullHand(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
    KingSkull owner;
    boolean mainHand;

    public KingSkullHand(KingSkull owner, Level level, boolean mainHand){
        super(ModEntities.KING_SKULL_HAND.get(), level);
        this.owner = owner;
        this.mainHand = mainHand;
        this.setNoGravity(true);

    }

    private Vec3 nextAttackPos;
    private boolean pauseAttack;
    private boolean resetGroundingAttack;
    private int refreshPosTick;
    private int refreshPosTickInternal = 30;

    public void tick(){
        super.tick();
        if(this.owner==null)return;
        Entity target = owner.getTarget();

        if(owner.handState == KingSkull.HandState.follow){
            if(this.mainHand) refreshPosTick = 0;
            else refreshPosTick = 20;
            resetGroundingAttack = true;
            Vec3 targetPos;
            if(mainHand){
                targetPos = owner.position().relative(Direction.fromYRot(90f+owner.getYRot()),2).add(0,1,0);
                this.setYRot(owner.getYRot());
            }else{
                targetPos = owner.position().relative(Direction.fromYRot(-90f+owner.getYRot()),2).add(0,1,0);
                this.setYRot(owner.getYRot() + 180);
            }

            this.addDeltaMovement(targetPos.subtract(this.position()).normalize().scale(0.2));

        }else{
            this.refreshPosTick++;
            if(target!=null) {
                if(refreshPosTick>refreshPosTickInternal){
                    refreshPosTick = 0;
                    nextAttackPos = target.position().add(0,1,0).add(target.position().subtract(position()).normalize().scale(5));
                    pauseAttack = false;

                }
                if(distanceToSqr(target)<10) pauseAttack = true;

                if(!pauseAttack && nextAttackPos!=null) {
                    if(resetGroundingAttack && onGround()) {
                        resetGroundingAttack = false;
                        nextAttackPos = target.position().add(0,1,0).add(target.position().subtract(position()).normalize().scale(5));

                    }
                    this.addDeltaMovement(nextAttackPos.subtract(this.position()).normalize().scale(0.3));
                }
            }
        }


        if(!level().isClientSide){
            //包围盒检测造成伤害
            var entities=level().getEntities(this,this.getBoundingBox());
            if(!entities.isEmpty()){
                for (var e:entities) {
                    if(owner.canAttack(e))
                        e.hurt(this.damageSources().mobAttack(this), (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                }
            }
        }



    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override//是否免疫摔伤
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
        return true;
    }

    @Override//是否在实体上渲染着火效果
    public boolean displayFireAnimation() {
        return false;
    }
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    public boolean hurt(DamageSource source,float damage){
        if(this.owner!=null)
            this.owner.hurt(source,damage);
        return true;
    }

}