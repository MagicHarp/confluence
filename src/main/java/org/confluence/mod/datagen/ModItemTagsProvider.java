package org.confluence.mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.bow.Bows;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.food.Foods;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.hook.Hooks;
import org.confluence.mod.item.mana.ManaWeapons;
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
        CurioItems.acceptTag(tag(ModTags.Items.ACCESSORY));
        Hooks.acceptTag(tag(ModTags.Items.HOOK));
        tag(ModTags.Items.MINECART).add(Items.MINECART);
        tag(ModTags.Items.PROVIDE_MANA).add(ModItems.STAR.get(), ModItems.SOUL_CAKE.get(), ModItems.SUGAR_PLUM.get());
        tag(ModTags.Items.PROVIDE_LIFE).add(ModItems.HEART.get(), ModItems.CANDY_APPLE.get(), ModItems.CANDY_CANE.get());
        tag(ModTags.Items.DESERT_FOSSIL).add(ModBlocks.DESERT_FOSSIL.get().asItem());
        tag(ModTags.Items.GRAVEL).add(Blocks.GRAVEL.asItem());
        tag(ModTags.Items.SLUSH).add(ModBlocks.SLUSH.get().asItem());
        tag(ModTags.Items.MARINE_GRAVEL).add(ModBlocks.MARINE_GRAVEL.get().asItem());
        tag(ModTags.Items.JUNK).add(Blocks.LILY_PAD.asItem(), Items.LEATHER_BOOTS, Blocks.SEAGRASS.asItem());
        tag(ModTags.Items.CORAL).add(Blocks.TUBE_CORAL.asItem(), Blocks.TUBE_CORAL_FAN.asItem(), Blocks.TUBE_CORAL_BLOCK.asItem(), Blocks.BRAIN_CORAL.asItem(), Blocks.BRAIN_CORAL_FAN.asItem(), Blocks.BRAIN_CORAL_BLOCK.asItem(),
                Blocks.BUBBLE_CORAL.asItem(), Blocks.BUBBLE_CORAL_FAN.asItem(), Blocks.BUBBLE_CORAL_BLOCK.asItem(), Blocks.FIRE_CORAL.asItem(), Blocks.FIRE_CORAL_FAN.asItem(), Blocks.FIRE_CORAL_BLOCK.asItem(), Blocks.HORN_CORAL.asItem(), Blocks.HORN_CORAL_FAN.asItem(), Blocks.HORN_CORAL_BLOCK.asItem(),
                Blocks.DEAD_TUBE_CORAL.asItem(), Blocks.DEAD_TUBE_CORAL_FAN.asItem(), Blocks.DEAD_TUBE_CORAL_BLOCK.asItem(), Blocks.DEAD_BRAIN_CORAL.asItem(), Blocks.DEAD_BRAIN_CORAL_FAN.asItem(), Blocks.DEAD_BRAIN_CORAL_BLOCK.asItem(),
                Blocks.DEAD_BUBBLE_CORAL.asItem(), Blocks.DEAD_BUBBLE_CORAL_FAN.asItem(), Blocks.DEAD_BUBBLE_CORAL_BLOCK.asItem(), Blocks.DEAD_FIRE_CORAL.asItem(), Blocks.DEAD_FIRE_CORAL_FAN.asItem(), Blocks.DEAD_FIRE_CORAL_BLOCK.asItem(), Blocks.DEAD_HORN_CORAL.asItem(), Blocks.DEAD_HORN_CORAL_FAN.asItem(), Blocks.DEAD_HORN_CORAL_BLOCK.asItem());
        tag(ModTags.Items.TR_PLANKS).add(ModBlocks.EBONY_LOG_BLOCKS.PLANKS.get().asItem(), ModBlocks.SHADOW_LOG_BLOCKS.PLANKS.get().asItem(), ModBlocks.PALM_LOG_BLOCKS.PLANKS.get().asItem(),
                ModBlocks.SPOOKY_LOG_BLOCKS.PLANKS.get().asItem(), ModBlocks.ASH_LOG_BLOCKS.PLANKS.get().asItem(), ModBlocks.PEARL_LOG_BLOCKS.PLANKS.get().asItem(), Blocks.OAK_PLANKS.asItem(), Blocks.SPRUCE_PLANKS.asItem(),
                Blocks.ACACIA_PLANKS.asItem(), Blocks.DARK_OAK_PLANKS.asItem(), Blocks.JUNGLE_PLANKS.asItem(), Blocks.MANGROVE_PLANKS.asItem(), Blocks.CHERRY_PLANKS.asItem(), Blocks.BAMBOO_PLANKS.asItem(), Blocks.CRIMSON_PLANKS.asItem(),
                Blocks.BIRCH_PLANKS.asItem(), Blocks.WARPED_PLANKS.asItem());
        tag(ModTags.Items.LEAD_AND_IRON).add(Items.IRON_INGOT, Materials.LEAD_INGOT.get());
        IntrinsicTagAppender<Item> torch = tag(ModTags.Items.TORCH);
        torch.add(Items.TORCH, Items.SOUL_TORCH);
        for (Torches torches : Torches.values()) torch.add(torches.item.get());
        tag(ModTags.Items.BOTTOMLESS).add(
                ModItems.BOTTOMLESS_WATER_BUCKET.get(),
                ModItems.BOTTOMLESS_LAVA_BUCKET.get(),
                ModItems.BOTTOMLESS_HONEY_BUCKET.get(),
                ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()
        );
        tag(ModTags.Items.FRUIT).add(
                Items.APPLE, Items.MELON_SLICE, Foods.APRICOT.get(),
                Foods.BANANA.get(), Foods.CHERRY.get(), Foods.COCONUT.get(),
                Foods.DRAGON_FRUIT.get(), Foods.GRAPE_FRUIT.get(), Foods.LEMON.get(),
                Foods.MANGO.get(), Foods.PEACH.get(), Foods.PINEAPPLE.get(),
                Foods.PLUM.get(), Foods.GRAPE.get(), Foods.SPICY_PEPPER.get(),
                Foods.STAR_FRUIT.get(), Foods.POMEGRANATE.get(), Foods.RAMBUTAN.get(),
                Foods.BLOOD_ORANGE.get(), Foods.ELDERBERRY.get(), Foods.BLACKCURRANT.get()
        );
        tag(Tags.Items.TOOLS_BOWS).add(
                Bows.WOODEN_SHORT_BOW.get(),
                Bows.COPPER_SHORT_BOW.get(),
                Bows.TIN_SHORT_BOW.get(),
                Bows.IRON_SHORT_BOW.get(),
                Bows.LEAD_SHORT_BOW.get(),
                Bows.SILVER_SHORT_BOW.get(),
                Bows.TUNGSTEN_SHORT_BOW.get(),
                Bows.GOLDEN_SHORT_BOW.get(),
                Bows.PLATINUM_SHORT_BOW.get()
        );
        IntrinsicTagAppender<Item> rangedWeapon = tag(ModTags.Items.RANGED_WEAPON);
        rangedWeapon.addTag(Tags.Items.TOOLS_BOWS);
        rangedWeapon.addTag(Tags.Items.TOOLS_CROSSBOWS);
        rangedWeapon.addTag(Tags.Items.TOOLS_TRIDENTS);
        for (ManaWeapons manaWeapons : ManaWeapons.values()) rangedWeapon.add(manaWeapons.get());
        tag(ModTags.Items.COIN).add(ModItems.COPPER_COIN.get(), ModItems.SILVER_COIN.get(), ModItems.GOLDEN_COIN.get(), ModItems.PLATINUM_COIN.get());
        tag(ItemTags.MUSIC_DISCS).add(ModItems.ALPHA.get());
        IntrinsicTagAppender<Item> hammer = tag(ModTags.Items.HAMMER);
        for (Hammers hammers : Hammers.values()) hammer.add(hammers.get());
    }
}
