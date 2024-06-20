package org.confluence.mod.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.mana.ManaStorage;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.IScope;
import org.confluence.mod.item.curio.movement.IMayFly;
import org.confluence.mod.item.curio.movement.IMultiJump;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.GamePhasePacketS2C;
import org.confluence.mod.network.s2c.ManaPacketS2C;
import org.confluence.mod.network.s2c.SpecificMoonPacketS2C;
import org.confluence.mod.network.s2c.WindSpeedPacketS2C;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;

public final class PlayerUtils {
    public static void syncMana2Client(ServerPlayer serverPlayer, ManaStorage manaStorage) {
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new ManaPacketS2C(manaStorage.getMaxMana(), manaStorage.getCurrentMana())
        );
    }

    public static void syncMana2Client(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(ManaProvider.CAPABILITY)
            .ifPresent(manaStorage -> syncMana2Client(serverPlayer, manaStorage));
    }

    public static void regenerateMana(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
            int delay = manaStorage.getRegenerateDelay();
            boolean notMove = Math.abs(serverPlayer.xCloak - serverPlayer.xCloakO) < 1.0E-7;
            if (delay > 0) {
                if (manaStorage.isArcaneCrystalUsed()) delay *= (notMove ? 0.975F : 0.95F);
                if (delay > 20 && serverPlayer.hasEffect(ModEffects.MANA_REGENERATION.get())) delay = 20;
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
        });
    }

    public static boolean extractMana(ServerPlayer serverPlayer, IntSupplier sup) {
        if (serverPlayer.gameMode.isCreative()) return true;

        AtomicBoolean success = new AtomicBoolean();
        serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
            if (manaStorage.extractMana(sup, serverPlayer)) {
                success.set(true);
                manaStorage.setRegenerateDelay((int) Math.ceil(0.7F * ((1 - (float) manaStorage.getCurrentMana() / manaStorage.getMaxMana()) * 240 + 45)));
                syncMana2Client(serverPlayer, manaStorage);
            }
        });
        return success.get();
    }

    public static void receiveMana(ServerPlayer serverPlayer, IntSupplier sup) {
        serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
            if (manaStorage.receiveMana(sup)) syncMana2Client(serverPlayer, manaStorage);
        });
    }

    public static void syncSavedData(ServerPlayer serverPlayer) {
        ConfluenceData data = ConfluenceData.get(serverPlayer.serverLevel());
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new SpecificMoonPacketS2C(data.getMoonSpecific()));
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new WindSpeedPacketS2C((float) Mth.length(data.getWindSpeedX(), data.getWindSpeedZ())));
        NetworkHandler.CHANNEL.send(
            PacketDistributor.ALL.noArg(),
            new GamePhasePacketS2C(data.getGamePhase())
        );
    }

    public static void syncAdvancements(ServerPlayer serverPlayer) {
        int step = ConfluenceData.get(serverPlayer.serverLevel()).getRevealStep();
        for (int i = 0; i < step + 1; i++) {
            ResourceLocation id = new ResourceLocation(Confluence.MODID, "reveal/step" + i);
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(id);
            if (advancement != null) serverPlayer.getAdvancements().award(advancement, "never");
        }
    }

    public static boolean isServerNotFake(Player player) {
        return player instanceof ServerPlayer && !(player instanceof FakePlayer);
    }

    public static void resetClientPacket(ServerPlayer serverPlayer) {
        IMultiJump.sendMsg(serverPlayer);
        IMayFly.sendMsg(serverPlayer);
        IAutoAttack.sendMsg(serverPlayer);
        IScope.sendMsg(serverPlayer);
    }
}
