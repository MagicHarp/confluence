package org.confluence.mod.entity.boss;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.DeathAnimOptions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.confluence.mod.util.ModUtils.isExpert;
import static org.confluence.mod.util.ModUtils.isMaster;

public class KingSlime extends Slime implements DeathAnimOptions {
    private static final FloatRGB COLOR = FloatRGB.fromInteger(0x73bcf4);
    private static final float[] BLOOD_COLOR = COLOR.mixture(FloatRGB.ZERO, 0.5f).toArray();
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.confluence.king_slime"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_12);
    private int waitTick;

    public KingSlime(EntityType<? extends Slime> slime, Level level) {
        super(slime, level);
        this.waitTick = 40;
        init();
    }

    public static AttributeSupplier.Builder createSlimeAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.ATTACK_DAMAGE, 1.0)
            .add(Attributes.ARMOR, 10.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 10.00)
            .add(Attributes.FOLLOW_RANGE, 100.0);
    }

    private void init() {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(isMaster(level()) ? 928.0 : isExpert(level()) ? 812.0 : 580.0);
        setHealth(isMaster(level()) ? 928.0F : isExpert(level()) ? 812.0F : 580.0F);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(isMaster(level()) ? 12.5 : isExpert(level()) ? 9.0 : 4.5);
    }

    @Override
    public void tick() {
        if (!level().isClientSide) {
            if (waitTick > 0) {
                waitTick--;
            }
        }
        bossEvent.setProgress(getHealth() / getMaxHealth());
        resetFallDistance();
        if (level().random.nextDouble() <= (isMaster(level()) ? 0.05 : isExpert(level()) ? 0.035 : 0.015)) {
            Vec3 motion = getDeltaMovement();
            setDeltaMovement(motion.x, motion.y + (isMaster(level()) ? 0.6 : 0.35), motion.z);
        }
        if (onGround() && !((SlimeAccessor) this).isWasOnGround()) {
            int i = getSize();
            for (int j = 0; j < i * 8; ++j) {
                float f = random.nextFloat() * Mth.TWO_PI;
                float f1 = random.nextFloat() * 0.5F + 0.5F;
                float f2 = Mth.sin(f) * (float) i * 0.5F * f1;
                float f3 = Mth.cos(f) * (float) i * 0.5F * f1;
                level().addParticle(ModParticles.ITEM_GEL.get(), getX() + (double) f2, getY(), getZ() + (double) f3, COLOR.red(), COLOR.green(), COLOR.blue());
            }
        }
        setSize((int) ((getHealth() / 600) * 7 + 4), false);

        List<Player> playersInRange = level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(100));
        List<Player> playersInRange2 = level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(150));
        if (playersInRange.isEmpty() && playersInRange2.isEmpty()) {
            discard();
        }
        if (playersInRange.isEmpty() || level().random.nextFloat() <= (isMaster(level()) ? 0.05D : isExpert(level()) ? 0.03D : 0.01D)) {

            if (this.waitTick == 0) {
                for (int i = getSize(); i > 1; i--) {
                    setSize(i, false);
                }
                if (level() instanceof ServerLevel serverLevel) {
                    Vec3 closestPlayerPos;
                    if (serverLevel.getRandomPlayer() != null) {
                        closestPlayerPos = serverLevel.getRandomPlayer().getOnPos().getCenter();
                        teleportTo(closestPlayerPos.x, closestPlayerPos.y + 0.75F, closestPlayerPos.z);
                    }
                }

                playersInRange.clear();

                for (int i = 1; i < (int) ((getHealth() / 600) * 7 + 4); i++) {
                    setSize(i, false);
                }
                this.waitTick = 40;
            }
        }

        super.tick();
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

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.IN_WALL)) {
            return false;
        }
        if (level() instanceof ServerLevel serverLevel) {
            if (level().random.nextDouble() <= (isMaster(level()) ? 0.9D : isExpert(level()) ? 0.75D : 0.5D)) {
                BaseSlime slime = new BaseSlime(ModEntities.BLUE_SLIME.get(), serverLevel, 0x73bcf4, 3);
                slime.setPos(getOnPos().getX(), getOnPos().getY(), getOnPos().getZ());
                if (isExpert(serverLevel)) {
                    //todo 尖刺史莱姆
                }
                serverLevel.addFreshEntity(slime);
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected boolean spawnCustomParticles() {
        return true;
    }

    @Override
    public void setSize(int pSize, boolean pResetHealth) {
        int i = Mth.clamp(pSize, 1, 127);
        entityData.set(ID_SIZE, i);
        reapplyPosition();
        refreshDimensions();
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1F * i);

        this.xpReward = i;
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        brain.clearMemories();
        setRemoved(removalReason);
        invalidateCaps();
    }


    @Override
    public float[] getBloodColor() {
        return BLOOD_COLOR;
    }
}
