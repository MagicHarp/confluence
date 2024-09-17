package org.confluence.mod.entity.boss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.demoneye.DemonEyeVariant;
import org.confluence.mod.util.DeathAnimOptions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static org.confluence.mod.util.ModUtils.isExpert;
import static org.confluence.mod.util.ModUtils.isMaster;

public class CthulhuEye extends DemonEye implements DeathAnimOptions, IBossFSM, GeoEntity {
    int difficultyIdx;

    private static final float[] MAX_HEALTHS = {1346f, 1056f, 812f};
    private static final float[] DAMAGE = {12.5f, 9f, 4.5f};
    private static final float[] CRAZY_DAMAGE = {24f, 16f, 8f};
    private static final float[] CRAZY_PERCENTAGE = {0.65f, 0.5f, 0.5f};

    public CthulhuEye(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.difficultyIdx = isMaster(level()) ? 0 : isExpert(level()) ? 1 : 2;
        attrInit(this.getNearbyPlayers(100.0D));
        this.AIState = STATE_NORMAL;
        toState(STATE_NORMAL);
    }

    @Override
    public void tick() {
        super.tick();

        bossEvent.setProgress(getHealth() / getMaxHealth());
        bossEvent.setName(getDisplayName());

        if (level().getDayTime() <= 12000 && level().getDayTime() >= 0) {
            this.AIState = STATE_MORNING;
            toState(STATE_MORNING);
            return;
        }

        if (!isNoAi()) {
            AI();
        }

        if (AIState.equals(STATE_NORMAL)) {
            if (getHealth() <= CRAZY_PERCENTAGE[difficultyIdx] * getMaxHealth()) {
                triggerAnim("controller", "switching");
                getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CRAZY_DAMAGE[difficultyIdx]);
                for (int i = 0; i < 20; i++) {
                    if (level() instanceof ServerLevel level) {
                        BlockPos pos = BlockPos.containing(position());
                        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ModBlocks.TR_CRIMSON_STONE.get().defaultBlockState()),
                                pos.getX() + 0.5F,
                                pos.getY() + 0.75F,
                                pos.getZ() + 0.5F,
                                10, 0.0625F, 0.0625F, 0.0625F, 0.15F);
                    }
                }
                spawnMinions(getTarget(), 5);
                this.AIState = STATE_CRAZY;
                toState(STATE_CRAZY);
            }
            if (level().random.nextDouble() <= 0.0055555555555556D) {
                spawnMinions(getTarget(), 3);
                this.AIState = STATE_DASH;
                toState(STATE_DASH);
            }
        } else if (AIState.equals(STATE_CRAZY)) {
            triggerAnim("controller", "type_2");
            if (level().random.nextDouble() <= mapValue(getHealth(), 0, CRAZY_PERCENTAGE[difficultyIdx] * getMaxHealth(),
                    0.00714285D, 0.016666666D)) {
                spawnMinions(getTarget(), 1);
                this.AIState = STATE_CRAZY_DASH;
                toState(STATE_CRAZY_DASH);
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 2.2)
                .add(Attributes.ARMOR, 12.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100.00)
                .add(Attributes.FOLLOW_RANGE, 100.0);
    }

    private void attrInit(List<Player> nearbyPlayers) {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(MAX_HEALTHS[difficultyIdx]);
        setHealth(MAX_HEALTHS[difficultyIdx]);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DAMAGE[difficultyIdx]);

        for (Player player : nearbyPlayers) {
            //todo music
        }
    }

    @Override
    public void setVariant(DemonEyeVariant pVariant) {
        attrInit(getNearbyPlayers(100.0D));
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        bossEvent.removePlayer(pServerPlayer);
    }

    private static final State<CthulhuEye> STATE_NORMAL = new State<>() {
        @Override
        public void tick(CthulhuEye boss) {}
    };

    private static final State<CthulhuEye> STATE_MORNING = new State<>() {
        @Override
        public void tick(CthulhuEye boss) {
            boss.setDeltaMovement(0, 2, 0);
        }
    };

    private static final State<CthulhuEye> STATE_DASH = new State<>() {
        @Override
        public void tick(CthulhuEye boss) {
            boss.triggerAnim("controller", "type_1_run");
            boss.dash();
            if (boss.level().random.nextDouble() <= 0.075D) {   //todo 史
                boss.triggerAnim("controller", "type_1");
                boss.AIState = STATE_NORMAL;
                boss.toState(STATE_NORMAL);
            }
        }
    };

    private static final State<CthulhuEye> STATE_CRAZY_DASH = new State<>() {
        @Override
        public void tick(CthulhuEye boss) {
            boss.triggerAnim("controller", "type_2_run");
            boss.dash();
            if (boss.level().random.nextDouble() <= 0.065D) {
                boss.triggerAnim("controller", "type_2");
                boss.AIState = STATE_CRAZY;
                boss.toState(STATE_CRAZY);
            }
        }
    };

    private static final State<CthulhuEye> STATE_CRAZY = new State<>() {
        @Override
        public void tick(CthulhuEye boss) {}
    };

    private State<CthulhuEye> AIState;
    private final ServerBossEvent bossEvent = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_12);

    private void dash() {
        if (getTarget() != null) {
            moveEntityTo(self(), getTarget().position(), 1.2);
        } else {
            moveEntityTo(self(), getNearbyPlayers(200).get(0).position(), 1.2);
        }
    }

    private void spawnMinions(LivingEntity target, int num) {
        if (level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < num; i++) {
                DemonEye eye = new DemonEye(ModEntities.DEMON_EYE.get(), serverLevel) {
                    @Override
                    protected boolean shouldDropLoot() {
                        return false;
                    }
                };  //todo 仆从
                eye.setHealth(8);
                eye.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8);
                eye.setPos(getOnPos().getX(), getOnPos().getY() + 0.5, getOnPos().getZ());
                eye.setTarget(target);
                serverLevel.addFreshEntity(eye);
            }
        }
    }

    public static void moveEntityTo(Entity entity, Vec3 to, double speed) {
        Vec3 entityPos = entity.position();
        Vec3 direction = to.subtract(entityPos).normalize();

        Vec3 newMovement = direction.scale(speed);

        entity.setDeltaMovement(newMovement);
    }

    /**
     * 转到新的state
     *
     * @param newState
     */
    @Override
    public void toState(State newState) {
        if (newState == this.AIState)
            return;
        this.AIState.leave(this);
        this.AIState = newState;
        this.AIState.enter(this);
    }

    @Override
    public void AI() {
        if (!level().isClientSide()) {
            this.AIState.tick(this);
        }
    }

    @Override
    public List<Player> getNearbyPlayers(double radius) {
        return level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(radius));
    }

    public static double mapValue(double input, double inputMin, double inputMax, double outputMin, double outputMax) {
        double normalized = (input - inputMin) / (inputMax - inputMin);
        return outputMin + normalized * (outputMax - outputMin);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state ->
                state.setAndContinue(RawAnimation.begin().thenLoop("type_1")))
                .triggerableAnim("type_2", RawAnimation.begin().thenLoop("type_2"))
                .triggerableAnim("switching", RawAnimation.begin().thenPlay("switching"))
                .triggerableAnim("type_1_run", RawAnimation.begin().thenLoop("type_1_run"))
                .triggerableAnim("type_2_run", RawAnimation.begin().thenLoop("type_2_run"))
        );
    }
}
