package org.confluence.mod.entity.slime;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.common.ColoredItem;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.mixin.accessor.SlimeAccessor;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

public class BaseSlime extends Slime {
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
}
