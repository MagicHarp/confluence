package org.confluence.mod.entity.boss.geoEntity;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.TerraBossBase;


public class EaterOfWorld_Segment extends TerraBossBase {

    public float segmentInternal = 3.5f;
    public EaterOfWorld head;
    public TerraBossBase lastSegment;
    //public static final RawAnimation roll = RawAnimation.begin().thenPlay("worm.roll");
    public int segmentIndex;

    public void setHead(EaterOfWorld head){
        this.head = head;
    }
    public void setLastSegment(TerraBossBase lastSegment){

        this.lastSegment = lastSegment;
    }


    public EaterOfWorld_Segment(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public EaterOfWorld_Segment(EaterOfWorld head, Level level, int index) {

        super(ModEntities.EATER_OF_WORLD_SEGMENT.get(), level);
        this.head = head;
        this.segmentIndex = index;

        this.noPhysics = true;
    }

    public Vec3 getNextPos(){
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

    @Override
    public void tick(){
        super.tick();

        if(!level().isClientSide) {
            if (lastSegment != null && lastSegment.isAlive())
                this.setPos(getNextPos());

        }
    }

   public boolean hurt(DamageSource source,float damage){
       return super.hurt(source,damage);

   }

    @Override//死亡时
    public void onRemovedFromWorld() {

        if(level().isClientSide) return;

        if(head!=null &&head.isAlive()&& head.segments.size()>segmentIndex){//重新设置头

            EaterOfWorld_Segment next = (EaterOfWorld_Segment) head.segments.get(segmentIndex);
            if(!next.isAlive())return;
            EaterOfWorld newHead = new EaterOfWorld(level(),false);
            newHead.setHealth(next.getHealth());
            newHead.setPos(next.position());
            newHead.setYRot(next.yRotO);
            newHead.setXRot(next.xRotO);
            newHead.genSegments =  false;

            TerraBossBase last = newHead;
            for(var n : head.segments){
                var seg = (EaterOfWorld_Segment)n;
                if(seg.segmentIndex>segmentIndex+1){
                    newHead.segments.add(seg);
                    seg.segmentIndex -= (segmentIndex+1);
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

    public boolean shouldShowBossBar(){return false;};

}
