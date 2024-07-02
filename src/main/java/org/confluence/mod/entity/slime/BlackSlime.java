package org.confluence.mod.entity.slime;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlackSlime extends Slime {
    public BlackSlime(EntityType<BlackSlime> slime, Level level) {
        super(slime, level);
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

    public void finalizeSpawn(RandomSource randomSource, DifficultyInstance difficulty) {
        int size = 2;
        if (randomSource.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) size = 4;
        setSize(size, true);
        AttributeInstance attackDamage = getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);
        assert attackDamage != null && maxHealth != null;
        if (size == 2) {
            attackDamage.setBaseValue(4.0F);
            maxHealth.setBaseValue(10.0F);
        } else {
            attackDamage.setBaseValue(12.0F);
            Objects.requireNonNull(getAttribute(Attributes.ARMOR)).setBaseValue(2);
            maxHealth.setBaseValue(58.0F);
        }
    }
}
