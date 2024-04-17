package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import top.theillusivec4.curios.Curios;

import static org.confluence.mod.Confluence.MODID;

public class ModTags {
    public static final TagKey<Block> NEEDS_4_LEVEL = BlockTags.create(new ResourceLocation(MODID, "needs_4_level"));
    public static final TagKey<Block> NEEDS_5_LEVEL = BlockTags.create(new ResourceLocation(MODID, "needs_5_level"));
    public static final TagKey<Block> NEEDS_6_LEVEL = BlockTags.create(new ResourceLocation(MODID, "needs_6_level"));
    public static final TagKey<Block> NEEDS_7_LEVEL = BlockTags.create(new ResourceLocation(MODID, "needs_7_level"));
    public static final TagKey<Block> NEEDS_8_LEVEL = BlockTags.create(new ResourceLocation(MODID, "needs_8_level"));

    public static final TagKey<Item> CURIO = ItemTags.create(new ResourceLocation(Curios.MODID, "curio"));
}