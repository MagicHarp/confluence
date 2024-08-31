package org.confluence.mod.entity.slime;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class NonDropSlime extends Slime implements DeathAnimOptions {
    private final FloatRGB color;
    private final int size;
    private final String name;

    public NonDropSlime(EntityType<NonDropSlime> slime, Level level, int color, int size, String name) {
        super(slime, level);
        this.color = FloatRGB.fromInteger(color);
        this.size = size;
        this.name = name;
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
        if (this.getType().equals(ModEntities.LAVA_SLIME.get())) {
            if (isInWater()){
                this.hurt(this.level().damageSources().freeze(), 0.8F);
            }
            this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 500, 4, false, true));
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
        int i = Mth.clamp(size, 1, 127);
        entityData.set(ID_SIZE, i);
        reapplyPosition();
        refreshDimensions();
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F + 0.1F * i);

        this.xpReward = i;
    }

    @Override
    public float[] getBloodColor() {
        return color.toArray();
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        var stateDefinition = Blocks.LAVA.getStateDefinition();
        Property<?> levelProperty = stateDefinition.getProperty("level");
        if (levelProperty instanceof IntegerProperty integerProperty) {
            if (ModUtils.isExpert(level())) {
                if (this.level().getBlockState(BlockPos.containing(this.position())).isAir() ||
                        this.level().getBlockState(BlockPos.containing(this.position())).canBeReplaced(Fluids.LAVA)) {
                    //todo 未知且非固定出现的渲染bug
                    this.level().setBlock(BlockPos.containing(this.position()), Blocks.LAVA.defaultBlockState().setValue(integerProperty, 14), 2);
                }
            }
        }
    }
}
