package org.confluence.mod.common.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class HatSporeZombie extends SporeZombie {
    public HatSporeZombie(EntityType<? extends HatSporeZombie> type, Level level) {
        super(type, level);
    }

}
