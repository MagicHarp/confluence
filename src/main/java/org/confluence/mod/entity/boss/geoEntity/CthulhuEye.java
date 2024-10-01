package org.confluence.mod.entity.boss.geoEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.BossSkill;
import org.confluence.mod.entity.boss.TerraBossBase;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.demoneye.DemonEyeWanderGoal;
import org.confluence.mod.util.DeathAnimOptions;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.function.Predicate;

import static org.confluence.mod.util.ModUtils.switchByDifficulty;
import static org.confluence.mod.util.ModUtils.updateEntityRotation;

@SuppressWarnings("all")
public class CthulhuEye extends TerraBossBase implements DeathAnimOptions, GeoEntity {
    private static final float[] MAX_HEALTHS = {728f, 946f, 1206f};
    private static final float[] DAMAGE = {4f, 6f, 9f};//一阶段接触伤害
    private static final float[] CRAZY_DAMAGE = {6f, 10f, 15f};//二阶段接触伤害
    private static final float[] MOVE_SPEED = {0.5f,0.6f,0.7f};
    private static final float[] CRAZY_PERCENTAGE = {0.25f, 0.25f, 0.25f};

    private final int followMinDistance = 16; //最近跟随距离的平方
    private final int distanceAbove = 3; //悬在玩家blockPos上距离
    private final float dashFactor = 1.5f; //冲刺增伤
    private final float speedFactor = 2f; //冲刺加速
    private final float stage2SpeedFactor = 1.5f; //二阶段加速加成
    private final float minDashDistanceSqr = 20;
    private int difficultyIdx;
    private int stage = 1; //阶段

    //定义技能参数
    private int summonCDAll = 15; //仆从召唤cd
    private int summonCD = summonCDAll;

    private final int stage2_dashCount_base = -5 + 3;
    private int stage2_dashCount = stage2_dashCount_base; //二阶段冲刺次数，每掉1/10的血+1

    private Vec3 dashPos;
    private Vec3 dashDir;

