package org.confluence.mod.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.client.color.AnimateColor;

public final class ModRarity {
    public static final Rarity GRAY = Rarity.create("gray", style -> style.withColor(0x828282));
    public static final Rarity WHITE = Rarity.create("white", style -> style.withColor(0xFFFFFF));
    public static final Rarity BLUE = Rarity.create("blue", style -> style.withColor(0x9696FF));
    public static final Rarity GREEN = Rarity.create("green", style -> style.withColor(0x96FF96));
    public static final Rarity ORANGE = Rarity.create("orange", style -> style.withColor(0x96FF96));
    public static final Rarity LIGHT_RED = Rarity.create("light_red", style -> style.withColor(0xFF9696));
    public static final Rarity PINK = Rarity.create("pink", style -> style.withColor(0xFF96FF));
    public static final Rarity LIGHT_PURPLE = Rarity.create("light_purple", style -> style.withColor(0xD2A0FF));
    public static final Rarity LIME = Rarity.create("lime", style -> style.withColor(0x96FF0A));
    public static final Rarity YELLOW = Rarity.create("yellow", style -> style.withColor(0xFFFF0A));
    public static final Rarity CYAN = Rarity.create("cyan", style -> style.withColor(0x05C8FF));
    public static final Rarity RED = Rarity.create("red", style -> style.withColor(0xFF2864));
    public static final Rarity PURPLE = Rarity.create("purple", style -> style.withColor(0xB428FF));

    public static final Rarity EXPERT = Rarity.create("expert", style -> style.withColor(0x000000));
    public static final Rarity MASTER = Rarity.create("master", style -> style.withColor(0x000000));
    public static final Rarity QUEST = Rarity.create("quest", style -> style.withColor(0xFFAF00));

    public interface Special {
        MutableComponent withColor(String descriptionId);
    }

    public interface Expert extends Special {
        @Override
        default MutableComponent withColor(String descriptionId) {
            return Component.translatable(descriptionId).withStyle(style -> style.withColor(AnimateColor.getExpertColor()));
        }
    }

    public interface Master extends Special {
        @Override
        default MutableComponent withColor(String descriptionId) {
            return Component.translatable(descriptionId).withStyle(style -> style.withColor(AnimateColor.getMasterColor()));
        }
    }
}
