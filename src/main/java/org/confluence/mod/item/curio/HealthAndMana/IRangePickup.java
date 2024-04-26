package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.misc.ModTags;

import java.util.function.Function;

public interface IRangePickup {
    static void apply(Player player, Function<PlayerAbility, Double> range, TagKey<Item> tag) {
        player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility ->
            player.level().getEntitiesOfClass(
                ItemEntity.class,
                new AABB(player.getOnPos()).inflate(range.apply(playerAbility)),
                itemEntity -> itemEntity.getItem().is(tag)
            ).forEach(itemEntity -> {
                if (itemEntity.isRemoved()) return;
                Vec3 vec3 = player.position()
                    .subtract(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ())
                    .normalize().scale(0.05F).add(0, 0.04F, 0);
                itemEntity.addDeltaMovement(vec3);
                itemEntity.move(MoverType.SELF, itemEntity.getDeltaMovement());
            })
        );
    }

    interface Star {
        static void apply(Player player) {
            IRangePickup.apply(player, PlayerAbility::getStarRange, ModTags.PROVIDE_MANA);
        }
    }

    interface Heart {
        static void apply(Player player) {
        }
    }

    interface Coin {
        static void apply(Player player) {
            IRangePickup.apply(player, PlayerAbility::getCoinRange, ModTags.COIN);
        }
    }
}