    public CthulhuEye(EntityType<CthulhuEye> entityType, Level level) {
        super(entityType, level);
        this.difficultyIdx = switchByDifficulty(level(), 0, 1, 2);
        //初始属性
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(MAX_HEALTHS[difficultyIdx]);
        setHealth(MAX_HEALTHS[difficultyIdx]);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DAMAGE[difficultyIdx]);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);

    }

    public CthulhuEye(Level level) {
        this(ModEntities.CTHULHU_EYE.get(), level);
    }

    // 定义动画
    private static final RawAnimation type1 = RawAnimation.begin().thenPlay("type_1");
    private static final RawAnimation type1run = RawAnimation.begin().thenPlay("type_1_run");
    private static final RawAnimation switching = RawAnimation.begin().thenPlay("switching");
    private static final RawAnimation type2 = RawAnimation.begin().thenPlay("type_2");
    private static final RawAnimation type2run = RawAnimation.begin().thenPlay("type_2_run");

    // 定义技能类型
    BossSkill stage1_stare;
    BossSkill state1_dash;
    BossSkill switch_1_to_2;
    BossSkill stage2_stare;
    BossSkill state2_dash;

    @Override
    public void addSkills() {
        this.difficultyIdx = switchByDifficulty(level(), 0, 1, 2);
        // 定义技能实现
        // 定格在玩家正上方
        this.stage1_stare = new BossSkill("1", "type_1", 5 * 20, 0,
                terraBossBase -> {},
                terraBossBase -> {
                    if (getTarget() == null) return;
                    cslLookAt(10);
                    // 生成粒子
                    for (int i = 0; i < 10; i++) {
                        BlockPos pos = BlockPos.containing(position());
                        ((ServerLevel) level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ModBlocks.TR_CRIMSON_STONE.get().defaultBlockState()),
                                pos.getX() + 0.5F,
                                pos.getY() + 0.75F,
                                pos.getZ() + 0.5F,
                                10, 0.0625F, 0.0625F, 0.0625F, 0.15F);
                    }
                    // 生成仆从
                    spawnMinions(getTarget());
                    // 向玩家正上方移动
                    Vec3 tar = getTarget().position().add(new Vec3(0, distanceAbove, 0));
                    if (distanceToSqr(tar) > followMinDistance) addDeltaMovement(tar.subtract(position()).normalize().scale(MOVE_SPEED[difficultyIdx] / 10));

                },
                terraBossBase -> {}
        );
        // 延迟20tick冲刺10tick
        this.state1_dash = new BossSkill("2", "type_1_run", 30, 20,
                terraBossBase -> {

                },
                terraBossBase -> {

                    // 延迟冲刺
                    if (getTarget() == null) return;
                    if (!skills.canContinue()) {
                        // 调整方向
                        cslLookAt(50);
                        // 不精准度
                        dashPos = getTarget().position().subtract(position()).add(0, 1, 0).offsetRandom(RandomSource.create(), 1);
                        return;
                    }
                    // 冲刺增加伤害
                    //getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(
                    //new AttributeModifier(DASH_UUID.toString(),2, AttributeModifier.Operation.ADDITION));
                    getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DAMAGE[difficultyIdx] * dashFactor);

                    if (dashPos != null) this.setDeltaMovement(dashPos.normalize().scale(MOVE_SPEED[difficultyIdx] * speedFactor));
                },
                terraBossBase -> {
                    // 结束冲刺移除加成
                    getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DAMAGE[difficultyIdx]);
                    //getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DASH_UUID);
                }
        );
        // 转换阶段
        this.switch_1_to_2 = new BossSkill("3", "switching", 40, 0,
                terraBossBase -> {
                    if (stage == 1){
                        skills.forceStartIndex(0);
                        return;
                    }
                    summonCD = 0;
                    summonCDAll = 7;
                },
                terraBossBase -> {


                    spawnMinions(getTarget());
                    },
                terraBossBase -> {
                    // 增加属性
                    getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CRAZY_DAMAGE[difficultyIdx]);

                });
        this.stage2_stare = new BossSkill("4", "type_2", 3 * 20, 0,
                terraBossBase -> {
                },
                terraBossBase -> {
                    if (getTarget() == null) return;
                    cslLookAt(10);

                    // 向玩家正上方移动
                    Vec3 tar = getTarget().position().add(new Vec3(0, distanceAbove, 0));
                    if (distanceToSqr(tar) > followMinDistance) addDeltaMovement(tar.subtract(position()).normalize().scale(MOVE_SPEED[difficultyIdx] * stage2SpeedFactor / 10));
                },
                terraBossBase -> {
                    // 生成冲撞次数
                    this.stage2_dashCount = (int) (stage2_dashCount_base + 10 - this.getHealth() / (getMaxHealth() / 10));
                    getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CRAZY_DAMAGE[difficultyIdx]);
                }
        );
        this.state2_dash = new BossSkill("5", "type_2_run", 20, 10,
                terraBossBase -> {
                    //setDeltaMovement(0, 0, 0);
                    if(this.getHealth()/getMaxHealth()<0.3f && stage2_dashCount < 4){
                        state2_dash.timeTrigger = 5;
                    }else state2_dash.timeTrigger = 10;
                },
                terraBossBase -> {
                    // 延迟冲刺
                    if (getTarget() == null) return;
                    if (!skills.canContinue()) {
                        // 调整方向
                        cslLookAt(360);
                        // 不精准度
                        dashPos = getTarget().position().subtract(position()).add(0, 1, 0).offsetRandom(RandomSource.create(), 4);
                        dashDir = dashPos.subtract(position());
                        //冲撞距离过小则后退
                        if(distanceToSqr(getTarget()) < minDashDistanceSqr) setDeltaMovement(dashPos.normalize().scale(-1));
                        return;
                    }
                    //updateEntityRotation(this,dashDir);
                    lookAtPos(dashDir,50,80);

                    // 冲刺增加伤害
                    getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CRAZY_DAMAGE[difficultyIdx] * dashFactor);
                    if (dashPos != null) this.setDeltaMovement(dashPos.normalize().scale(MOVE_SPEED[difficultyIdx] * speedFactor * stage2SpeedFactor));
                },
                terraBossBase -> {
                    // 结束冲刺移除加成
                    getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CRAZY_DAMAGE[difficultyIdx]);
                    if (--stage2_dashCount <= 0) {
                        // 冲刺完
                        stage2_dashCount = stage2_dashCount_base;
                        skills.forceStartIndex(5);
                    } else {
                        //继续冲刺
                        skills.forceStartIndex(6);
                    }
                }
        );
        // 添加技能序列
        addSkill(stage1_stare, type1); // 0
        addSkill(state1_dash, type1run); // 1
        addSkill(state1_dash, type1run); // 2
        addSkill(state1_dash, type1run); // 3
        addSkill(switch_1_to_2, switching); // 4
        addSkill(stage2_stare, type2); // 5
        addSkill(state2_dash, type2run); // 6
    }

    public void endDash2(){

    }


    private void cslLookAt(float maxAngleY) {
        var pEntity = getTarget();
        if (pEntity != null) {
            lookAt(getTarget(), maxAngleY, 85);

        }
    }




    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 2.2)
                .add(Attributes.ARMOR, 12.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FOLLOW_RANGE, 100.0);

    }

    private void spawnMinions(LivingEntity target) {
        if (level() instanceof ServerLevel serverLevel) {
            if (--summonCD > 0) return;
            summonCD = summonCDAll;
            DemonEye eye = new DemonEye(ModEntities.DEMON_EYE.get(), serverLevel) {
                @Override
                protected boolean shouldDropLoot() {
                    return false;
                }
            };  //todo 仆从
            eye.setHealth(8);
            eye.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8);
            eye.setPos(position().add(getForward().normalize().scale(-1)));
            eye.setTarget(target);
            serverLevel.addFreshEntity(eye);
        }
    }



    @Override // 受伤音效
    protected SoundEvent getHurtSound(DamageSource damageSource) {return SoundEvents.SLIME_HURT;}

    @Override
    public boolean isNoGravity(){ return true; }

    // 转换阶段
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.getHealth() / getMaxHealth() < 0.5 && stage == 1) {
            stage = 2;
            skills.forceStartIndex(4); // 强制执行技能序列
        }
        return super.hurt(pSource, pAmount);
    }

    // 同步旋转
    @Override
    public void tick() {
        super.tick();
        syncRot();
    }

    @Override // boss条显示
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        player.sendSystemMessage(Component.translatable("bossevent.confluence.cthulhu_eye.generate").withStyle(ChatFormatting.DARK_PURPLE));
    }
    @Override // boss条消失
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        player.sendSystemMessage(Component.translatable("bossevent.confluence.cthulhu_eye.death").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
