package org.confluence.mod.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.client.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

// 除了显示数字还能显示“美味...” “致命失误！”
public record DamageIndicatorOptions(Component text) implements ParticleOptions {
    @Override
    @NotNull
    public ParticleType<?> getType(){
        return ModParticles.DAMAGE_INDICATOR.get();
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buffer){
        buffer.writeComponent(text);
    }

    @Override
    @NotNull
    public String writeToString(){
        return String.format(
            Locale.ROOT,
            "%s %s",
            ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), text.getString());
    }

    public static final Codec<DamageIndicatorOptions> CODEC = RecordCodecBuilder.create(
        (thisOptionsInstance) -> thisOptionsInstance.group(
            ExtraCodecs.COMPONENT.fieldOf("text").forGetter((thisOptions) -> thisOptions.text)
        ).apply(thisOptionsInstance, DamageIndicatorOptions::new)
    );

    @SuppressWarnings("deprecation")
    public static final Deserializer<DamageIndicatorOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        @NotNull
        public DamageIndicatorOptions fromCommand(@NotNull ParticleType<DamageIndicatorOptions> particleType, StringReader reader) throws CommandSyntaxException{
            reader.expect(' ');
            String text = reader.readString();
            return new DamageIndicatorOptions(Component.literal(text));
        }

        @Override
        @NotNull
        public DamageIndicatorOptions fromNetwork(@NotNull ParticleType<DamageIndicatorOptions> particleType, FriendlyByteBuf buffer){
            return new DamageIndicatorOptions(buffer.readComponent());
        }
    };

}
