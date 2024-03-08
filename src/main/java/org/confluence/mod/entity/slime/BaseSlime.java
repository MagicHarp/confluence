package org.confluence.mod.entity.slime;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BaseSlime extends Slime {
    private final ParticleOptions particleOptions;
    private final int size;

    public BaseSlime(EntityType<? extends Slime> slime, Level level, ParticleOptions particleOptions, int size) {
        super(slime, level);
        this.particleOptions = particleOptions;
        this.size = size;
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return particleOptions;
    }

    @Override
    public void setSize(int s, boolean setAttr) {
        super.setSize(size, setAttr);
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        brain.clearMemories();
        setRemoved(removalReason);
        invalidateCaps();
    }
}
