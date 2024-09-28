package org.confluence.mod.entity.projectile.arrows;

import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseArrowEntity extends AbstractArrow {


/** 调参表 **/
    static final float minSpeedAttackFactor = 0.5f;//速度影响伤害的最小系数

    public static class Tuple {
        public String path;
        public Builder attr;
        static Tuple create(String path, Builder type){Tuple t = new Tuple();t.path = path;t.attr = type;return t;}


        //构建属性   mc原版木箭：   damage：2f
        static Tuple JESTERS_ARROW_ENTITY = create("textures/entity/arrow/jesters_arrow.png",new Builder()
                .damage(4f).penetration(99).knockBackFactor(2).speedFactor(0.8f).auto_discard(50).low_gravity(0));
        static Tuple UNHOLY_ARROW_ENTITY = create("textures/entity/arrow/unholy_arrow.png",new Builder()
                .damage(4.5f).penetration(5).knockBackFactor(1.5f));
        static Tuple FLAMING_ARROW_ENTITY = create("textures/entity/arrow/flaming_arrow.png",new Builder()
                .damage(4.5f).causeFire(10*20) );
    }

        /*
        HELLFIRE_ARROW
        FROSTBURN_ARROW
        BONE_ARROW
        SHIMMER_ARROW
        */






    private static final EntityDataAccessor<String> TEXTURE_PATH = SynchedEntityData.defineId(BaseArrowEntity.class, EntityDataSerializers.STRING);
    public String texturePath = "";
    private int penetrate = 0;
    private List<LivingEntity> havenBeen = new ArrayList<>();//标记不能重复穿透
    private Builder attr = new Builder();

    public BaseArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public BaseArrowEntity(LivingEntity owner, Tuple arrowTuple) {
        super(ModEntities.ARROW_PROJECTILE.get(),owner, owner.level());
        this.attr = arrowTuple.attr;
        if((attr.type&Tag.low_gravity)!=0) this.setNoGravity(true);
        entityData.set(TEXTURE_PATH,arrowTuple.path);
        this.texturePath = arrowTuple.path;

    }

    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy);
        this.setDeltaMovement(getDeltaMovement().scale(attr.speedFactor));
    }

    protected void onHitEntity(EntityHitResult pResult) {
        if((attr.type&Tag.cause_fire)!=0) this.setRemainingFireTicks(this.getRemainingFireTicks() + attr.causeFireTick - tickCount);//todo 火焰附加要不要减去移动时间

        if(!canPenetrate()) super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if(havenBeen.contains((LivingEntity) entity)) return;
        float f = (float)this.getDeltaMovement().length();
        f = Math.max(f, minSpeedAttackFactor);//速度修正系数影响的最小速度值
        float i = (float) Mth.clamp((double)f * this.getBaseDamage(), 0.0D, Integer.MAX_VALUE);
        /*暴击增伤
        if (this.isCritArrow()) {
            long j = this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }
        */
        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = this.damageSources().arrow(this, this);
        } else {
            damagesource = this.damageSources().arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }
        havenBeen.add((LivingEntity) pResult.getEntity());
        penetrate++;
        int k = entity.getRemainingFireTicks();

        if(entity.hurt(damagesource, i)){
            this.playSound(getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

            LivingEntity livingentity = (LivingEntity)entity;

            this.doPostHurtEffects(livingentity);


            if (!this.level().isClientSide) {
                livingentity.setArrowCount(livingentity.getArrowCount() + 1);
            }
            //击退
            this.setKnockback((int) (getKnockback()*attr.knockBackFactor));
            if (this.getKnockback() > 0) {
                double d0 = Math.max(0.0D, 1.0D - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.getKnockback() * 0.6D * d0);
                if (vec3.lengthSqr() > 0.0D) {
                    livingentity.push(vec3.x, 0.1D, vec3.z);
                }
            }

            //箭药水效果
            if (!this.level().isClientSide && entity1 instanceof LivingEntity) {
                EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
            }

            //命中自己
            if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }

        }else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
            }
        }



    }
    public double getBaseDamage() {
        return attr.base_damage;
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);


    }

    private static SoundEvent getSound(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }


    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
/*
    //要加上，不然报错
    public boolean isNoPhysics() {
        return this.noPhysics;
    }
*/
    public void tick(){

        if(!level().isClientSide && tickCount>attr.auto_discard_tick)discard();
        //todo 重力调整


        super.tick();
    }


    public boolean canPenetrate(){
        return penetrate+1 < attr.penetration_count;
    }



    public void defineSynchedData(){
        super.defineSynchedData();
        entityData.define(TEXTURE_PATH,"");
    }
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        this.texturePath =entityData.get(TEXTURE_PATH);

    }




    public static Map<Item,Tuple> selectArrowFromItemMap = Map.of(
            ModItems.JESTERS_ARROW.get(),Tuple.JESTERS_ARROW_ENTITY,
            ModItems.UNHOLY_ARROW.get(),Tuple.UNHOLY_ARROW_ENTITY,
            ModItems.FLAMING_ARROW.get(),Tuple.FLAMING_ARROW_ENTITY
    );


    /** 能力表 **/
    static class Tag{
        static final int penetration = 1;//可穿透
        static final int low_gravity = 2;//低重力
        static final int auto_discard = 4;//超过时间自动消失
        static final int cause_fire = 8;//火焰附加

    }
    static class Builder{
        private int type = 0;
        private int penetration_count = 0;
        private int gravity_count = 0;
        private int auto_discard_tick = 1200;
        private float base_damage = 2;
        private float speedFactor = 1;
        private float knockBackFactor = 1;
        private int causeFireTick = 0;
        private List<MobEffect> effects = new ArrayList<>();
        public Builder damage(float damage){//基本伤害
            base_damage = damage;
            return this;
        }
        public Builder knockBackFactor(float factor){//击退修正系数
            this.knockBackFactor = factor;
            return this;
        }


        public Builder penetration(int count){//穿透次数
            type|=Tag.penetration;
            penetration_count = count;
            return this;
        }
        public Builder low_gravity(int gravity){//重力
            type|=Tag.low_gravity;
            gravity_count = gravity;
            return this;
        }
        public Builder auto_discard(int tick){//消失tick
            type|=Tag.auto_discard;
            auto_discard_tick = tick;
            return this;
        }
        public Builder speedFactor(float factor){//初始速度修正系数
            type|=Tag.auto_discard;
            speedFactor = factor;
            return this;
        }
        public Builder causeFire(int tick){//初始火焰增加
            type|=Tag.cause_fire;
            this.causeFireTick = tick;
            return this;
        }




    }

}
