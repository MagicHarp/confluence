package org.confluence.mod.command;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.GamePhasePacketS2C;
import org.confluence.mod.network.s2c.SpecificMoonPacketS2C;
import org.confluence.mod.network.s2c.WindSpeedPacketS2C;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;

public class ConfluenceData extends SavedData {
    private SpecificMoon specificMoon;
    private GamePhase gamePhase;
    private float windSpeedX;
    private float windSpeedZ;
    private int revealStep;

    ConfluenceData() {
        this.specificMoon = SpecificMoon.VANILLA;
        this.gamePhase = GamePhase.BEFORE_SKELETRON;
        this.windSpeedX = 0.0F;
        this.windSpeedZ = 0.0F;
        this.revealStep = -1;
    }

    ConfluenceData(CompoundTag nbt) {
        this.specificMoon = SpecificMoon.byId(nbt.getInt("moonSpecific"));
        this.gamePhase = GamePhase.valueOf(nbt.getString("gamePhase"));
        this.windSpeedX = nbt.getFloat("windSpeedX");
        this.windSpeedZ = nbt.getFloat("windSpeedZ");
        this.revealStep = nbt.getInt("revealStep");
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag nbt) {
        nbt.putInt("moonSpecific", specificMoon.getId());
        nbt.putString("gamePhase", gamePhase.name());
        nbt.putFloat("windSpeedX", windSpeedX);
        nbt.putFloat("windSpeedZ", windSpeedZ);
        nbt.putInt("revealStep", revealStep);
        return nbt;
    }

    public static ConfluenceData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(ConfluenceData::new, ConfluenceData::new, "confluence");
    }

    public boolean isHardcore() {
        return gamePhase.ordinal() > 1;
    }

    public boolean isGraduated() {
        return gamePhase.ordinal() == 6;
    }

    public void setSpecificMoon(SpecificMoon specificMoon) {
        this.specificMoon = specificMoon;
        NetworkHandler.CHANNEL.send(
            PacketDistributor.ALL.noArg(),
            new SpecificMoonPacketS2C(specificMoon)
        );
        setDirty();
    }

    public SpecificMoon getSpecificMoon() {
        return specificMoon;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
        NetworkHandler.CHANNEL.send(
            PacketDistributor.ALL.noArg(),
            new GamePhasePacketS2C(gamePhase)
        );
        setDirty();
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setWindSpeed(float x, float z) {
        this.windSpeedX = x;
        this.windSpeedZ = z;
        NetworkHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), new WindSpeedPacketS2C((float) Mth.length(x, z)));
        setDirty();
    }

    public float getWindSpeedX() {
        return windSpeedX;
    }

    public float getWindSpeedZ() {
        return windSpeedZ;
    }

    public boolean increaseRevealStep(ServerLevel serverLevel) {
        if (revealStep < 8) {
            this.revealStep++;
            setDirty();
            serverLevel.players().forEach(PlayerUtils::syncAdvancements);
            return true;
        }
        return false;
    }

    public int getRevealStep() {
        return revealStep;
    }
}
