package org.confluence.mod.entity.slime;

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
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KingSlime extends Slime implements DeathAnimOptions {
    private final FloatRGB color;
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.confluence.king_slime"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_12);

    public KingSlime(EntityType<? extends Slime> slime, Level level) {
        super(slime, level);
        this.color = FloatRGB.fromInteger(0x73bcf4);
    }

    public static AttributeSupplier.Builder createSlimeAttributes(float attackDamage, int armor, float maxHealth) {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, attackDamage)
                .add(Attributes.ARMOR, armor)
                .add(Attributes.MAX_HEALTH, maxHealth)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100);
    }

    @Override
    public void tick() {
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        resetFallDistance();
        if (onGround() && !((SlimeAccessor) this).isWasOnGround()) {
            int i = getSize();
            for (int j = 0; j < i * 8; ++j) {
                float f = random.nextFloat() * Mth.TWO_PI;
                float f1 = random.nextFloat() * 0.5F + 0.5F;
                float f2 = Mth.sin(f) * (float) i * 0.5F * f1;
                float f3 = Mth.cos(f) * (float) i * 0.5F * f1;
                level().addParticle(ModParticles.ITEM_GEL.get(), getX() + (double) f2, getY(), getZ() + (double) f3, color.red(), color.green(), color.blue());
            }
        }
        this.setSize((int) (getHealth() / getMaxHealth() * 7 + 4), false);

        List<Player> playersInRange = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(40));
        if (playersInRange.isEmpty() || level().random.nextFloat() <= 0.02D) {

            for (int i = getSize(); i > 1; i--) {
                setSize(i, false);
            }
            if (level() instanceof ServerLevel serverLevel) {
                Vec3 closestPlayerPos;
                if (serverLevel.getRandomPlayer() != null) {
                    closestPlayerPos = serverLevel.getRandomPlayer().getOnPos().getCenter();
                    this.teleportTo(closestPlayerPos.x, closestPlayerPos.y, closestPlayerPos.z);
                }
            }

            playersInRange.clear();

            for (int i = 1; i < (int) (getHealth() / getMaxHealth() * 7 + 4); i++) {
                setSize(i, false);
            }
        }

        super.tick();
    }

    public void startSeenByPlayer(@NotNull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.IN_WALL)) {
            return false;
        }
        if (level() instanceof ServerLevel serverLevel) {
            BaseSlime slime = new BaseSlime(ModEntities.BLUE_SLIME.get(), serverLevel, 0x73bcf4, serverLevel.random.nextInt(1, 4));
            slime.setPos(getOnPos().getX(), getOnPos().getY(), getOnPos().getZ());
            if (ModUtils.isExpert(serverLevel)) {
                //todo 尖刺史莱姆
            }
            serverLevel.addFreshEntity(slime);
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
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F * i);

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
        return color.mixture(new FloatRGB(0, 0, 0), 0.5f).toArray();
    }
}
