package org.confluence.mod.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class RarityComponent {
    public static final Codec<RarityComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(RarityComponent::name),
            Codec.INT.fieldOf("color").forGetter(RarityComponent::color)
    ).apply(instance, RarityComponent::new));
    public static final StreamCodec<ByteBuf, RarityComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, RarityComponent::name,
            ByteBufCodecs.INT, RarityComponent::color,
            RarityComponent::new
    );
    private final String name;
    private final int color;

    public RarityComponent(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String name() {
        return name;
    }

    public int color() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RarityComponent) obj;
        return Objects.equals(this.name, that.name) &&
                this.color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }

    @Override
    public String toString() {
        return "RarityComponent[" +
                "name=" + name + ", " +
                "color=" + color + ']';
    }
}
