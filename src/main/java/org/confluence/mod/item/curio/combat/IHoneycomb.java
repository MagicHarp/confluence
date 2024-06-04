package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.projectile.BeeProjectile;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;

public interface IHoneycomb {
    Component TOOLTIP = Component.translatable("item.confluence.honey_comb.tooltip");

    static void apply(LivingEntity living, RandomSource random) {
        if (CuriosUtils.hasCurio(living, IHoneycomb.class)) {
            boolean hasHivePack = CuriosUtils.hasCurio(living, CurioItems.HIVE_PACK.get());
            double eyeY = living.getEyeY();
            int summon = random.nextInt(1, hasHivePack ? 5 : 4);
            for (int i = 0; i < summon; i++) {
                BeeProjectile projectile = new BeeProjectile(living.level(), living, hasHivePack && random.nextBoolean());
                projectile.setPos(living.position().add(random.nextInt(1, 3), eyeY, random.nextInt(1, 3)));
                living.level().addFreshEntity(projectile);
            }
            living.addEffect(new MobEffectInstance(ModEffects.HONEY.get(), 100));
        }
    }
}
