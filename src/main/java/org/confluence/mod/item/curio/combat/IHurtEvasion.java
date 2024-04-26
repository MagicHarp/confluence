package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.util.CuriosUtils;

public interface IHurtEvasion {
    static boolean isInvul(LivingEntity living) {
        return living.level().random.nextFloat() < 0.1F && CuriosUtils.hasCurio(living, IHurtEvasion.class);
    }
}
