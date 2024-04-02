package org.confluence.mod.command;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.SpecificMoonPacketS2C;
import org.jetbrains.annotations.NotNull;

public class ConfluenceData extends SavedData {
    private int moonSpecific = -1;
    private int gamePhase = 0; // 0:骷髅王前, 1:骷髅王后, 2:肉后, 3:新三王后, 4:花后, 5:石巨人后, 6:月后

    public ConfluenceData() {
    }

    public ConfluenceData(CompoundTag nbt) {
        this.moonSpecific = nbt.getInt("moonSpecific");
        this.gamePhase = nbt.getInt("gamePhase");
    }

    public static ConfluenceData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(ConfluenceData::new, ConfluenceData::new, "confluence");
    }

    public void setMoonSpecific(int moonSpecific) {
        this.moonSpecific = moonSpecific;
        NetworkHandler.CHANNEL.send(
            PacketDistributor.ALL.noArg(),
            new SpecificMoonPacketS2C(moonSpecific)
        );
        setDirty();
    }

    public int getMoonSpecific() {
        return moonSpecific;
    }

    public void setGamePhase(int gamePhase) {
        this.gamePhase = gamePhase;
        setDirty();
    }

    public int getGamePhase() {
        return gamePhase;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag nbt) {
        nbt.putInt("moonSpecific", moonSpecific);
        nbt.putInt("gamePhase", gamePhase);
        return nbt;
    }
}
