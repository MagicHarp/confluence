package org.confluence.mod.entity.slime;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlackSlime extends Slime {
    private final ItemStack itemStack;
    private final FloatRGB color;

    public BlackSlime(EntityType<BlackSlime> slime, Level level) {
        super(slime, level);
        ItemStack itemStack = new ItemStack(Materials.GEL.get());
        ColoredItem.setColor(itemStack, 0x373535);
        this.itemStack = itemStack;
        this.color = FloatRGB.fromInteger(0x373535);
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
    public void remove(@NotNull RemovalReason removalReason) {
        if (getSize() == 2) {
            brain.clearMemories();
            setRemoved(removalReason);
            invalidateCaps();
        } else {
            super.remove(removalReason);
        }
    }

    @Override
    public void setSize(int pSize, boolean setByVanilla) {
        if (setByVanilla) return; // 原版为true
        int i = Mth.clamp(pSize, 1, 127);
        entityData.set(ID_SIZE, i);
        reapplyPosition();
        refreshDimensions();
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F + 0.1F * i);

        this.xpReward = i;
    }

    public void finalizeSpawn(RandomSource randomSource, DifficultyInstance difficulty) {
        int size = 2;
        float specialMultiplier = difficulty.getSpecialMultiplier();

        float probability;
        if (specialMultiplier <= 0.5F) {
            probability = 0.15F;
        } else if (specialMultiplier <= 1.0F) {
            probability = 0.60F;
        } else {
            probability = 0.85F;
        }

        if (randomSource.nextFloat() < probability) {
            size = 4;
        }

        setSize(size, false);
        AttributeInstance attackDamage = getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);
        assert attackDamage != null && maxHealth != null;
        if (size == 2) {
            attackDamage.setBaseValue(6.0F);
            maxHealth.setBaseValue(25.0F);
        } else {
            attackDamage.setBaseValue(12.0F);
            Objects.requireNonNull(getAttribute(Attributes.ARMOR)).setBaseValue(2);
            maxHealth.setBaseValue(58.0F);
        }
        setHealth(getMaxHealth());
    }
    public static void dropColoredGel(LivingEntity living) {
        if (living instanceof BlackSlime blackSlime) {
            ModUtils.createItemEntity(blackSlime.itemStack.copy(), living.getX(), living.getY(), living.getZ(), living.level(), 0);
        }
    }
}
