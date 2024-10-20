package org.confluence.mod.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.confluence.mod.client.animate.ColorAnimation;
import org.confluence.mod.client.animate.ExpertColorAnimation;
import org.confluence.mod.client.animate.MasterColorAnimation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class ModRarity implements DataComponentType<ModRarity> {
    public static final ModRarity COMMON = new ModRarity("common",16777215);
    public static final ModRarity UNCOMMON = new ModRarity("uncommon", 16777045);
    public static final ModRarity RARE = new ModRarity("rare", 5636095);
    public static final ModRarity EPIC = new ModRarity("epic", 16733695);

    public static final ModRarity GRAY = new ModRarity("gray", 0x828282);
    public static final ModRarity WHITE = new ModRarity("white", 0xFFFFFF);
    public static final ModRarity BLUE = new ModRarity("blue", 0x9696FF);
    public static final ModRarity GREEN = new ModRarity("green", 0x96FF96);
    public static final ModRarity ORANGE = new ModRarity("orange", 0xFFC896);
    public static final ModRarity LIGHT_RED = new ModRarity("light_red", 0xFF9696);
    public static final ModRarity PINK = new ModRarity("pink", 0xFF96FF);
    public static final ModRarity LIGHT_PURPLE = new ModRarity("light_purple", 0xD2A0FF);
    public static final ModRarity LIME = new ModRarity("lime", 0x96FF0A);
    public static final ModRarity YELLOW = new ModRarity("yellow", 0xFFFF0A);
    public static final ModRarity CYAN = new ModRarity("cyan", 0x05C8FF);
    public static final ModRarity RED = new ModRarity("red", 0xFF2864);
    public static final ModRarity PURPLE = new ModRarity("purple", 0xB428FF);
    

    public static final ModRarity EXPERT = new ModRarity("expert", ExpertColorAnimation.INSTANCE);
    public static final ModRarity MASTER = new ModRarity("master", MasterColorAnimation.INSTANCE);
    public static final ModRarity QUEST = new ModRarity("quest",0xFFAF00);




    public static final Codec<ModRarity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(ModRarity::getName),
            ColorAnimation.CODEC.fieldOf("animation").forGetter(ModRarity::getAnimation),
            Style.Serializer.CODEC.fieldOf("style").forGetter(ModRarity::getStyle)
    ).apply(instance, ModRarity::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ModRarity> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ModRarity::getName,
            ByteBufCodecs.INT, ModRarity::getColor,
            Style.Serializer.TRUSTED_STREAM_CODEC, ModRarity::getStyle,
            ModRarity::new
    );
    private ColorAnimation animation;

    private String name;
    private Style style;


    public ModRarity(String name, int color) {
        this.name = name;
        this.animation = new ColorAnimation(color);
        this.style = Style.EMPTY.withColor(color);
    }
    public ModRarity(String name, ColorAnimation animation) {
        this.name = name;
        this.animation = animation;
        this.style = Style.EMPTY.withColor(animation.getColor());
    }
    public ModRarity(String name, Style style) {
        this.name = name;
        this.animation = new ColorAnimation(0);
        this.style = style;
    }
    public ModRarity(String name, int color, Style style) {
        this.name = name;
        this.animation = new ColorAnimation(color);
        this.style = style;
    }
    public ModRarity(String string, ColorAnimation animation, Style style) {
        this.name = string;
        this.animation = animation;
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return animation.getColor();
    }

    public Style getStyle() {
        return style;
    }

    public ColorAnimation getAnimation() {
        return animation;
    }

    @Override
    public @Nullable Codec<ModRarity> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, ModRarity> streamCodec() {
        return STREAM_CODEC;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ModRarity modRarity)) return false;

        return Objects.equals(getAnimation(), modRarity.getAnimation()) && Objects.equals(getName(), modRarity.getName()) && Objects.equals(getStyle(), modRarity.getStyle());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getAnimation());
        result = 31 * result + Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getStyle());
        return result;
    }
}
