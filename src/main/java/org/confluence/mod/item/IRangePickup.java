package org.confluence.mod.item;

import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.capability.ability.PlayerAbility;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.misc.ModTags;

import java.util.function.Function;
import java.util.function.Predicate;

public interface IRangePickup {
    static void apply(Player player, Function<PlayerAbility, Double> range, Predicate<ItemStack> predicate) {
        player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
            double value = range.apply(playerAbility);
            if (value <= 0.0) return;
            player.level().getEntitiesOfClass(
                ItemEntity.class,
                new AABB(player.getOnPos()).inflate(value),
                itemEntity -> predicate.test(itemEntity.getItem())
            ).forEach(itemEntity -> {
                if (itemEntity.isRemoved()) return;
                Vec3 vec3 = player.position()
                    .subtract(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ())
                    .normalize().scale(0.05F).add(0, 0.04F, 0);
                itemEntity.addDeltaMovement(vec3);
                itemEntity.move(MoverType.SELF, itemEntity.getDeltaMovement());
            });
        });
    }

    interface Star {
        static void apply(Player player) {
            IRangePickup.apply(player, PlayerAbility::getStarRange, itemStack -> itemStack.is(ModTags.Items.PROVIDE_MANA));
        }
    }

    interface Heart {
        static void apply(Player player) {
            double range = player.hasEffect(ModEffects.HEART_REACH.get()) ? 12.17 : 1.75;
            player.level().getEntitiesOfClass(
                ItemEntity.class,
                new AABB(player.getOnPos()).inflate(range),
                itemEntity -> itemEntity.getItem().is(ModTags.Items.PROVIDE_LIFE)
            ).forEach(itemEntity -> {
                if (itemEntity.isRemoved()) return;
                Vec3 vec3 = player.position().subtract(itemEntity.position())
                    .normalize().scale(0.05F).add(0, 0.04F, 0);
                itemEntity.addDeltaMovement(vec3);
                itemEntity.move(MoverType.SELF, itemEntity.getDeltaMovement());
            });
        }
    }

    interface Coin {
        static void apply(Player player) {
            player.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
                double value = playerAbility.getCoinRange();
                if (value <= 0.0) return;
                player.level().getEntitiesOfClass(
                    ItemEntity.class,
                    new AABB(player.getOnPos()).inflate(value),
                    itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.getItem().is(ModTags.Items.COIN)
                ).forEach(itemEntity -> {
                    if (itemEntity.isRemoved()) return;
                    Vec3 vec3 = player.position()
                        .subtract(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ())
                        .normalize().scale(0.05F).add(0, 0.04F, 0);
                    itemEntity.addDeltaMovement(vec3);
                    itemEntity.move(MoverType.SELF, itemEntity.getDeltaMovement());
                });
            });
        }
    }

    interface Drops {
        static void apply(Player player) {
            IRangePickup.apply(player, PlayerAbility::getDropsRange, itemStack -> !itemStack.is(ModTags.Items.PROVIDE_MANA) && !itemStack.is(ModTags.Items.PROVIDE_LIFE) && !itemStack.is(ModTags.Items.COIN));
        }
    }
}
