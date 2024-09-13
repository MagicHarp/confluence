package org.confluence.mod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public final class ModTiers {
    public static final ModTier CANDY = new ModTier(2, 4000, 6, 2, 14, () -> Ingredient.of(Items.SUGAR)); // 糖(圣诞限定）

    public static final ModTier CACTUS = new ModTier(0, 144, 3, 1, 4, () -> Ingredient.of(Items.CACTUS));
    public static final ModTier COPPER = new ModTier(1, 250, 4, 1, 5, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final ModTier TIN = new ModTier(1, 270, 4, 1, 5, () -> Ingredient.of(Materials.TIN_INGOT.get()));
    public static final ModTier LEAD = new ModTier(2, 286, 6, 2, 14, () -> Ingredient.of(Materials.LEAD_INGOT.get()));
    public static final ModTier SILVER = new ModTier(2, 304, 6, 2, 14, () -> Ingredient.of(Materials.SILVER_INGOT.get()));
    public static final ModTier TUNGSTEN = new ModTier(2, 648, 8, 2, 18, () -> Ingredient.of(Materials.TUNGSTEN_INGOT.get()));
    public static final ModTier GOLD = new ModTier(3, 1600, 8, 3, 22, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final ModTier FOSSIL = new ModTier(3, 1200, 8, 3, 22, () -> Ingredient.of(Materials.STURDY_FOSSIL.get()));
    public static final ModTier PLATINUM = new ModTier(3, 1661, 8, 3, 22, () -> Ingredient.of(Materials.PLATINUM_INGOT.get()));
    public static final ModTier METEORITE = new ModTier(3, 0, 9, 3, 23, () -> Ingredient.of(Materials.METEORITE_INGOT.get()));

    // Sorted
    public static final ForgeTier EBONY = new ForgeTier(4, 0, 10, 4, 24, ModTags.Blocks.NEEDS_4_LEVEL, () -> Ingredient.of(Materials.EBONY_INGOT.get()));
    public static final ForgeTier TR_CRIMSON = new ForgeTier(4, 0, 11, 4, 25, ModTags.Blocks.NEEDS_4_LEVEL, () -> Ingredient.of(Materials.TR_CRIMSON_INGOT.get()));
    public static final ForgeTier HELLSTONE = new ForgeTier(5, 0, 14, 6, 27, ModTags.Blocks.NEEDS_5_LEVEL, () -> Ingredient.of(Materials.HELLSTONE_INGOT.get()));
    public static final ForgeTier COBALT = new ForgeTier(6, 0, 4, 1, 5, ModTags.Blocks.NEEDS_6_LEVEL, () -> Ingredient.of(Materials.COBALT_INGOT.get()));
    public static final ForgeTier PALLADIUM = new ForgeTier(6, 0, 4, 1, 5, ModTags.Blocks.NEEDS_6_LEVEL, () -> Ingredient.of(Materials.PALLADIUM_INGOT.get()));
    public static final ForgeTier MITHRIL = new ForgeTier(7, 0, 4, 1, 5, ModTags.Blocks.NEEDS_7_LEVEL, () -> Ingredient.of(Materials.MITHRIL_INGOT.get()));
    public static final ForgeTier ORICHALCUM = new ForgeTier(7, 0, 4, 1, 5, ModTags.Blocks.NEEDS_7_LEVEL, () -> Ingredient.of(Materials.ORICHALCUM_INGOT.get()));
    public static final ForgeTier ADAMANTITE = new ForgeTier(8, 0, 4, 1, 5, ModTags.Blocks.NEEDS_8_LEVEL, () -> Ingredient.of(Materials.ADAMANTITE_INGOT.get()));
    public static final ForgeTier TITANIUM = new ForgeTier(8, 0, 4, 1, 5, ModTags.Blocks.NEEDS_8_LEVEL, () -> Ingredient.of(Materials.TITANIUM_INGOT.get()));
    public static final ForgeTier HALLOWED = new ForgeTier(9, 0, 4, 1, 5, ModTags.Blocks.NEEDS_9_LEVEL, () -> Ingredient.of(Materials.HALLOWED_INGOT.get()));

    public static void register() {
        ResourceLocation netherite = TierSortingRegistry.getName(Tiers.NETHERITE);
        ResourceLocation ebony = Confluence.asResource("ebony");
        ResourceLocation tr_crimson = Confluence.asResource("tr_crimson");
        ResourceLocation hellstone = Confluence.asResource("hellstone");
        ResourceLocation cobalt = Confluence.asResource("cobalt");
        ResourceLocation palladium = Confluence.asResource("palladium");
        ResourceLocation mithril = Confluence.asResource("mithril");
        ResourceLocation orichalcum = Confluence.asResource("orichalcum");
        ResourceLocation adamantite = Confluence.asResource("adamantite");
        ResourceLocation titanium = Confluence.asResource("titanium");
        ResourceLocation hallowed = Confluence.asResource("hallowed");
        assert netherite != null;
        TierSortingRegistry.registerTier(EBONY, ebony, List.of(), List.of(tr_crimson));
        TierSortingRegistry.registerTier(TR_CRIMSON, tr_crimson, List.of(ebony), List.of(netherite));
        /* 下界合金 */
        TierSortingRegistry.registerTier(HELLSTONE, hellstone, List.of(netherite), List.of(cobalt));
        TierSortingRegistry.registerTier(COBALT, cobalt, List.of(hellstone), List.of(palladium));
        TierSortingRegistry.registerTier(PALLADIUM, palladium, List.of(cobalt), List.of(mithril));
        TierSortingRegistry.registerTier(MITHRIL, mithril, List.of(palladium), List.of(orichalcum));
        TierSortingRegistry.registerTier(ORICHALCUM, orichalcum, List.of(mithril), List.of(adamantite));
        TierSortingRegistry.registerTier(ADAMANTITE, adamantite, List.of(orichalcum), List.of(titanium));
        TierSortingRegistry.registerTier(TITANIUM, titanium, List.of(adamantite), List.of(hallowed));
    }

    public record ModTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull Supplier<Ingredient> repairIngredient) implements Tier {
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
        public int getLevel() {
            return level;
        }

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
