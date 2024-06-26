package org.confluence.mod.datagen.subprovider;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.Boxes;
import org.confluence.mod.block.common.DecorativeBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.block.natural.Ores;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.curio.CurioItems;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.confluence.mod.block.ModBlocks.*;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    public ModBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // region ore
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
        dropSelf(Ores.PALLADIUM_BLOCK.get());
        dropSelf(Ores.RAW_PLATINUM_BLOCK.get());
        dropSelf(Ores.MITHRIL_BLOCK.get());
        dropSelf(Ores.RAW_MITHRIL_BLOCK.get());
        dropSelf(Ores.ORICHALCUM_BLOCK.get());
        dropSelf(Ores.RAW_ORICHALCUM_BLOCK.get());
        dropSelf(Ores.ADAMANTITE_BLOCK.get());
        dropSelf(Ores.RAW_ADAMANTITE_BLOCK.get());
        dropSelf(Ores.TITANIUM_BLOCK.get());
        dropSelf(Ores.RAW_TITANIUM_BLOCK.get());

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
        // 宝石
        add(Ores.RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.DEEPSLATE_RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.DEEPSLATE_AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.DEEPSLATE_TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.DEEPSLATE_SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.ANOTHER_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.ANOTHER_AMETHYST.get()));
        add(Ores.DEEPSLATE_ANOTHER_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.ANOTHER_AMETHYST.get()));

        add(Ores.METEORITE_ORE.get(), block -> createOreDrop(block, Materials.RAW_METEORITE.get()));
        add(Ores.EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.DEEPSLATE_EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.ANOTHER_CRIMSON_ORE.get(), block -> createOreDrop(block, Materials.RAW_ANOTHER_CRIMSON.get()));
        add(Ores.DEEPSLATE_ANOTHER_CRIMSON_ORE.get(), block -> createOreDrop(block, Materials.RAW_ANOTHER_CRIMSON.get()));
        add(Ores.HELLSTONE.get(), block -> createOreDrop(block, Materials.RAW_HELLSTONE.get()));
        add(Ores.DEEPSLATE_COBALT_ORE.get(), block -> createOreDrop(block, Materials.RAW_COBALT.get()));
        add(Ores.DEEPSLATE_PALLADIUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PALLADIUM.get()));
        add(Ores.DEEPSLATE_MITHRIL_ORE.get(), block -> createOreDrop(block, Materials.RAW_MITHRIL.get()));
        add(Ores.DEEPSLATE_ORICHALCUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_ORICHALCUM.get()));
        add(Ores.DEEPSLATE_ADAMANTITE_ORE.get(), block -> createOreDrop(block, Materials.RAW_ADAMANTITE.get()));
        add(Ores.DEEPSLATE_TITANIUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_TITANIUM.get()));
        // endregion ore
        //torch
        for (Torches torch : Torches.values()) {
            dropSelf(torch.stand.get());
            dropSelf(torch.wall.get());
        }
        // region natural
        dropSelf(EBONY_STONE.get());
        dropSelf(EBONY_SAND.get());
        dropSelf(PEARL_STONE.get());
        dropSelf(PEARL_SAND.get());
        dropSelf(ANOTHER_CRIMSON_STONE.get());
        dropSelf(ANOTHER_CRIMSON_SAND.get());
        dropSelf(ASH_BLOCK.get());
        dropOther(ANOTHER_CRIMSON_GRASS_BLOCK.get(), Items.DIRT);
        dropOther(CORRUPT_GRASS_BLOCK.get(), Items.DIRT);
        dropOther(HALLOW_GRASS_BLOCK.get(), Items.DIRT);
        dropSelf(EBONY_LOG_BLOCKS.LOG.get());
        dropSelf(SHADOW_LOG_BLOCKS.LOG.get());
        dropSelf(PEARL_LOG_BLOCKS.LOG.get());
        dropSelf(PALM_LOG_BLOCKS.LOG.get());
        dropSelf(ASH_LOG_BLOCKS.LOG.get());
        dropOther(NATURES_GIFT.get(), CurioItems.NATURES_GIFT.get());
        add(JUNGLE_ROSE.get(), LootTable.lootTable().withPool(LootPool.lootPool()
            .add(LootItem.lootTableItem(JUNGLE_ROSE.get()).when(LootItemRandomChanceCondition.randomChance(0.05f)))));
        // endregion natural

        dropSelf(BIG_RUBY_BLOCK.get());
        dropSelf(BIG_AMBER_BLOCK.get());
        dropSelf(BIG_TOPAZ_BLOCK.get());
        dropSelf(BIG_SAPPHIRE_BLOCK.get());
        dropSelf(BIG_ANOTHER_AMETHYST_BLOCK.get());

        dropOther(LIFE_CRYSTAL_BLOCK.get(), Materials.LIFE_CRYSTAL.get());
        dropOther(MUSHROOM_GRASS_BLOCK.get(), Items.MUD);

        for (LogBlocks logBlocks : LogBlocks.LOG_BLOCKS) {
            dropSelf(logBlocks.PLANKS.get());
            if (logBlocks.STRIPPED_LOG != null) dropSelf(logBlocks.STRIPPED_LOG.get());
            if (logBlocks.WOOD != null) dropSelf(logBlocks.WOOD.get());
            if (logBlocks.STRIPPED_WOOD != null) dropSelf(logBlocks.STRIPPED_WOOD.get());
            dropSelf(logBlocks.BUTTON.get());
            dropSelf(logBlocks.FENCE.get());
            dropSelf(logBlocks.FENCE_GATE.get());
            dropSelf(logBlocks.PRESSURE_PLATE.get());
            add(logBlocks.SLAB.get(), this::createSlabItemTable);
            dropSelf(logBlocks.STAIRS.get());
            dropSelf(logBlocks.SIGN.get());
            dropSelf(logBlocks.TRAPDOOR.get());
            add(logBlocks.DOOR.get(), this::createDoorTable);
        }
        for (DecorativeBlocks decorativeBlocks : DecorativeBlocks.values()) {
            dropSelf(decorativeBlocks.get());
        }
        for (Boxes boxes : Boxes.values()) dropSelf(boxes.get());

        // 草药
        addHerbDrop(WATERLEAF.get(), ModItems.WATERLEAF.get(), ModItems.WATERLEAF_SEED.get());
        addHerbDrop(FLAMEFLOWERS.get(), ModItems.FLAMEFLOWERS.get(), ModItems.FLAMEFLOWERS_SEED.get());
        addHerbDrop(MOONSHINE_GRASS.get(), ModItems.MOONSHINE_GRASS.get(), ModItems.MOONSHINE_GRASS_SEED.get());
        addHerbDrop(SHINE_ROOT.get(), ModItems.SHINE_ROOT.get(), ModItems.SHINE_ROOT_SEED.get());
        addHerbDrop(SHIVERINGTHORNS.get(), ModItems.SHIVERINGTHORNS.get(), ModItems.SHIVERINGTHORNS_SEED.get());
        addHerbDrop(SUNFLOWERS.get(), ModItems.SUNFLOWERS.get(), ModItems.SUNFLOWERS_SEED.get());
        addHerbDrop(DEATHWEED.get(), ModItems.DEATHWEED.get(), ModItems.DEATHWEED_SEED.get());

        dropOther(ANOTHER_CRIMSON_MUSHROOM.get(), ModItems.ANOTHER_CRIMSON_MUSHROOM.get());
        dropOther(EBONY_MUSHROOM.get(), ModItems.EBONY_MUSHROOM.get());
        dropOther(GLOWING_MUSHROOM.get(), ModItems.GLOWING_MUSHROOM.get()); // TODO: 掉落概率不是100%；掉落蘑菇草种子
        dropOther(LIFE_MUSHROOM.get(), ModItems.LIFE_MUSHROOM.get());
        add(JUNGLE_SPORE.get(), LootTable.lootTable().withPool(LootPool.lootPool()
            .add(LootItem.lootTableItem(ModItems.JUNGLE_SPORE.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,3))))));

    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
            .filter(block -> map.containsKey(block.getLootTable())).toList();
    }

    private LootTable.Builder createTinOreDrop(Block block) {
        return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Materials.RAW_TIN.get())
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
        ));
    }

    // TODO: 时运 再生法杖 再生之斧
    private void addHerbDrop(BaseHerbBlock block,Item herb,Item seed) {
        add(block,LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .setBonusRolls(ConstantValue.exactly(0.5f))
                .add(LootItem.lootTableItem(herb))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseHerbBlock.AGE,2))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(seed)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,3))))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseHerbBlock.AGE,2))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(herb))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseHerbBlock.AGE,1)))));
    }


}
