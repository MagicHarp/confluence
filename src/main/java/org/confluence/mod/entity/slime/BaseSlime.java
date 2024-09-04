package org.confluence.mod.entity.slime;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class BaseSlime extends Slime implements DeathAnimOptions {
    private final int size;
    private final FloatRGB color;

    public BaseSlime(EntityType<? extends Slime> slime, Level level, int color, int size) {
        super(slime, level);
        this.size = size;
        // setSize在constructor中调用时size还没更新，再变一遍
        setSize(size, false);
        this.color = FloatRGB.fromInteger(color);
    }

    public static AttributeSupplier.Builder createSlimeAttributes(float attackDamage, int armor, float maxHealth) {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, attackDamage)
                .add(Attributes.ARMOR, armor)
                .add(Attributes.MAX_HEALTH, maxHealth);
    }

    public static boolean checkSlimeSpawn(EntityType<? extends Mob> type, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (!(pLevel instanceof Level level)) {
            return false;
        }
        if (!checkMobSpawnRules(type, pLevel, pSpawnType, pPos, pRandom)) {
            return false;
        } else if (type == ModEntities.BLUE_SLIME.get() || type == ModEntities.GREEN_SLIME.get() || type == ModEntities.PURPLE_SLIME.get()
                || type == ModEntities.ICE_SLIME.get() || type == ModEntities.DESERT_SLIME.get() || type == ModEntities.JUNGLE_SLIME.get()
                || type == ModEntities.PINK_SLIME.get()) {
            int y = pPos.getY();
            return y > 30 && y < 260 && level.isDay() && pLevel.canSeeSky(pPos);
        } else if (type == ModEntities.YELLOW_SLIME.get() || type == ModEntities.RED_SLIME.get()) {
            return pLevel.getBrightness(LightLayer.SKY, pPos) == 0 && pPos.getY() > 30;
        } else if (type == ModEntities.BLACK_SLIME.get()) {
            return pLevel.getBrightness(LightLayer.SKY, pPos) == 0 && pPos.getY() <= 30;
        }
        // 剩下的条件用方块的isValidSpawn方法
        return false;
    }

    @Override
    public void tick() {
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
        if (super.isInWater() && getType() == ModEntities.LAVA_SLIME.get()) {
            hurt(level().damageSources().freeze(), 0.8F);
        }
        super.tick();
    }

    @Override
    protected boolean spawnCustomParticles() {
        return true;
    }

    @Override
    public void setSize(int pSize, boolean pResetHealth) {
        int i = Mth.clamp(size, 1, 127);
        entityData.set(ID_SIZE, i);
        reapplyPosition();
        refreshDimensions();
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F + 0.1F * i);

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

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (getType() == ModEntities.TROPIC_SLIME.get() && pSource.is(DamageTypes.DROWN)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isInWater() {
        if (getType() == ModEntities.TROPIC_SLIME.get()) {
            return false;
        }
        return super.isInWater();
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        if (level().isClientSide) return super.mobInteract(pPlayer, pHand);
        ItemStack item = pPlayer.getItemInHand(pHand);
        if (item.is(ModItems.HONEY_BUCKET.get()) || item.is(ModItems.BOTTOMLESS_HONEY_BUCKET.get())) {
            EntityType<? extends Slime> type = getType();
            if (type == ModEntities.BLUE_SLIME.get() || type == ModEntities.GREEN_SLIME.get() || type == ModEntities.PURPLE_SLIME.get()) {
                HoneySlime honeySlime = new HoneySlime(ModEntities.HONEY_SLIME.get(), level(), 0xf8e234);
                honeySlime.setPos(position());
                honeySlime.setDeltaMovement(getDeltaMovement());
                honeySlime.setXRot(getXRot());
                honeySlime.setYRot(getYRot());
                level().addFreshEntity(honeySlime);
                discard();
                if (!pPlayer.getAbilities().instabuild && item.is(ModItems.HONEY_BUCKET.get())) {
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                }
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.HONEY_DRINK, SoundSource.AMBIENT, 1.0F, 1.0F);
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected void dealDamage(@NotNull LivingEntity pLivingEntity) {
        if (isAlive()) {
            int i = getSize();
            if (distanceToSqr(pLivingEntity) < 0.6 * (double) i * 0.6 * (double) i && hasLineOfSight(pLivingEntity) && pLivingEntity.hurt(damageSources().mobAttack(this), getAttackDamage())) {
                playSound(SoundEvents.SLIME_ATTACK, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                doEnchantDamageEffects(this, pLivingEntity);

                if (getType() == ModEntities.ICE_SLIME.get()) {
                    if (ModUtils.isMaster(level()) || (ModUtils.isExpert(level()) && level().random.nextBoolean())) {
                        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0), this);
                    }
                } else if (getType() == ModEntities.LAVA_SLIME.get()) {
                    pLivingEntity.setSecondsOnFire(5);
                }
            }
        }
    }
}
