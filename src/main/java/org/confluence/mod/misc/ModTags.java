package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.confluence.mod.Confluence;
import top.theillusivec4.curios.Curios;

public final class ModTags {
    public static final TagKey<Item> CURIO = ItemTags.create(new ResourceLocation(Curios.MODID, "accessory"));
    public static final TagKey<Item> MINUTE_WATCH = ItemTags.create(new ResourceLocation(Confluence.MODID, "minute_watch"));
    public static final TagKey<Item> RANGED_WEAPON = ItemTags.create(new ResourceLocation(Confluence.MODID, "ranged_weapon"));
    public static final TagKey<Block> FLOWER_BOOTS_AVAILABLE = BlockTags.create(new ResourceLocation(Confluence.MODID, "flower_boots_available"));
    public static final TagKey<Fluid> WATER_LIKE_WALK = FluidTags.create(new ResourceLocation(Confluence.MODID, "water_like_walk"));
    public static final TagKey<Fluid> ALL_FLUID_WALK = FluidTags.create(new ResourceLocation(Confluence.MODID, "all_fluid_walk"));
}
