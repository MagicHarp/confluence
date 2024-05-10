package org.confluence.mod.item.curio.combat;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.projectile.BeeProjectile;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;

public class HoneyComb extends BaseCurioItem {
    public HoneyComb() {
        super(ModRarity.GREEN);
    }

    public static void apply(LivingEntity living, RandomSource random) {
        if (CuriosUtils.hasCurio(living, CurioItems.HONEY_COMB.get())) {
            double eyeY = living.getEyeY();
            int summon = random.nextInt(1, /* 蜂巢背包 ? 5 : */4);
            for (int i = 0; i < summon; i++) {
                BeeProjectile projectile = new BeeProjectile(living.level(), living);
                projectile.setPos(living.position().add(random.nextInt(1, 3), eyeY, random.nextInt(1, 3)));
                living.level().addFreshEntity(projectile);
            }
            living.addEffect(new MobEffectInstance(ModEffects.HONEY.get(), 100));
        }
    }
}
