package org.confluence.mod.entity.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.item.curio.informational.ILifeFormAnalyzer;

public class PinkSlime extends BaseSlime implements ILifeFormAnalyzer.Detectable {
    public PinkSlime(EntityType<? extends Slime> slime, Level level) {
        super(slime, level, ModParticles.ITEM_PINK_GEL, 1);
    }

    @Override
    public int getRarity() {
        return 2;
    }
}
