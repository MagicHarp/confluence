package org.confluence.mod.entity.boss.geoEntity;


import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.TerraBossBase;


public class EaterOfWorldSegment extends TerraBossBase {
    private static final float[] MAX_HEALTHS = {50f, 70f, 100f};
    private static final float[] DAMAGE = {4f, 6f, 9f};//接触伤害


    public float segmentInternal = 3.5f;
    public EaterOfWorld head;
    public TerraBossBase lastSegment;

    //public int segmentIndex;
    public boolean genNewHeadOnRemove = true;
    public boolean ifTail = false;
    public static final EntityDataAccessor<Boolean> DATA_TAIL = SynchedEntityData.defineId(EaterOfWorldSegment.class, EntityDataSerializers.BOOLEAN);
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_TAIL, false);
    }

    public void setHead(EaterOfWorld head){
        this.head = head;
    }
    public void setLastSegment(TerraBossBase lastSegment){

        this.lastSegment = lastSegment;
    }


    public EaterOfWorldSegment(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public EaterOfWorldSegment(EaterOfWorld head, Level level) {

        super(ModEntities.EATER_OF_WORLD_SEGMENT.get(), level);
        this.head = head;

        this.noPhysics = true;

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(MAX_HEALTHS[difficultyIdx]);
        setHealth(MAX_HEALTHS[difficultyIdx]);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DAMAGE[difficultyIdx]);
    }

    public Vec3 getNextPos(){
        if(distanceToSqr(lastSegment)<1) return position();
        this.lookAt(lastSegment,500,500);
        Vec3 newPos = lastSegment.position().add(position().subtract(lastSegment.position()).normalize().scale(segmentInternal));
        return newPos;
    }

    public boolean isNoGravity(){
        return true;
    }
    @Override
    public void addSkills() {

    }
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public void tick(){
        super.tick();

        if(!level().isClientSide) {
            if (lastSegment != null && lastSegment.isAlive())
                this.setPos(getNextPos());
            entityData.set(DATA_TAIL,ifTail);
            /*
            if(tickCount%5==0){
                TerraBossBase rear = lastSegment;
                int count = 1;
                while (rear instanceof EaterOfWorld_Segment && rear.isAlive()){
                    rear = ((EaterOfWorld_Segment) rear).lastSegment;
                    count++;
                }
                if (rear != null && rear.isAlive()) {

                }
                segmentIndex = count;
                head = (EaterOfWorld) rear;
            }

*/

        }else{
            ifTail = entityData.get(DATA_TAIL);
        }
    }

   public boolean hurt(DamageSource source,float damage){
       return super.hurt(source,damage);

   }
/*
    @Override//死亡时
    public void onRemovedFromWorld() {

        if(level().isClientSide || !genNewHeadOnRemove) return;

        if(head!=null &&head.isAlive()&& head.segments.size()>segmentIndex){//重新设置头

            EaterOfWorld_Segment next = (EaterOfWorld_Segment) head.segments.get(segmentIndex);
            if(!next.isAlive())return;
            EaterOfWorld newHead = new EaterOfWorld(level(),false);
            newHead.setHealth(next.getHealth());
            newHead.setPos(next.position());
            newHead.setYRot(next.yRotO);
            newHead.setXRot(next.xRotO);
            newHead.genSegments =  false;
            next.genNewHeadOnRemove = false;
            TerraBossBase last = newHead;
            int count = 1;
            for(var n : head.segments){
                var seg = (EaterOfWorld_Segment)n;
                if(seg.segmentIndex>segmentIndex+1){
                    newHead.segments.add(seg);
                    seg.segmentIndex = (segmentIndex+count++);
                    seg.head = newHead;
                    seg.lastSegment = last;
                    last=seg;

                }
            }
            next.discard();
            level().addFreshEntity(newHead);
        }

        if(head!=null && head.isAlive()){//删除子序列
            head.segments.removeIf(o->((EaterOfWorld_Segment)o).segmentIndex>=segmentIndex);
        }

        super.onRemovedFromWorld();
    }

*/

    public boolean shouldShowBossBar(){return false;};

}
