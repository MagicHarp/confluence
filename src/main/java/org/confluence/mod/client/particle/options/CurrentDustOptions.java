package org.confluence.mod.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.confluence.mod.client.particle.ModParticles;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Locale;

@SuppressWarnings("deprecation")
public class CurrentDustOptions extends DustParticleOptionsBase {
    public static final Codec<CurrentDustOptions> CODEC = RecordCodecBuilder.create(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            ExtraCodecs.VECTOR3F.fieldOf("fromColor").forGetter((thisOptions) -> thisOptions.color),
            ExtraCodecs.VECTOR3F.fieldOf("toColor").forGetter((thisOptions) -> thisOptions.toColor),
            Codec.FLOAT.fieldOf("scale").forGetter((thisOptions) -> thisOptions.scale)
        ).apply(thisOptionsInstance, CurrentDustOptions::new)
    );
    public static final ParticleOptions.Deserializer<CurrentDustOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public @NotNull CurrentDustOptions fromCommand(@NotNull ParticleType<CurrentDustOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = DustParticleOptionsBase.readVector3f(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            Vector3f vector3f1 = DustParticleOptionsBase.readVector3f(reader);
            return new CurrentDustOptions(vector3f, vector3f1, f);
        }

        public @NotNull CurrentDustOptions fromNetwork(@NotNull ParticleType<CurrentDustOptions> type, @NotNull FriendlyByteBuf buf) {
            Vector3f vector3f = DustParticleOptionsBase.readVector3f(buf);
            float f = buf.readFloat();
            Vector3f vector3f1 = DustParticleOptionsBase.readVector3f(buf);
            return new CurrentDustOptions(vector3f, vector3f1, f);
        }
    };
    private final Vector3f toColor;
    public CurrentDustOptions(Vector3f color1, Vector3f color2, float scale) {
        super(color1, scale);
        this.toColor = color2;
    }
    public Vector3f getFromColor() {
        return this.color;
    }
    public Vector3f getToColor() {
        return this.toColor;
    }
    public void writeToNetwork(@NotNull FriendlyByteBuf pBuffer) {
        super.writeToNetwork(pBuffer);
        pBuffer.writeFloat(this.toColor.x());
        pBuffer.writeFloat(this.toColor.y());
        pBuffer.writeFloat(this.toColor.z());
    }

    public @NotNull String writeToString() {
        return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f",
            BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()),
            this.color.x(),
            this.color.y(),
            this.color.z(),
            this.scale,
            this.toColor.x(),
            this.toColor.y(),
            this.toColor.z()
        );
    }
    public @NotNull ParticleType<CurrentDustOptions> getType() {
        return ModParticles.CURRENT_DUST.get();
    }
}
