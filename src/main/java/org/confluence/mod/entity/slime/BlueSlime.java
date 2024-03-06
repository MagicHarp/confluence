package org.confluence.mod.entity.slime;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.jetbrains.annotations.NotNull;

public class BlueSlime extends Slime {
    public BlueSlime(EntityType<BlueSlime> slime, Level level) {
        super(slime, level);
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return ConfluenceParticles.ITEM_BLUE_SLIME.get();
    }
}
