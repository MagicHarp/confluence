package org.confluence.mod.datagen.subprovider;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.Ores;
import org.confluence.mod.item.common.Materials;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(
        ModBlocks.ACTUATORS.get()
    ).map(Block::asItem).collect(Collectors.toSet());

    public ModBlockLootSubProvider() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(Ores.TIN_BLOCK.get());
        dropSelf(Ores.RAW_TIN_BLOCK.get());
        dropSelf(Ores.LEAD_BLOCK.get());
        dropSelf(Ores.RAW_LEAD_BLOCK.get());
        dropSelf(Ores.SILVER_BLOCK.get());
        dropSelf(Ores.RAW_SILVER_BLOCK.get());
        dropSelf(Ores.TUNGSTEN_BLOCK.get());
        dropSelf(Ores.RAW_TUNGSTEN_BLOCK.get());
        dropSelf(Ores.PLATINUM_BLOCK.get());
        dropSelf(Ores.RAW_PLATINUM_BLOCK.get());
        dropSelf(Ores.EBONY_BLOCK.get());
        dropSelf(Ores.RAW_EBONY_BLOCK.get());
        dropSelf(Ores.ANOTHER_CRIMSON_BLOCK.get());
        dropSelf(Ores.RAW_ANOTHER_CRIMSON_BLOCK.get());
        dropSelf(Ores.COBALT_BLOCK.get());
        dropSelf(Ores.RAW_COBALT_BLOCK.get());

        add(Ores.TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.DEEPSLATE_TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.DEEPSLATE_LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.DEEPSLATE_SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.TUNGSTEN_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.DEEPSLATE_SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        add(Ores.DEEPSLATE_PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        add(Ores.METEORITE_ORE.get(), block -> createOreDrop(block, Materials.RAW_METEORITE.get()));
        add(Ores.EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.DEEPSLATE_EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.ANOTHER_CRIMSON_ORE.get(), block -> createOreDrop(block, Materials.RAW_ANOTHER_CRIMSON.get()));
        add(Ores.DEEPSLATE_ANOTHER_CRIMSON_ORE.get(), block -> createOreDrop(block, Materials.RAW_ANOTHER_CRIMSON.get()));
        add(Ores.HELLSTONE.get(), block -> createOreDrop(block, Materials.RAW_HELLSTONE.get()));
        add(Ores.DEEPSLATE_COBALT_ORE.get(), block -> createOreDrop(block, Materials.RAW_COBALT.get()));
    }

    private LootTable.Builder createTinOreDrop(Block block) {
        return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Materials.RAW_TIN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
}
