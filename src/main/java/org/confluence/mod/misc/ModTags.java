package org.confluence.mod.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import top.theillusivec4.curios.Curios;

import static org.confluence.mod.Confluence.MODID;

public final class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_4_LEVEL = register("needs_4_level");
        public static final TagKey<Block> NEEDS_5_LEVEL = register("needs_5_level");
        public static final TagKey<Block> NEEDS_6_LEVEL = register("needs_6_level");
        public static final TagKey<Block> NEEDS_7_LEVEL = register("needs_7_level");
        public static final TagKey<Block> NEEDS_8_LEVEL = register("needs_8_level");
        public static final TagKey<Block> FLOWER_BOOTS_AVAILABLE = register("flower_boots_available");
        public static final TagKey<Block> TORCH = register("torch");
        public static final TagKey<Block> HARDCORE = register("hardcore");

        private static TagKey<Block> register(String id) {
            return BlockTags.create(new ResourceLocation(MODID, id));
        }
    }

    public static class Items {
        public static final TagKey<Item> CURIO = curios("curio");
        public static final TagKey<Item> HOOK = curios("hook");
        public static final TagKey<Item> PROVIDE_MANA = register("provide_mana");
        public static final TagKey<Item> PROVIDE_LIFE = register("provide_life");
        public static final TagKey<Item> COIN = register("coin");
        public static final TagKey<Item> TORCH = register("torch");
        public static final TagKey<Item> HARDCORE = register("hardcore");
        public static final TagKey<Item> BOTTOMLESS = register("bottomless");
        public static final TagKey<Item> FRUIT = register("fruit");
        public static final TagKey<Item> MINUTE_WATCH = register("minute_watch");

        private static TagKey<Item> curios(String id) {
            return ItemTags.create(new ResourceLocation(Curios.MODID, id));
        }

        private static TagKey<Item> register(String id) {
            return ItemTags.create(new ResourceLocation(MODID, id));
        }
    }

    public static final TagKey<Fluid> FISHING_ABLE = FluidTags.create(new ResourceLocation(MODID, "fishing_able"));
    public static final TagKey<Fluid> NOT_LAVA = FluidTags.create(new ResourceLocation(MODID, "not_lava"));

    public static final TagKey<DamageType> HARMFUL_EFFECT = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MODID, "harmful_effect"));
}
