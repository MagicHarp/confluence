package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.ModTags;
import org.confluence.mod.block.DecorationLogBlocks;
import org.confluence.mod.block.Ores;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.confluence.mod.Confluence.MODID;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper helper) {
        super(output, lookup, MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        DecorationLogBlocks.acceptAxeTag(tag(BlockTags.MINEABLE_WITH_AXE));
        Ores.acceptTag(tag(BlockTags.MINEABLE_WITH_PICKAXE));

        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
            Ores.EBONY_ORE.get(), Ores.DEEPSLATE_EBONY_ORE.get(), Ores.EBONY_BLOCK.get(), Ores.RAW_EBONY_BLOCK.get(),
            Ores.ANOTHER_CRIMSON_ORE.get(), Ores.DEEPSLATE_ANOTHER_CRIMSON_ORE.get(), Ores.ANOTHER_CRIMSON_BLOCK.get(), Ores.RAW_ANOTHER_CRIMSON_BLOCK.get()
        );

        tag(ModTags.NEEDS_4_LEVEL).add(
            Ores.HELLSTONE.get()
        );
        tag(ModTags.NEEDS_5_LEVEL).add(
            Ores.DEEPSLATE_COBALT_ORE.get(), Ores.RAW_COBALT_BLOCK.get(), Ores.COBALT_BLOCK.get(),
            Ores.DEEPSLATE_PALLADIUM_ORE.get(), Ores.RAW_PALLADIUM_BLOCK.get(), Ores.PALLADIUM_BLOCK.get()
        );
        tag(ModTags.NEEDS_6_LEVEL).add(
            Ores.DEEPSLATE_MITHRIL_ORE.get(), Ores.RAW_MITHRIL_BLOCK.get(), Ores.MITHRIL_BLOCK.get(),
            Ores.DEEPSLATE_ORICHALCUM_ORE.get(), Ores.RAW_ORICHALCUM_BLOCK.get(), Ores.ORICHALCUM_BLOCK.get()
        );
        tag(ModTags.NEEDS_7_LEVEL).add(
            Ores.DEEPSLATE_ADAMANTITE_ORE.get(), Ores.RAW_ADAMANTITE_BLOCK.get(), Ores.ADAMANTITE_BLOCK.get(),
            Ores.DEEPSLATE_TITANIUM_ORE.get(), Ores.RAW_TITANIUM_BLOCK.get(), Ores.TITANIUM_BLOCK.get()
        );
    }
}
