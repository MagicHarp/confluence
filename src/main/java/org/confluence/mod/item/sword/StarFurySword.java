package org.confluence.mod.item.sword;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.ReversalImage16x;
import org.confluence.mod.entity.projectile.StarFuryProjectile;
import org.confluence.mod.entity.projectile.SwordProjectile;
import org.confluence.mod.misc.ModSoundEvents;

import java.util.ArrayList;
import java.util.List;

public class StarFurySword extends SwordItem implements CustomItemModel, ISwordProjectile{
    private float maxAngle = 30;//索敌最大角度
    private float range = 30;//索敌范围
    private float predict = 10;//预判量
    private float inAccuracy = 0.5f;
    private float offsetV = 20;//发射时的高度偏移
    private float offsetH = 5;//发射时的xy偏移

    @Override
    public float getBaseVelocity() {
        return 1.5f;
    }

    public StarFurySword(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, rawDamage, rawSpeed, properties);
    }

    public void genProjectile(Player owner) {
        owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), getSound(), SoundSource.AMBIENT, 1.0F, 1.0F);

        Vec3 eye = owner.position().add(0,1,0);
        Entity target = getTargets(eye,eye.add(owner.getForward().normalize().scale(range)),owner.level(), owner);
        Vec3 waveTarget = Vec3.ZERO;

        if(target!=null){
            //周围有目标 预判
            waveTarget = target.position().add(target.getDeltaMovement().scale(predict)).add(0,0.5,0);

        }else{
            //周围无目标 获取视线指向点
            Vec3 ori = owner.position().add(0,1,0);
            Vec3 end = ori.add(owner.getForward().normalize().scale(range));
            BlockHitResult blockHitResult = owner.level().clip(new ClipContext(ori,end, ClipContext.Block.OUTLINE,ClipContext.Fluid.NONE, owner));
            waveTarget = blockHitResult.getLocation();
        }
        var proj = getProjectile(owner);
        proj.setPos(waveTarget.add(Math.random() * offsetH - offsetH,offsetV,Math.random() * offsetH - offsetH));
        proj.shoot(waveTarget.x - proj.getX(),waveTarget.y- proj.getY(),waveTarget.z - proj.getZ(),getBaseVelocity(),inAccuracy);

        owner.level().addFreshEntity(proj);

    }

    private LivingEntity getTargets(Vec3 ori, Vec3 end, Level level, Entity entity){
        //扩大包围盒
        AABB range = entity.getBoundingBox().inflate(this.range);
        List<HitResult> hits = new ArrayList<>();
        List<HitResult> subHits = new ArrayList<>();
        List<? extends Entity> entities = level.getEntities(entity,range,entity1 -> entity1.isPickable() && entity1.isAlive());
        for(var e : entities){
            //获取视线交点
            Vec3 vec3 = e.getBoundingBox().clip(ori,end).orElse(null);
            //优先指向的目标
            if(vec3!=null){
                //System.out.println("point directly");
                EntityHitResult entityHitResult = new EntityHitResult(e,vec3);
                hits.add(entityHitResult);
            }//自瞄其他夹角小于一定度数的目标
            else if(hits.isEmpty() && angle(e.position().subtract(ori),end.subtract(ori)) < maxAngle *  Math.PI/180){
                EntityHitResult entityHitResult = new EntityHitResult(e,e.position());
                subHits.add(entityHitResult);
            }
        }


        if(!hits.isEmpty()){
            //射线命中的目标 按距离排序
            hits.sort((o1,o2)->o1.getLocation().distanceToSqr(ori) < o2.getLocation().distanceToSqr(ori)?-1:1);
            HitResult hitResult = hits.get(0);
            if(hitResult instanceof  EntityHitResult entityHitResult &&
                    entityHitResult.getEntity() instanceof LivingEntity livingEntity){
                return livingEntity;
            }
        }else if(!subHits.isEmpty()){
            //未命中的目标 按距离排序
            subHits.sort((o1,o2)->o1.getLocation().distanceToSqr(ori) < o2.getLocation().distanceToSqr(ori)?-1:1);

            HitResult hitResult = subHits.get(0);
            if(hitResult instanceof  EntityHitResult entityHitResult &&
                    entityHitResult.getEntity() instanceof LivingEntity livingEntity){
                return livingEntity;
            }
        }
        return null;
    }
    public double angle(Vec3 line1,Vec3 line2){
        return Math.acos(line1.dot(line2)/line1.length()/line2.length());
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public SoundEvent getSound() {
        return ModSoundEvents.STAR.get();
    }



    @Override
    public SwordProjectile getProjectile(Player player) {
        return new StarFuryProjectile(player);
    }


}