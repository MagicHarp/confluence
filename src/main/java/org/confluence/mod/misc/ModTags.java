package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.confluence.mod.Confluence;
import top.theillusivec4.curios.Curios;

public final class ModTags {
    public static final TagKey<Item> CURIO = ItemTags.create(new ResourceLocation(Curios.MODID, "curio"));
    public static final TagKey<Block> FLOWER_BOOTS_AVAILABLE = BlockTags.create(new ResourceLocation(Confluence.MODID, "flower_boots_available"));
}
