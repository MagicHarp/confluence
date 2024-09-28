package org.confluence.mod.entity.boss.geoEntity;

import com.xiaohunao.enemybanner.EntityBannerPattern;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.entity.boss.BossSkill;
import org.confluence.mod.entity.boss.TerraBossBase;
import org.confluence.mod.entity.projectile.IceBladeSwordProjectile;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.RawAnimation;


public class KingSkull extends TerraBossBase implements GeoEntity {

    //定义动画
    private static final RawAnimation roll = RawAnimation.begin().thenPlay("animation.model.roll");
    private static final RawAnimation fire = RawAnimation.begin().thenPlay("animation.model.fire");

    //子部件
    KingSkullHand handLeft;
    KingSkullHand handRight;

    public enum HandState{
        follow,attack
    }

    public KingSkull(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setNoGravity(true);
        this.noPhysics = true;

        handLeft = new KingSkullHand(this,level(),false);
        handRight = new KingSkullHand(this,level(),true);



    }
    public boolean isNoGravity(){
        return true;
    }

    //技能与动画匹配
    @Override
    public void addSkills() {
        //id作为强制跳转的标识符
        addSkill(new BossSkill("roll","animation.model.roll",5*20,1),roll);
        addSkill(new BossSkill("fire1","animation.model.fire",30,25),fire);
        addSkill(new BossSkill("fire2","animation.model.fire",30,25),fire);
        addSkill(new BossSkill("fire3","animation.model.fire",30,25),fire);
        addSkill(new BossSkill("fire4","animation.model.fire",30,25),fire);
        addSkill(new BossSkill("fire5","animation.model.fire",30,25),fire);
        addSkill(new BossSkill("fire6","animation.model.fire",30,25),fire);
        addSkillNoAnim(new BossSkill("hand","hand",100,20));
        addSkillNoAnim(new BossSkill("dash","dash",4*20,0));
    }


    //技能逻辑
    public Vec3 normalDash;//冲撞目标点
    public boolean onDash = false;
    public boolean onNormalDash = false;
    public HandState handState = HandState.follow;

    @Override
    public void tick(){
        super.tick();

        if(tickCount == 20) {
            handLeft.setPos(position());
            handRight.setPos(position());
            level().addFreshEntity(handLeft);
            level().addFreshEntity(handRight);
        }
//定义释放技能时状态
        Entity entity = getTarget();

        if(entity != null){
            this.lookAt(entity,10,10);
            //对每个技能设置攻击逻辑
            String skillName = skills.getCurSkill();
            switch (skillName){
                case "animation.model.roll":
                    handState = HandState.follow;
                    onDash = true;
                    onNormalDash = false;
                    if(!skills.canContinue())break;
                    skillRoll();
                    break;
                case "animation.model.fire":
                    onDash = false;
                    handState = HandState.follow;
                    onNormalDash = false;
                    if(skills.canTrigger())
                        skillFire();
                    break;
                case "dash":
                    handState = HandState.follow;
                    if(!skills.canContinue())return;
                    onDash = true;
                    onNormalDash = true;


                    if(skills.tick==1) normalDash = entity.position().add(0,2,0);

                    break;
                case "hand":
                    onDash = false;
                    handState = HandState.attack;
                    onNormalDash = false;
                    break;


            }
            //这里放释放技能同时执行的动作


            //额外动作
            //非冲撞增加向上向量
            if(!onDash)
                this.addDeltaMovement(entity.position().subtract(position()).add(0,10,0).multiply(0,1,0).normalize().scale(0.1));
            //冲撞位移
            if(onNormalDash && normalDash!=null){
                dash(normalDash);
            }
        }

    }

    //技能实现
    private void skillRoll(){//技能：滚动
        Entity target = getTarget();
        if(target==null) return;
        System.out.println("ROLL");
        this.addDeltaMovement(target.position().subtract(position()).normalize().scale(0.15));
    }
    //远程攻击
    private void skillFire(){//远程攻击
        LivingEntity target = getTarget();
        IceBladeSwordProjectile  wave = new IceBladeSwordProjectile(target);
        wave.setPos(this.position());
        System.out.println("FIRE");
        wave.shoot(target.getX()-getX(),target.getY()+1-getY(),target.getZ()-getZ(),1F,5);
        level().addFreshEntity(wave);

    }
    //普通冲撞
    private void dash(Vec3 pos){//普通冲撞
        Entity target = getTarget();
        if(target==null)return;
        System.out.println("DASH");
        this.addDeltaMovement(pos.subtract(position()).normalize().scale( 0.3));
    }


    @Override//死亡时
    public void onRemovedFromWorld() {
        handLeft.discard();
        handRight.discard();
        super.onRemovedFromWorld();
    }

}