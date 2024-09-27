package org.confluence.mod.entity.projectile.arrows;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseArrowEntity extends AbstractArrow {

    int penetrate = 0;
    private static final EntityDataAccessor<String> TEXTURE_PATH = SynchedEntityData.defineId(BaseArrowEntity.class, EntityDataSerializers.STRING);
    public String texturePath;

    List<LivingEntity> havenBeen = new ArrayList<>();//标记不能重复穿透
    Builder attr = new Builder();

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



    protected void onHitEntity(EntityHitResult pResult) {
        if(!canPenetrate()) super.onHitEntity(pResult);
        if(!(pResult.getEntity() instanceof LivingEntity) || havenBeen.contains((LivingEntity) pResult.getEntity())) return;
        pResult.getEntity().hurt(damageSources().arrow(this,this.getOwner()), (float) getBaseDamage());
        havenBeen.add((LivingEntity) pResult.getEntity());
        penetrate++;
    }
    public double getBaseDamage() {
        return attr.base_damage;
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);


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
        return penetrate < attr.penetration_count;
    }



    public void defineSynchedData(){
        super.defineSynchedData();
        entityData.define(TEXTURE_PATH,"");
    }
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        this.texturePath =entityData.get(TEXTURE_PATH);

    }


    public static class Tuple {
        public String path;
        public Builder attr;
        static Tuple create(String path, Builder type){
            Tuple t = new Tuple();
            t.path = path;
            t.attr = type;
            return t;
        }
        //构建属性
        static Tuple JESTERS_ARROW_ENTITY = create("textures/entity/arrow/jesters_arrow.png",new Builder().damage(10).low_gravity(0).penetration(99).auto_discard(100));
        static Tuple UNHOLY_ARROW_ENTITY = create("textures/entity/arrow/unholy_arrow.png",new Builder().damage(10).penetration(5));
        static Tuple FLAMING_ARROW_ENTITY = create("textures/entity/arrow/flaming_arrow.png",new Builder() );

    }
    /*
    HELLFIRE_ARROW
            FROSTBURN_ARROW
    BONE_ARROW = IT
            SHIMMER_ARROW =
    */
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

    }
    static class Builder{
        private int type = 0;
        private int penetration_count = 0;
        private int gravity_count = 0;
        private int auto_discard_tick = 1200;
        private int base_damage = 5;
        private ResourceLocation texture;
        public Builder damage(int damage){
            base_damage = damage;
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


    }

}
