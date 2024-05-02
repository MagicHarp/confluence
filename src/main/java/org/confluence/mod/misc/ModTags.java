package org.confluence.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.Curios;

public final class ModTags {
    public static final TagKey<Item> CURIO = ItemTags.create(new ResourceLocation(Curios.MODID, "curio"));
}
