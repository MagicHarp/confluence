package org.confluence.mod.entity.slime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
}
