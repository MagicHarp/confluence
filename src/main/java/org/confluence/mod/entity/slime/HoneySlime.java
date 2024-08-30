package org.confluence.mod.entity.slime;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class HoneySlime extends Slime implements DeathAnimOptions {
    private final ItemStack itemStack;
    private final FloatRGB color;

    public HoneySlime(EntityType<? extends Slime> slime, Level level, int color) {
        super(slime, level);
        // setSize在constructor中调用时size还没更新，再变一遍
        setSize(2, true);
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

    @Override
    public void tick() {
        this.setTarget(null);
        resetFallDistance();
        if (onGround() && !((SlimeAccessor) this).isWasOnGround()) {
            int i = getSize();
            for (int j = 0; j < i * 8; ++j) {
                float f = random.nextFloat() * Mth.TWO_PI;
                float f1 = random.nextFloat() * 0.5F + 0.5F;
                float f2 = Mth.sin(f) * (float) i * 0.5F * f1;
                float f3 = Mth.cos(f) * (float) i * 0.5F * f1;
                level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.HONEY_BLOCK.defaultBlockState()), getX() + (double) f2, getY(), getZ() + (double) f3, color.red(), color.green(), color.blue());
            }
        }
        if (getSize() < 3){
            if (level().random.nextDouble() <= 0.00005D) {
                setSize(getSize() + 1, false);
            }
        }
        super.tick();
    }
    @Override
    protected boolean spawnCustomParticles() {
        return true;
    }

    @Override
    public void setSize(int pSize, boolean init) {
        int i;
        if (!init){
            i = Mth.clamp(pSize, 1, 127);
        } else {
            i = 2;
        }
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
        if (living instanceof HoneySlime baseSlime && living.getType() != ModEntities.PINK_SLIME.get()) {
            ModUtils.createItemEntity(baseSlime.itemStack.copy(), living.getX(), living.getY(), living.getZ(), living.level(), 0);
        }
    }

    @Override
    public float[] getBloodColor(){
        return color.mixture(new FloatRGB(0,0,0),0.5f).toArray();
    }
}
