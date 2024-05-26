package org.confluence.mod.item.fishing;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;
import static org.confluence.mod.item.ModRarity.*;

public enum Baits implements EnumRegister<Baits.BaitItem> {
    APPRENTICE_BAIT("apprentice_bait", BLUE, 0.15F),
    JOURNEYMAN_BAIT("journeyman_bait", GREEN, 0.3F),
    MASTER_BAIT("master_bait", ORANGE, 0.5F),
    BLACK_DRAGONFLY("black_dragonfly", BLUE, 0.2F),
    BLACK_SCORPION("black_scorpion", BLUE, 0.15F),
    BLUE_DRAGONFLY("blue_dragonfly", BLUE, 0.2F),
    BLUE_JELLYFISH("blue_jellyfish", BLUE, 0.2F),
    BUGGY("buggy", GREEN, 0.4F),
    ENCHANTED_NIGHTCRAWLER("enchanted_nightcrawler", GREEN, 0.35F),
    FIREFLY("firefly", BLUE, 0.2F),
    GLOWING_SNAIL("glowing_snail", BLUE, 0.15F),
    GOLD_BUTTERFLY("gold_butterfly", ORANGE, 0.5F),
    GOLD_DRAGONFLY("gold_dragonfly", ORANGE, 0.5F),
    GOLD_GRASSHOPPER("gold_grasshopper", ORANGE, 0.5F),
    GOLD_LADYBUG("gold_ladybug", ORANGE, 0.5F),
    GOLD_WATER_STRIDER("gold_water_strider", ORANGE, 0.5F),
    GOLD_WORM("gold_warm", ORANGE, 0.5F),
    GRASSHOPPER("grasshopper", WHITE, 0.1F),
    GREEN_DRAGONFLY("green_dragonfly", BLUE, 0.2F),
    GREEN_JELLYFISH("green_jellyfish", BLUE, 0.2F),
    GRUBBY("grubby", BLUE, 0.15F),
    HELL_BUTTERFLY("hell_butterfly", BLUE, 0.15F),
    JULIA_BUTTERFLY("julia_butterfly", BLUE, 0.25F),
    LADYBUG("ladybug", BLUE, 0.17F),
    LAVAFLY("lavafly", BLUE, 0.25F),
    LIGHTNING_BUG("lightning_bug", GREEN, 0.35F),
    MAGGOT("maggot", BLUE, 0.22F),
    MAGMA_SNAIL("magma_snail", GREEN, 0.35F),
    MONARCH_BUTTERFLY("monarch_butterfly", WHITE, 0.05F),
    ORANGE_DRAGONFLY("orange_dragonfly", BLUE, 0.2F),
    PINK_JELLYFISH("pink_jellyfish", BLUE, 0.2F),
    PURPLE_EMPEROR_BUTTERFLY("purple_emperor_butterfly", GREEN, 0.35F),
    RED_ADMIRAL_BUTTERFLY("red_admiral_butterfly", GREEN, 0.3F),
    RED_DRAGONFLY("red_dragonfly", BLUE, 0.2F),
    SCORPION("scorpion", WHITE, 0.1F),
    SLUGGY("sluggy", BLUE, 0.25F),
    SNAIL("snail", WHITE, 0.1F),
    STINKBUG("stinkbug", WHITE, 0.1F),
    SULPHUR_BUTTERFLY("sulphur_butter", WHITE, 0.1F),
    TREE_NYMPH_BUTTERFLY("tree_numph_butter_fly", ORANGE, 0.5F),
    TRUFFLE_WORM("truffle_worm", ORANGE, 6.66F),
    ULYSSES_BUTTERFLY("ulysses_butterfly", BLUE, 0.2F),
    WATER_STRIDER("water_strider", BLUE, 0.17F),
    WORM("worm", BLUE, 0.25F),
    YELLOW_DRAGONFLY("yellow_dragonfly", BLUE, 0.2F),
    ZEBRA_SWALLOWTAIL_BUTTERFLY("zebra_swallowtail_butterfly", BLUE, 0.15F);

    private final RegistryObject<BaitItem> value;

    Baits(String id, Rarity rarity, float bonus) {
        this.value = ModItems.ITEMS.register(id, () -> new BaitItem(rarity, bonus));
    }

    @Override
    public RegistryObject<BaitItem> getValue() {
        return value;
    }

    public static void init() {}

    public static class BaitItem extends Item implements IBait {
        private final float bonus;

        public BaitItem(Rarity rarity, float bonus) {
            super(new Properties().rarity(rarity));
            this.bonus = bonus;
        }

        @Override
        public float getBaitBonus() {
            return bonus;
        }

        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, @NotNull TooltipFlag pIsAdvanced) {
            list.add(Component.translatable("info.confluence.bait", ATTRIBUTE_MODIFIER_FORMAT.format(getBaitBonus() * 100.0)).withStyle(style -> style.withColor(ChatFormatting.BLUE)));
        }
    }

    public interface IBait {
        float getBaitBonus();
    }
}
