package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.util.CuriosUtils;

public interface IHurtEvasion {
    static boolean isInvul(LivingEntity living) {
        return living.level().random.nextFloat() < ModConfigs.HURT_EVASION_CHANCE.get() && CuriosUtils.hasCurio(living, IHurtEvasion.class);
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.hurt_evasion");
}
