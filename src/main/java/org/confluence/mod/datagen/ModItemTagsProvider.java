package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> b, @Nullable ExistingFileHelper helper) {
        super(output, provider, b, Confluence.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        IntrinsicTagAppender<Item> appender = tag(ModTags.CURIO);
        for (CurioItems curioItems : CurioItems.values()) appender.add(curioItems.get());
        tag(ModTags.PROVIDE_MANA).add(ModItems.STAR.get(), ModItems.SOUL_CAKE.get());
        tag(ModTags.COIN).add(ModItems.COPPER_COIN.get(), ModItems.SILVER_COIN.get(), ModItems.GOLDEN_COIN.get(), ModItems.PLATINUM_COIN.get());
    }
}
