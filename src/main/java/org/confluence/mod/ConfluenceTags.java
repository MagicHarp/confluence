package org.confluence.mod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceTags {
    // block
    public static final TagKey<Block> NEEDS_COPPER_TOOL = TagKey.create(Registries.BLOCK, new ResourceLocation(MODID, "needs_copper_tool"));
    public static final TagKey<Block> NEEDS_EBONY_TOOL = TagKey.create(Registries.BLOCK, new ResourceLocation(MODID, "needs_ebony_tool"));
}
