package org.confluence.mod.common.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;
import org.confluence.mod.common.init.item.Materials;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public final class ModTiers {
    public static final Tier CANDY = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 4000, 6, 2, 14, () -> Ingredient.of(Items.SUGAR)); // 糖(圣诞限定）
    public static final Tier CACTUS = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 144, 3, 1, 4, () -> Ingredient.of(Items.CACTUS));
    public static final Tier COPPER = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 250, 4, 1, 5, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier TIN = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 270, 4, 1, 5, () -> Ingredient.of(Materials.TIN_INGOT.get()));
    public static final Tier LEAD = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 286, 6, 2, 14, () -> Ingredient.of(Materials.LEAD_INGOT.get()));
    public static final Tier SILVER = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 304, 6, 2, 14, () -> Ingredient.of(Materials.SILVER_INGOT.get()));
    public static final Tier TUNGSTEN = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 648, 8, 2, 18, () -> Ingredient.of(Materials.TUNGSTEN_INGOT.get()));
    public static final Tier GOLD = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 1600, 8, 3, 22, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final Tier FOSSIL = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 1200, 8, 3, 22, () -> Ingredient.of(Materials.STURDY_FOSSIL.get()));
    public static final Tier PLATINUM = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 1661, 8, 3, 22, () -> Ingredient.of(Materials.PLATINUM_INGOT.get()));
    public static final Tier METEORITE = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 0, 9, 3, 23, () -> Ingredient.of(Materials.METEORITE_INGOT.get()));

    public static final Tier EBONY = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 0, 10, 4, 24, () -> Ingredient.of(Materials.EBONY_INGOT.get()));
    public static final Tier TR_CRIMSON = new SimpleTier(ModTags.Blocks.NEEDS_4_LEVEL, 0, 11, 4, 25, () -> Ingredient.of(Materials.TR_CRIMSON_INGOT.get()));
    public static final Tier HELLSTONE = new SimpleTier(ModTags.Blocks.NEEDS_5_LEVEL, 0, 14, 6, 27, () -> Ingredient.of(Materials.HELLSTONE_INGOT.get()));
    public static final Tier COBALT = new SimpleTier(ModTags.Blocks.NEEDS_6_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.COBALT_INGOT.get()));
    public static final Tier PALLADIUM = new SimpleTier(ModTags.Blocks.NEEDS_6_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.PALLADIUM_INGOT.get()));
    public static final Tier MITHRIL = new SimpleTier(ModTags.Blocks.NEEDS_7_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.MITHRIL_INGOT.get()));
    public static final Tier ORICHALCUM = new SimpleTier(ModTags.Blocks.NEEDS_7_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.ORICHALCUM_INGOT.get()));
    public static final Tier ADAMANTITE = new SimpleTier(ModTags.Blocks.NEEDS_8_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.ADAMANTITE_INGOT.get()));
    public static final Tier TITANIUM = new SimpleTier(ModTags.Blocks.NEEDS_8_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.TITANIUM_INGOT.get()));
    public static final Tier HALLOWED = new SimpleTier(ModTags.Blocks.NEEDS_9_LEVEL, 0, 4, 1, 5, () -> Ingredient.of(Materials.HALLOWED_INGOT.get()));

    /**
     * 关于incorrectBlocksForDrops，请看这个{@link BlockTags#INCORRECT_FOR_WOODEN_TOOL}。不要乱写
     */
    public static void register() { // todo 镐力
    }

    public static class PoweredTier extends SimpleTier {
        private final int power;

        public PoweredTier(int power, TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
            super(incorrectBlocksForDrops, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient);
            this.power = power;
        }

        public int getPower() {
            return power;
        }

        @Override
        public @NotNull Tool createToolProperties(@NotNull TagKey<Block> block) {
            return new Tool(List.of(
                    Tool.Rule.deniesDrops(getIncorrectBlocksForDrops()),
                    Tool.Rule.minesAndDrops(block, getSpeed())
            ), 1.0F, 1);
        }

        @Override
        public @NotNull String toString() {
            return "PoweredTier[" +
                    "incorrectBlocksForDrops=" + getIncorrectBlocksForDrops() + ", " +
                    "uses=" + getUses() + ", " +
                    "speed=" + getSpeed() + ", " +
                    "attackDamageBonus=" + getAttackDamageBonus() + ", " +
                    "enchantmentValue=" + getEnchantmentValue() + ", " +
                    "repairIngredient=" + getRepairIngredient() + ", " +
                    "power=" + power + ']';
        }
    }
}
