package org.confluence.mod.item.curio.expert;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;

public class RoyalGel extends BaseCurioItem implements ModRarity.Expert {
    public RoyalGel() {
        super(ModRarity.EXPERT);
    }

    public static boolean apply(LivingEntity attacker, LivingEntity target) {
        return attacker instanceof Slime && CuriosUtils.hasCurio(target, CurioItems.ROYAL_GEL.get());
    }

    public static boolean isInvul(LivingEntity living, DamageSource damageSource) {
        return damageSource.getEntity() instanceof Slime && CuriosUtils.hasCurio(living, CurioItems.ROYAL_GEL.get());
    }
}
