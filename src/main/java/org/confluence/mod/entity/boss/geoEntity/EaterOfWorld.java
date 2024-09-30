package org.confluence.mod.entity.boss.geoEntity;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.BossSkill;
import org.confluence.mod.entity.boss.TerraBossBase;
import org.confluence.mod.util.ModUtils;
import software.bernie.geckolib.core.animation.RawAnimation;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EaterOfWorld extends TerraBossBase {
    private float segmentInternal = 3.5f;
    int segmentCount = 30;//体节长度
    static float turnSpeedBase = 3f;//转向速度
    static float moveSpeedBase = 0.5f;//移动速度
    boolean genSegments = true;//是否生成体节
    float wanderPosRadius = 20;//寻点半径


    int genTick = 5;//生成体节延迟
    boolean shouldMove = true;
    float moveSpeed = moveSpeedBase;
    float turnSpeed = turnSpeedBase;
    Vec3 targetPos = new Vec3(0, 0, 0);
    boolean shouldFollowTarget = true;
    LivingEntity target;
    public List<TerraBossBase>segments = new ArrayList<>();
    public enum WonderType {UP,DOWN}
    private WonderType wanderType = WonderType.DOWN;
    private void genSegments(){
        Vec3 dir = this.getForward().normalize().scale(-segmentInternal);
        EaterOfWorld_Segment temp = null;
        for(int i=1;i<=segmentCount;i++){
            EaterOfWorld_Segment newSegment = new EaterOfWorld_Segment(this,level(),i);
            newSegment.setPos(position().add(dir.scale(i)));

            newSegment.setLastSegment(Objects.requireNonNullElse(temp, this));

            temp = newSegment;
            segments.add(newSegment);
            level().addFreshEntity(newSegment);
        }
    }
    public EaterOfWorld(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        if(!level.isClientSide){
            if(getTarget()!=null){
                this.moveTo(getTarget().position());
            }
        }
        this.noPhysics = true;
    }
    public EaterOfWorld(Level level, boolean genSegments) {
        super(ModEntities.EATER_OF_WORLD.get(), level);
        if(!level.isClientSide){
            if(getTarget()!=null){
                this.moveTo(getTarget().position());
            }
        }
        this.noPhysics = true;

        this.genSegments = genSegments;
    }
    public boolean isNoGravity(){
        return true;
    }
    //这里必须static，否则不会初始化时生成
    private static final RawAnimation roll = RawAnimation.begin().thenPlay("worm.roll");
    private static final RawAnimation bite = RawAnimation.begin().thenPlay("worm.bite");

    BossSkill dash;
    BossSkill follow;
    BossSkill wonder;


    @Override
    public void addSkills() {
        dash = new BossSkill("0","dash",120,10,
                (terraBossBase)->{
                    isDashing = true;
                    moveSpeed = moveSpeedBase;
                    turnSpeed = turnSpeedBase;
                    shouldMove = true;
                    shouldFollowTarget=true;


                },
                (terraBossBase)->{

                    if(target==null) {
                        target = getTarget();
                        return;
                    }
                    //设置触发条件，否则取消tick
                    if(ModUtils.angleBetween(getForward(),target.position().subtract(position()))>20    //角度
                            && distanceToSqr(target) > 20   //距离
                            && !isDashing   //只执行一次
                    ){
                        skills.tick = 0;
                    }

                },
                (terraBossBase)->{

                });
        follow=new BossSkill("1","follow",120,1);
        wonder=new BossSkill("2","wonder",120,5,
                (terraBossBase)->{
                    //设置状态触发时目的点

                    if(target==null)return;
                    float random1 = random.nextFloat()*wanderPosRadius*2-wanderPosRadius;
                    float random2 = random.nextFloat()*wanderPosRadius*2-wanderPosRadius;
                    float random3 = random.nextFloat()*wanderPosRadius*2-wanderPosRadius;
                    if(wanderType ==WonderType.DOWN){
                        targetPos = target.position().add(random1,-Math.abs(random2),random3);
                    }else if(wanderType ==WonderType.UP){
                        targetPos = target.position().add(random1,Math.abs(random2) ,random3);
                    }else{
                        targetPos = target.position().add(random1,random2,random3);
                    }

                },
                (terraBossBase)->{
                    shouldFollowTarget = false;
                    isDashing = false;
                    moveSpeed = moveSpeedBase;
                    turnSpeed = turnSpeedBase;
                    shouldMove = true;

                    //提前结束
                    if(wanderType ==WonderType.DOWN){
                        if(distanceToSqr(targetPos)<=36){
                            skills.forceEnd();
                        }
                    }
                },
                (terraBossBase)->{

                }
        );

        addSkillNoAnim(dash);
        addSkillNoAnim(wonder);
        addSkillNoAnim(wonder);
        //addSkillNoAnim(follow);

    }


    boolean isDashing = false;
    @Override
    public void tick(){
        super.tick();

        if(!level().isClientSide){


            //召唤的瞬间位置为初始值，要延迟召唤segments
            if(this.tickCount==genTick && genSegments){
                genSegments(); }

            //没有目标禁止行为
            target = getTarget();

            //无目标消失
            if(target==null){
                if(tickCount>100)discard();
                return;
            }


            System.out.println(skills.getCurSkill() + shouldFollowTarget+skills.tick);
            //转向机制
            if(shouldFollowTarget){
                this.lookAt(getTarget(), turnSpeed,80);

            }else{
                this.lookAtPos(targetPos,turnSpeed,80);
            }

            //移动机制
            if(shouldMove) {
                this.setPos(position().add(getForward().normalize().scale(moveSpeed)));
            }

            if(this.segmentCount==0)discard();

        }
    }
    @Override//死亡时
    public void onRemovedFromWorld() {
        if(!level().isClientSide && !segments.isEmpty()){
            EaterOfWorld newHead = new EaterOfWorld(level(),false);
            newHead.setXRot(xRotO);
            newHead.setYRot(yRotO);
            newHead.setPos(position());
            newHead.genSegments = false;

            level().addFreshEntity(newHead);
            TerraBossBase last = newHead;

            for(var n : segments){
                var seg = (EaterOfWorld_Segment)n;
                if(seg.segmentIndex>1){
                    newHead.segments.add(n);
                    seg.segmentIndex -=1;
                    seg.head = newHead;
                    seg.lastSegment = last;
                    last = seg;
                }else{//移除第二节点
                    ((EaterOfWorld_Segment) n).genNewHeadOnRemove = false;
                    n.discard();
                }
            }
        }
        super.onRemovedFromWorld();
    }

    @Override//boss条更新
    protected void customServerAiStep() {
        super.customServerAiStep();
        if(shouldShowBossBar()){
            float health = this.getHealth();
            float maxHp = this.getMaxHealth();
            for (TerraBossBase segment : segments) {
                health += segment.getHealth();
                maxHp += segment.getMaxHealth();
            }
            this.bossEvent.setProgress(health / maxHp);
        }

    }

/*
    //temp
    public LivingEntity getTarget(){
        var list = getNearbyPlayers(100);
        return list.isEmpty()?list.get(0):null;
    }
    public List<Player> getNearbyPlayers(double radius) {
        return level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(radius));
    }
    */
}
