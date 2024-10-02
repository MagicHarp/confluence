package org.confluence.mod.entity.boss.geoEntity;


import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.FallingStarItemEntity;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.BossSkill;
import org.confluence.mod.entity.boss.TerraBossBase;
import org.confluence.mod.entity.projectile.BaseBulletEntity;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.FallingStarItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.util.ModUtils;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animation.RawAnimation;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.confluence.mod.entity.projectile.BaseBulletEntity.Variant.EMERALD;

public class EaterOfWorld extends TerraBossBase {
    private static final float[] MAX_HEALTHS = {50f, 70f, 100f};
    private static final float[] DAMAGE = {4f, 6f, 9f};//接触伤害
    private static final float[] projDamage = {3,4,5};


    private float segmentInternal = 3.5f;
    int segmentCount = 60;//体节长度
    static float turnSpeedBase = 3f;//转向速度
    static float moveSpeedBase = 0.6f;//移动速度
    boolean genSegments = true;//是否生成体节
    float wanderPosRadius = 10;//寻点半径


    boolean ifBaseHead = false;
    int genTick = 5;//生成体节延迟
    boolean shouldMove = true;
    float moveSpeed = moveSpeedBase;
    float turnSpeed = turnSpeedBase;
    Vec3 targetPos = new Vec3(0, 0, 0);
    boolean shouldFollowTarget = true;
    LivingEntity target;
    //public List<TerraBossBase>segments = new ArrayList<>();
    public List<TerraBossBase>baseSegments = new ArrayList<>();
    public List<Float>baseSegmentsHealth = new ArrayList<>();
    public enum WonderType {UP,DOWN}
    private WonderType wanderType = WonderType.DOWN;
    public static final EntityDataAccessor<Integer> DATA_SEG_COUNT = SynchedEntityData.defineId(EaterOfWorld.class, EntityDataSerializers.INT);
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_SEG_COUNT, 0);
    }

    private void genSegments(){
        Vec3 dir = this.getForward().normalize().scale(-segmentInternal);
        EaterOfWorld_Segment temp = null;
        //segments.add(this);
        baseSegments.add(this);
        baseSegmentsHealth.add(this.getMaxHealth());
        for(int i=1;i<=segmentCount;i++){
            EaterOfWorld_Segment newSegment = new EaterOfWorld_Segment(this,level());
            newSegment.setPos(position().add(dir.scale(i*0.1)));
            newSegment.setLastSegment(Objects.requireNonNullElse(temp, this));
            temp = newSegment;
            baseSegments.add(newSegment);
            baseSegmentsHealth.add(newSegment.getMaxHealth());
            level().addFreshEntity(newSegment);
        }
        ifBaseHead = true;
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

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(MAX_HEALTHS[difficultyIdx]);
        setHealth(MAX_HEALTHS[difficultyIdx]);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DAMAGE[difficultyIdx]);

    }
    public boolean isNoGravity(){
        return true;
    }

    BossSkill dash;
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
                            //&& !isDashing   //只执行一次
                    ){
                        skills.tick = 0;
                    }

                },
                (terraBossBase)->{

                });
        wonder=new BossSkill("2","wonder",120,0,
                (terraBossBase)->{
                    //设置状态触发时目的点
                    wanderType = wanderType==WonderType.DOWN?WonderType.UP:WonderType.DOWN;
                    if(target==null)return;
                    float random1 = random.nextFloat()*360;
                    double random2 = wanderPosRadius * Math.sin(random1);
                    double random3 = wanderPosRadius * Math.cos(random1);
                    if(wanderType ==WonderType.DOWN){
                        targetPos = target.position().add(random2,-8,random3);
                    }else if(wanderType ==WonderType.UP){
                        targetPos = target.position().add(random2,8 ,random3);
                    }else{
                        targetPos = target.position().add(random1,random2,random3);
                    }
                    turnSpeed = 5;
                    moveSpeed = 0.3f;

                },
                (terraBossBase)->{
                    shouldFollowTarget = false;
                    isDashing = false;
                    moveSpeed = moveSpeedBase;
                    turnSpeed = turnSpeedBase;
                    shouldMove = true;

                    //提前结束
                    if(wanderType ==WonderType.DOWN){
                        if(distanceToSqr(targetPos)<=4){
                            skills.forceEnd();
                        }
                    }
                },
                (terraBossBase)->{
                    turnSpeed = turnSpeedBase;
                    moveSpeed = moveSpeedBase;
                }
        );

        addSkillNoAnim(wonder);
        addSkillNoAnim(dash);
        addSkillNoAnim(wonder);

    }
    private int fireTick = 0;
    private int firetickbase = 20;
    boolean isDashing = false;
    private int discardTick = 100;
    boolean firstWander = false;
    @Override
    public void tick(){
        super.tick();
        if(!level().isClientSide){
            entityData.set(DATA_SEG_COUNT,segmentCount);
            //召唤的瞬间位置为初始值，要延迟召唤segments
            if(this.tickCount > genTick && genSegments){
                genSegments();
                genSegments=false;
            }

            //没有目标禁止行为
            target = getTarget();

            //TODO 无目标消失
            /*
            if(target==null){
                if(ifBaseHead &&tickCount>discardTick ){
                    for(var n : baseSegments)
                        n.discard();
                }
                return;
            }
            */
            if(!firstWander){
                skills.forceStartIndex(2);
                skills.forceEnd();
                firstWander = true;
            }
            if(targetPos.x==0&&targetPos.y==0&&targetPos.z==0){
                targetPos = target.position();
            }
            //头部发射弹幕
            if(--fireTick<0){
                if(this.position().y>target.yo){
                    fireTick = firetickbase;
                    BaseBulletEntity bullet = new BaseBulletEntity(this,level(),EMERALD);
                    bullet.setPos(position());
                    bullet.setDamage(projDamage[difficultyIdx]);
                    bullet.shoot(target.getX()-getX(),target.getY()+1-getY(),target.getZ()-getZ(),1F,1);
                }
            }
            //转向机制
            if(shouldFollowTarget){
                this.lookAt(target, turnSpeed,80);

            }else{
                this.lookAtPos(targetPos,turnSpeed,80);
            }

            //移动机制
            if(shouldMove) {
                this.setPos(position().add(getForward().normalize().scale(moveSpeed)));
            }
            //中枢头刷新和重现机制
            if(ifBaseHead){
                int allSeg = 0;
                int cur = 0;
                int headCount = 0;
                EaterOfWorld newHead = null;
                TerraBossBase lastSeg = null;

                for(int i=0;i<baseSegments.size();i++){
                    baseSegmentsHealth.set(i,baseSegments.get(i).getHealth());
                    if(baseSegments.get(i) instanceof EaterOfWorld  eater){
                        if(!eater.ifBaseHead || !eater.isAlive()){
                            eater.removeBossEvent();
                        }else{
                            if(target instanceof ServerPlayer player){
                                ((EaterOfWorld) baseSegments.get(i)).setBossEvent(player);
                            }
                        }
                    }
                    //死亡则跳过
                    if(baseSegmentsHealth.get(i)<=0.0){
                        cur = 0;
                        continue;
                    }
                    allSeg++;
                    //头
                    if(cur==0){
                        //错误体节，替换为头                                       //被区块刷新掉的重现
                        if(baseSegments.get(i) instanceof EaterOfWorld_Segment || baseSegmentsHealth.get(i)>0 && baseSegments.get(i).isRemoved()){
                            newHead = new EaterOfWorld(level(),false);
                            newHead.setHealth(baseSegments.get(i).getHealth());
                            newHead.setPos(baseSegments.get(i).position());
                            newHead.setYRot(baseSegments.get(i).yRotO);
                            newHead.setXRot(baseSegments.get(i).xRotO);
                            newHead.genSegments =  false;
                            newHead.ifBaseHead = false;
                            //newHead.segments.add(newHead);
                            baseSegments.get(i).discard();
                            baseSegments.set(i,newHead);
                            level().addFreshEntity(newHead);
                        }

                        headCount++;
                        newHead = (EaterOfWorld) baseSegments.get(i);
                        lastSeg = newHead;
                    }else{//体节
                        EaterOfWorld_Segment curSeg = (EaterOfWorld_Segment)baseSegments.get(i);
                        //TODO 被区块刷新掉的体节重现
                        if(baseSegments.get(i).isRemoved() && tickCount % 50 == 0){
/*
                            curSeg = new EaterOfWorld_Segment(newHead,level());
                            curSeg.setPos(newHead.position());
                            level().addFreshEntity(curSeg);
*/
                        }
//                        if(newHead.segments.size()==cur){
//                            newHead.segments.add(curSeg);
//                        }
                        curSeg.head = newHead;
                        curSeg.lastSegment = lastSeg;
                        lastSeg = curSeg;

                    }

                    cur++;
                }
//                System.out.println(allSeg + " head:" + headCount);
            }
        }else{
            segmentCount = entityData.get(DATA_SEG_COUNT);
        }
    }

    @Override//死亡时
    public void onRemovedFromWorld() {
        this.bossEvent.removeAllPlayers();
        if(!level().isClientSide && ifBaseHead){
            int aliveCount = 0;
            for(var n : baseSegments){
                if(n.getHealth()>0.0 && n!=this){
                    if(n instanceof EaterOfWorld_Segment){
                        EaterOfWorld newHead = new EaterOfWorld(level(),false);
                        newHead.setPos(n.position());
                        transformHead(newHead);
                        level().addFreshEntity(newHead);
                        n.discard();
                    }
                    else{
                        transformHead((EaterOfWorld) n);
                    }

                    break;
                }
                if(n.isAlive()) aliveCount++;

            }
            if(aliveCount==0){
                //生成掉落物
                ItemStack stack = Materials.FALLING_STAR.get().getDefaultInstance();
                stack.setCount(10);
                level().addFreshEntity(new ItemEntity(level(),getX(),getY(),getZ(), stack));
            }
        }
        super.onRemovedFromWorld();
    }
    public void transformHead(EaterOfWorld newHead){
        newHead.setXRot(xRotO);
        newHead.setYRot(yRotO);
        newHead.setPos(position());
        newHead.genSegments = false;
        if(ifBaseHead) {
            newHead.ifBaseHead = true;
            newHead.baseSegments = baseSegments;
            newHead.baseSegmentsHealth = baseSegmentsHealth;
        }
    }



    @Override//boss条更新
    protected void customServerAiStep() {
        super.customServerAiStep();
        if(ifBaseHead){
            float health = 0;
            float maxHp = 0;
            int index = 0;
            for (TerraBossBase segment : baseSegments) {
                health += baseSegmentsHealth.get(index);
                maxHp += segment.getMaxHealth();
                index++;
            }
            this.bossEvent.setProgress(health / maxHp);
        }
    }
    @Override // boss条显示
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (ifBaseHead) this.bossEvent.addPlayer(player);
    }

    public boolean shouldShowBossBar(){
        return false;
    }

    @Override // boss条消失
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    public void removeBossEvent(){
        this.bossEvent.removeAllPlayers();
    }
    public void setBossEvent(ServerPlayer player){
        this.bossEvent.addPlayer(player);
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
