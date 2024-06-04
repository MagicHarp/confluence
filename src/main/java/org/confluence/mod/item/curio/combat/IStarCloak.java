package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.entity.projectile.StarCloakEntity;
import org.confluence.mod.util.CuriosUtils;

import java.util.List;
import java.util.Optional;

public interface IStarCloak {
    static void apply(LivingEntity living, RandomSource randomSource) {
        Optional<IStarCloak> curio = CuriosUtils.findCurio(living, IStarCloak.class);
        if (curio.isEmpty()) return;
        Level level = living.level();
        List<Entity> list = level.getEntities(living, new AABB(living.getOnPos()).inflate(4.0, 3.0, 4.0), entity -> entity instanceof Enemy);
        for (int i = 0; i < 3; i++) {
            Entity target;
            if (list.isEmpty()) {
                target = living;
            } else {
                target = list.get(randomSource.nextInt(list.size()));
            }
            StarCloakEntity entity = new StarCloakEntity(level, living, target);
            level.addFreshEntity(entity);
        }
    }

    Component TOOLTIP = Component.translatable("item.confluence.star_cloak.tooltip");
}
