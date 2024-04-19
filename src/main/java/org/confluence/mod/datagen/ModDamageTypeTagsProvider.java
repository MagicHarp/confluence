package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static org.confluence.mod.misc.ModDamageTypes.*;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper helper) {
        super(output, future, Confluence.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.HARMFUL_EFFECT).add(ACID_VENOM, CURSED_INFERNO, FROST_BURN);
    }
}
