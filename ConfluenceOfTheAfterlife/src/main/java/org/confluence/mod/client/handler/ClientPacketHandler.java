package org.confluence.mod.client.handler;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.confluence.mod.common.data.saved.GamePhase;
import org.confluence.mod.common.init.ModSoundEvents;
import org.confluence.mod.network.s2c.GamePhasePacketS2C;
import org.confluence.mod.network.s2c.ManaPacketS2C;
import org.confluence.mod.network.s2c.WindSpeedPacketS2C;
import org.joml.Vector2f;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;
    private static GamePhase gamePhase = GamePhase.BEFORE_SKELETRON;
    private static final Vector2f windSpeed = new Vector2f();

    public static int getCurrentMana() {
        return currentMana;
    }

    public static int getMaxMana() {
        return maxMana;
    }

    public static GamePhase getGamePhase() {
        return gamePhase;
    }

    public static boolean isHardcore() {
        return gamePhase.ordinal() > 1;
    }

    public boolean isGraduated() {
        return gamePhase.ordinal() == 6;
    }

    public static Vector2f getWindSpeed() {
        return windSpeed;
    }

    public static void handleMana(ManaPacketS2C packet, Player player) {
        maxMana = packet.maxMana();
        currentMana = packet.currentMana();
        if (currentMana == maxMana) {
            player.playSound(ModSoundEvents.COOLDOWN_RECOVERY.get());
        }
    }

    public static void handleGamePhase(GamePhasePacketS2C gamePhasePacketS2C, Player player) {
        gamePhase = gamePhasePacketS2C.gamePhase();
    }

    public static void handleWindSpeed(WindSpeedPacketS2C windSpeedPacketS2C, Player player) {
        windSpeed.set(windSpeedPacketS2C.x(), windSpeedPacketS2C.z());
    }
}
