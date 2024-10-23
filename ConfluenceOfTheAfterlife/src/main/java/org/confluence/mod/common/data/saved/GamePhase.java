package org.confluence.mod.common.data.saved;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.neoforged.fml.ModLoader;
import org.confluence.mod.api.event.GamePhaseInitializeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.IntFunction;

public class GamePhase implements StringRepresentable {
    // 0:骷髅王前, 1:骷髅王后, 2:肉后, 3:新三王后, 4:花后, 5:石巨人后, 6:月后
    public static final GamePhase BEFORE_SKELETRON = new GamePhase(0, "before_skeletron");
    public static final GamePhase AFTER_SKELETRON = new GamePhase(1, "after_skeletron");
    public static final GamePhase WALL_OF_FLESH = new GamePhase(2, "wall_of_flesh");
    public static final GamePhase KING_NEO_THREE = new GamePhase(3, "king_of_three");
    public static final GamePhase PLANTERA = new GamePhase(4, "plantera");
    public static final GamePhase GOLEM = new GamePhase(5, "golem");
    public static final GamePhase MOON_LORD = new GamePhase(6, "moon_lord");

    public static final Codec<GamePhase> CODEC = StringRepresentable.fromValues(GamePhase::values);
    public static final IntFunction<GamePhase> BY_ID = ByIdMap.continuous(GamePhase::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, GamePhase> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, GamePhase::ordinal);
    private static GamePhase[] values;
    private final int ordinal;
    private final String name;

    public GamePhase(int ordinal, String name) {
        this.ordinal = ordinal;
        this.name = name;
    }

    public int ordinal() {
        return ordinal;
    }

    public String name() {
        return name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name.toLowerCase(Locale.ROOT);
    }

    public static GamePhase getById(int id) {
        return BY_ID.apply(id);
    }

    public static GamePhase[] values() {
        if (values == null) {
            GamePhaseInitializeEvent event = new GamePhaseInitializeEvent(new GamePhase[]{
                    BEFORE_SKELETRON,
                    AFTER_SKELETRON,
                    WALL_OF_FLESH,
                    KING_NEO_THREE,
                    PLANTERA,
                    GOLEM,
                    MOON_LORD
            });
            ModLoader.postEvent(event);
            values = event.getNeoValues();
        }
        return values;
    }
}
