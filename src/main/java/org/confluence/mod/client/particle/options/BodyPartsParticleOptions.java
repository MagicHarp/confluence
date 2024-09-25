package org.confluence.mod.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.client.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BodyPartsParticleOptions implements ParticleOptions {
    public final double vx;
    public final double vy;
    public final double vz;
    public final int entityId;

    public BodyPartsParticleOptions(double vx, double vy, double vz, int entityId){
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.entityId = entityId;
    }

    @Override
    @NotNull
    public ParticleType<?> getType(){
        return ModParticles.BODY_PART.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer){
        buffer.writeDouble(vx);
        buffer.writeDouble(vy);
        buffer.writeDouble(vz);
        buffer.writeInt(entityId);
    }

    @Override
    @NotNull
    public String writeToString(){
        return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %d",
            ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), vx, vy, vz, entityId);
    }

    public static final Codec<BodyPartsParticleOptions> CODEC = RecordCodecBuilder.create(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            Codec.DOUBLE.fieldOf("vx").forGetter((thisOptions) -> thisOptions.vx),
            Codec.DOUBLE.fieldOf("vy").forGetter((thisOptions) -> thisOptions.vy),
            Codec.DOUBLE.fieldOf("vz").forGetter((thisOptions) -> thisOptions.vz),
            Codec.INT.fieldOf("entity").forGetter(options -> options.entityId)
        ).apply(thisOptionsInstance, BodyPartsParticleOptions::new)
    );

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<BodyPartsParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        @NotNull
        public BodyPartsParticleOptions fromCommand(@NotNull ParticleType<BodyPartsParticleOptions> particleType, StringReader reader) throws CommandSyntaxException{
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            int entityId = reader.readInt();
            return new BodyPartsParticleOptions(r, g, b,entityId);
        }

        @Override
        @NotNull
        public BodyPartsParticleOptions fromNetwork(@NotNull ParticleType<BodyPartsParticleOptions> particleType, FriendlyByteBuf buffer){
            return new BodyPartsParticleOptions(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt());
        }
    };

}
