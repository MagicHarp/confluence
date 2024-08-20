package org.confluence.mod.entity.slime;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class BaseSlime extends Slime implements DeathAnimOptions {
    private final int size;
    private final ItemStack itemStack;
    private final FloatRGB color;

    public BaseSlime(EntityType<? extends Slime> slime, Level level, int color, int size) {
        super(slime, level);
        this.size = size;
        ItemStack itemStack = new ItemStack(Materials.GEL.get());
        ColoredItem.setColor(itemStack, color);
        this.itemStack = itemStack;
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

    public static void dropColoredGel(LivingEntity living) {
        if (living instanceof BaseSlime baseSlime && living.getType() != ModEntities.PINK_SLIME.get()) {
            ModUtils.createItemEntity(baseSlime.itemStack.copy(), living.getX(), living.getY(), living.getZ(), living.level(), 0);
        }
    }

    @Override
    public float[] getBloodColor(){
        return color.mixture(new FloatRGB(0,0,0),0.5f).toArray();
    }
}
