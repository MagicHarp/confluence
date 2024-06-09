package org.confluence.mod.effect.beneficial;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.PlayerLightPacketS2C;
import org.confluence.mod.util.CuriosUtils;

public class ShineEffect extends MobEffect {
    public ShineEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFF66);
    }

    public static void onAdd(MobEffect mobEffect, LivingEntity living) {
        if (mobEffect == ModEffects.SHINE.get() && living instanceof ServerPlayer serverPlayer) {
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerLightPacketS2C(serverPlayer.getUUID(), true)
            );
        }
    }

    public static void onRemove(MobEffect mobEffect, LivingEntity living) {
        if (mobEffect == ModEffects.SHINE.get() && living instanceof ServerPlayer serverPlayer) {
            boolean curio = CuriosUtils.hasCurio(living, CurioItems.MAGILUMINESCENCE.get());
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerLightPacketS2C(serverPlayer.getUUID(), curio)
            );
        }
    }
}
