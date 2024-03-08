package org.confluence.mod.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.mana.ManaProvider;
import org.confluence.mod.mana.ManaStorage;
import org.confluence.mod.network.ManaPacketS2C;
import org.confluence.mod.network.NetworkHandler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class PlayerUtils {
    public static void syncMana2Client(ServerPlayer serverPlayer, ManaStorage manaStorage) {
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new ManaPacketS2C(manaStorage.getMaxMana(), manaStorage.getCurrentMana())
        );
    }

    public static void syncMana2Client(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaStorage -> {
            syncMana2Client(serverPlayer, manaStorage);
        });
    }

    public static void regenerateMana(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaStorage -> {
            Supplier<Integer> receive = () -> {
                float a = ((float) manaStorage.getMaxMana() / 7) + manaStorage.getRegenerateBonus() + 1;
                float b = ((float) manaStorage.getCurrentMana()) / manaStorage.getMaxMana() * 0.8F + 0.2F;
                if (serverPlayer.getDeltaMovement().length() < 0.3) a += (float) manaStorage.getMaxMana() / 2;
                return Math.round(a * b * 1.15F);
            };

            if (manaStorage.receiveMana(receive) != -1) {
                syncMana2Client(serverPlayer, manaStorage);
            }
        });
    }

    public static boolean extractMana(ServerPlayer serverPlayer, Supplier<Integer> sup) {
        AtomicBoolean success = new AtomicBoolean(false);
        serverPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaStorage -> success.set(manaStorage.extractMana(sup) != -1));
        return success.get();
    }

    public static boolean receiveMana(ServerPlayer serverPlayer, Supplier<Integer> sup) {
        AtomicBoolean success = new AtomicBoolean(false);
        serverPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaStorage -> {
            if (manaStorage.receiveMana(sup) != -1) {
                success.set(true);
                syncMana2Client(serverPlayer, manaStorage);
            }
        });
        return success.get();
    }

    public static void syncAdvancements(ServerPlayer serverPlayer) {

    }
}
