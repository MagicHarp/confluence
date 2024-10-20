package org.confluence.mod.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import org.confluence.mod.common.attachment.ManaStorage;
import org.confluence.mod.common.init.ModAttachments;
import org.confluence.mod.network.s2c.ManaPacketS2C;

import java.util.function.IntSupplier;

public final class PlayerUtils {
    public static void syncMana2Client(ServerPlayer serverPlayer, ManaStorage manaStorage) {
        PacketDistributor.sendToPlayer(serverPlayer, new ManaPacketS2C(manaStorage.getMaxMana(), manaStorage.getCurrentMana()));
    }

    public static void syncMana2Client(ServerPlayer serverPlayer) {
        syncMana2Client(serverPlayer, serverPlayer.getData(ModAttachments.MANA_STORAGE.get()));
    }

    // todo
    public static void regenerateMana(ServerPlayer serverPlayer) {
        ManaStorage manaStorage = serverPlayer.getData(ModAttachments.MANA_STORAGE.get());

        int delay = manaStorage.getRegenerateDelay();
        boolean notMove = Math.abs(serverPlayer.xCloak - serverPlayer.xCloakO) < 1.0E-7;
        if (delay > 0) {
            if (manaStorage.isArcaneCrystalUsed()) delay = (int) ((float) delay * (notMove ? 0.975F : 0.95F));
//            if (delay > 20 && serverPlayer.hasEffect(ModEffects.MANA_REGENERATION.get())) delay = 20;
            int delayReduce = notMove ? 2 : 1;
            if (manaStorage.hasManaRegenerationBand()) delayReduce += 1;
            manaStorage.setRegenerateDelay(delay - delayReduce);
            return;
        }

        IntSupplier receive = () -> {
            float a = manaStorage.getMaxMana() / 7.0F + (manaStorage.hasManaRegenerationBand() ? 25 : 0) + 1;
            float b = manaStorage.getCurrentMana() * 0.8F / manaStorage.getMaxMana() + 0.2F;
            if (notMove) a += manaStorage.getMaxMana() / 2.0F;
            return Math.max(Math.round(a * b * 0.0115F), 1);
        };

        if (manaStorage.receiveMana(receive)) syncMana2Client(serverPlayer, manaStorage);
    }

    public static boolean extractMana(ServerPlayer serverPlayer, IntSupplier sup) {
        if (serverPlayer.gameMode.isCreative()) return true;
        boolean success = false;
        ManaStorage manaStorage = serverPlayer.getData(ModAttachments.MANA_STORAGE.get());
        if (manaStorage.extractMana(sup, serverPlayer)) {
            success = true;
            manaStorage.setRegenerateDelay((int) Math.ceil(0.7F * ((1 - (float) manaStorage.getCurrentMana() / manaStorage.getMaxMana()) * 240 + 45)));
            syncMana2Client(serverPlayer, manaStorage);
        }
        return success;
    }

    public static void receiveMana(ServerPlayer serverPlayer, IntSupplier sup) {
        ManaStorage manaStorage = serverPlayer.getData(ModAttachments.MANA_STORAGE.get());
        if (manaStorage.receiveMana(sup)) syncMana2Client(serverPlayer, manaStorage);
    }

    public static boolean isServerNotFake(Player player) {
        return player instanceof ServerPlayer && !(player instanceof FakePlayer);
    }
}
