package org.confluence.mod.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.confluence.mod.Confluence;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public final class ModTiers {
    //todo 镐力
    public static final Tier CANDY = new ModTier(2, 4000, 6, 2, 14, () -> Ingredient.of(Items.SUGAR),ModTags.Blocks.NEEDS_4_LEVEL); // 糖(圣诞限定）


    public static final Tier CACTUS = new ModTier(0, 144, 3, 1, 4, () -> Ingredient.of(Items.CACTUS),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier COPPER = new ModTier(1, 250, 4, 1, 5, () -> Ingredient.of(Items.COPPER_INGOT),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier TIN = new ModTier(1, 270, 4, 1, 5, () -> Ingredient.of(Materials.TIN_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier LEAD = new ModTier(2, 286, 6, 2, 14, () -> Ingredient.of(Materials.LEAD_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier SILVER = new ModTier(2, 304, 6, 2, 14, () -> Ingredient.of(Materials.SILVER_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier TUNGSTEN = new ModTier(2, 648, 8, 2, 18, () -> Ingredient.of(Materials.TUNGSTEN_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier GOLD = new ModTier(3, 1600, 8, 3, 22, () -> Ingredient.of(Items.GOLD_INGOT),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier FOSSIL = new ModTier(3, 1200, 8, 3, 22, () -> Ingredient.of(Materials.STURDY_FOSSIL.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier PLATINUM = new ModTier(3, 1661, 8, 3, 22, () -> Ingredient.of(Materials.PLATINUM_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier METEORITE = new ModTier(3, 0, 9, 3, 23, () -> Ingredient.of(Materials.METEORITE_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);

    // Sorted
    public static final Tier EBONY = new ModTier(4, 0, 10, 4, 24,  ()->Ingredient.of(Materials.EBONY_INGOT.get()),ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier TR_CRIMSON = new ModTier(4, 0, 11, 4, 25, () -> Ingredient.of(Materials.TR_CRIMSON_INGOT.get()), ModTags.Blocks.NEEDS_4_LEVEL);
    public static final Tier HELLSTONE = new ModTier(5, 0, 14, 6, 27, () -> Ingredient.of(Materials.HELLSTONE_INGOT.get()), ModTags.Blocks.NEEDS_5_LEVEL);
    public static final Tier COBALT = new ModTier(6, 0, 4, 1, 5, () -> Ingredient.of(Materials.COBALT_INGOT.get()), ModTags.Blocks.NEEDS_6_LEVEL);
    public static final Tier PALLADIUM = new ModTier(6, 0, 4, 1, 5, () -> Ingredient.of(Materials.PALLADIUM_INGOT.get()), ModTags.Blocks.NEEDS_6_LEVEL);
    public static final Tier MITHRIL = new ModTier(7, 0, 4, 1, 5, () -> Ingredient.of(Materials.MITHRIL_INGOT.get()), ModTags.Blocks.NEEDS_7_LEVEL);
    public static final Tier ORICHALCUM = new ModTier(7, 0, 4, 1, 5, () -> Ingredient.of(Materials.ORICHALCUM_INGOT.get()), ModTags.Blocks.NEEDS_7_LEVEL);
    public static final Tier ADAMANTITE = new ModTier(8, 0, 4, 1, 5, () -> Ingredient.of(Materials.ADAMANTITE_INGOT.get()), ModTags.Blocks.NEEDS_8_LEVEL);
    public static final Tier TITANIUM = new ModTier(8, 0, 4, 1, 5, () -> Ingredient.of(Materials.TITANIUM_INGOT.get()), ModTags.Blocks.NEEDS_8_LEVEL);
    public static final Tier HALLOWED = new ModTier(9, 0, 4, 1, 5, () -> Ingredient.of(Materials.HALLOWED_INGOT.get()), ModTags.Blocks.NEEDS_9_LEVEL);



    public record ModTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient,TagKey<Block> incorrectBlocksForDrops) implements Tier {
        @Override
        public int getUses() {
            return uses;
        }
        @Override
        public float getSpeed() {
            return speed;
        }
        @Override
        public float getAttackDamageBonus() {
            return attackDamageBonus;
        }
        @Override
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {return this.incorrectBlocksForDrops;}
        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }
        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }
    }
}
