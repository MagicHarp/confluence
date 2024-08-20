package org.confluence.mod.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Locale;

public class LightsBaneParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private final Vector3f direction;

    protected LightsBaneParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet sprites, Vector3f dir){
        super(pLevel, pX, pY, pZ);
        this.spriteSet = sprites;
        this.direction = dir;
    }

    @Override
    public void tick(){
        setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<LightsBaneParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(LightsBaneParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
            return new LightsBaneParticle(level, x, y, z, spriteSet, type.direction);
        }
    }

    public static class LightsBaneParticleOptions implements ParticleOptions {
        private final Vector3f direction;

        public LightsBaneParticleOptions(Vector3f direction){
            this.direction = direction;
        }

        @Override
        @NotNull
        public ParticleType<?> getType(){
            return ModParticles.LIGHTS_BANE.get();
        }

        @Override
        public void writeToNetwork(FriendlyByteBuf buffer){
            buffer.writeVector3f(direction);
        }

        @Override
        @NotNull
        public String writeToString(){
            return String.format(
                Locale.ROOT,
                "%s %.2f %.2f %.2f",
                ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), direction.x, direction.y, direction.z);
        }
    }

    public static final Codec<LightsBaneParticleOptions> CODEC = RecordCodecBuilder.create(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            ExtraCodecs.VECTOR3F.fieldOf("x").forGetter((thisOptions) -> thisOptions.direction)
        ).apply(thisOptionsInstance, LightsBaneParticleOptions::new)
    );

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<LightsBaneParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        @NotNull
        public LightsBaneParticleOptions fromCommand(@NotNull ParticleType<LightsBaneParticleOptions> particleType, StringReader reader) throws CommandSyntaxException{
            reader.expect(' ');
            float x = reader.readFloat();
            reader.expect(' ');
            float y = reader.readFloat();
            reader.expect(' ');
            float z = reader.readFloat();
            return new LightsBaneParticleOptions(new Vector3f(x, y, z));
        }

        @Override
        @NotNull
        public LightsBaneParticleOptions fromNetwork(@NotNull ParticleType<LightsBaneParticleOptions> particleType, FriendlyByteBuf buffer){
            return new LightsBaneParticleOptions(new Vector3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));
        }
    };

}
