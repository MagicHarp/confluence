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

public class BloodParticleOptions implements ParticleOptions {
    public final float r;
    public final float g;
    public final float b;
    public final double vx;
    public final double vy;
    public final double vz;

    public BloodParticleOptions(float r, float g, float b, double vx, double vy, double vz){
        this.r = r;
        this.g = g;
        this.b = b;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    @Override
    @NotNull
    public ParticleType<?> getType(){
        return ModParticles.BLOOD.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer){
        buffer.writeFloat(r);
        buffer.writeFloat(g);
        buffer.writeFloat(b);
        buffer.writeDouble(vx);
        buffer.writeDouble(vy);
        buffer.writeDouble(vz);
    }

    @Override
    @NotNull
    public String writeToString(){
        return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %.2f %.2f %.2f",
            ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), r, g, b, vx, vy, vz);
    }

    public static final Codec<BloodParticleOptions> CODEC = RecordCodecBuilder.create(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            Codec.FLOAT.fieldOf("r").forGetter((thisOptions) -> thisOptions.r),
            Codec.FLOAT.fieldOf("g").forGetter((thisOptions) -> thisOptions.g),
            Codec.FLOAT.fieldOf("b").forGetter((thisOptions) -> thisOptions.b),
            Codec.DOUBLE.fieldOf("vx").forGetter((thisOptions) -> thisOptions.vx),
            Codec.DOUBLE.fieldOf("vy").forGetter((thisOptions) -> thisOptions.vy),
            Codec.DOUBLE.fieldOf("vz").forGetter((thisOptions) -> thisOptions.vz)
        ).apply(thisOptionsInstance, BloodParticleOptions::new)
    );

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<BloodParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        @NotNull
        public BloodParticleOptions fromCommand(@NotNull ParticleType<BloodParticleOptions> particleType, StringReader reader) throws CommandSyntaxException{
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            double vx = reader.readDouble();
            reader.expect(' ');
            double vy = reader.readDouble();
            reader.expect(' ');
            double vz = reader.readDouble();
            return new BloodParticleOptions(r, g, b, vx, vy, vz);
        }

        @Override
        @NotNull
        public BloodParticleOptions fromNetwork(@NotNull ParticleType<BloodParticleOptions> particleType, FriendlyByteBuf buffer){
            return new BloodParticleOptions(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        }
    };

}
