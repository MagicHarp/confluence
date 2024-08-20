package org.confluence.mod.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BloodParticle extends TextureSheetParticle {

    protected BloodParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float r, float g, float b){
        super(level, x, y, z);
        this.lifetime = level.getRandom().nextInt(20, 50);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        scale(0.8f);
    }

    @Override
    public void tick(){
        super.tick();
        float gravity = 0.08f;
        this.yd -= gravity;
    }

    @Override
    public void render(@NotNull VertexConsumer buffer, @NotNull Camera camera, float partialTicks){
        super.render(buffer, camera, partialTicks);
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<BloodParticle.BloodParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BloodParticle.BloodParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
            RandomSource random = level.getRandom();
            double vx = type.vx + ModUtils.nextDouble(random, -0.2, 0.2);
            double vy = type.vy + ModUtils.nextDouble(random, -0.3, 0.1);
            double vz = type.vz + ModUtils.nextDouble(random, -0.2, 0.2);
            BloodParticle particle = new BloodParticle(level, x, y, z, vx, vy, vz, type.r, type.g, type.b);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }

    public static class BloodParticleOptions implements ParticleOptions {
        private final float r;
        private final float g;
        private final float b;
        private final double vx;
        private final double vy;
        private final double vz;

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
