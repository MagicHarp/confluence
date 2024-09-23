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
        dropSelf(Ores.TR_CRIMSON_BLOCK.get());
        dropSelf(Ores.RAW_TR_CRIMSON_BLOCK.get());
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
        dropSelf(Ores.CHLOROPHYTE_BLOCK.get());
        dropSelf(Ores.RAW_CHLOROPHYTE_BLOCK.get());
        dropSelf(Ores.LUMINITE_BLOCK.get());
        dropSelf(Ores.RAW_LUMINITE_BLOCK.get());
        dropSelf(Ores.HELLSTONE_BLOCK.get());
        dropSelf(Ores.RAW_HELLSTONE_BLOCK.get());
        dropSelf(DESERT_FOSSIL.get());
        dropSelf(SLUSH.get());
        dropSelf(MARINE_GRAVEL.get());
        dropSelf(DIATOMACEOUS.get());
        dropSelf(CLOUD_BLOCK.get());
        dropSelf(RAIN_CLOUD_BLOCK.get());
        dropSelf(SNOW_CLOUD_BLOCK.get());
        dropSelf(Ores.STURDY_FOSSIL_BLOCK.get());

        dropSelf(EXTRACTINATOR.get());
        dropSelf(DEEPSLATE_PRESSURE_PLATE.get());

        add(Ores.SANCTIFICATION_COAL_ORE.get(), block -> createOreDrop(block, Items.COAL));
        add(Ores.CORRUPTION_COAL_ORE.get(), block -> createOreDrop(block, Items.COAL));
        add(Ores.FLESHIFICATION_COAL_ORE.get(), block -> createOreDrop(block, Items.COAL));
        add(Ores.TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.SANCTIFICATION_TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.CORRUPTION_TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.FLESHIFICATION_TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.DEEPSLATE_TIN_ORE.get(), this::createTinOreDrop);
        add(Ores.SANCTIFICATION_COPPER_ORE.get(), super::createCopperOreDrops);
        add(Ores.CORRUPTION_COPPER_ORE.get(), super::createCopperOreDrops);
        add(Ores.FLESHIFICATION_COPPER_ORE.get(), super::createCopperOreDrops);
        add(Ores.LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.SANCTIFICATION_LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.CORRUPTION_LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.FLESHIFICATION_LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.DEEPSLATE_LEAD_ORE.get(), block -> createOreDrop(block, Materials.RAW_LEAD.get()));
        add(Ores.SANCTIFICATION_IRON_ORE.get(), block -> createOreDrop(block, Items.RAW_IRON));
        add(Ores.CORRUPTION_IRON_ORE.get(), block -> createOreDrop(block, Items.RAW_IRON));
        add(Ores.FLESHIFICATION_IRON_ORE.get(), block -> createOreDrop(block, Items.RAW_IRON));
        add(Ores.SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.SANCTIFICATION_SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.CORRUPTION_SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.FLESHIFICATION_SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.DEEPSLATE_SILVER_ORE.get(), block -> createOreDrop(block, Materials.RAW_SILVER.get()));
        add(Ores.TUNGSTEN_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.SANCTIFICATION_TUNGSTEN_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.CORRUPTION_TUNGSTEN_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.FLESHIFICATION_TUNGSTEN_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.DEEPSLATE_TUNGSTEN_ORE.get(), block -> createOreDrop(block, Materials.RAW_TUNGSTEN.get()));
        add(Ores.SANCTIFICATION_GOLD_ORE.get(), block -> createOreDrop(block, Items.RAW_GOLD));
        add(Ores.CORRUPTION_GOLD_ORE.get(), block -> createOreDrop(block, Items.RAW_GOLD));
        add(Ores.FLESHIFICATION_GOLD_ORE.get(), block -> createOreDrop(block, Items.RAW_GOLD));
        add(Ores.PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        add(Ores.SANCTIFICATION_PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        add(Ores.CORRUPTION_PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        add(Ores.FLESHIFICATION_PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        add(Ores.DEEPSLATE_PLATINUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PLATINUM.get()));
        // 红石青金石
        add(Ores.SANCTIFICATION_LAPIS_ORE.get(), super::createLapisOreDrops);
        add(Ores.CORRUPTION_LAPIS_ORE.get(), super::createLapisOreDrops);
        add(Ores.FLESHIFICATION_LAPIS_ORE.get(), super::createLapisOreDrops);
        // 宝石
        add(Ores.RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.SANCTIFICATION_RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.CORRUPTION_RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.FLESHIFICATION_RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.DEEPSLATE_RUBY_ORE.get(), block -> createOreDrop(block, Materials.RUBY.get()));
        add(Ores.AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.SANCTIFICATION_AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.CORRUPTION_AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.FLESHIFICATION_AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.DEEPSLATE_AMBER_ORE.get(), block -> createOreDrop(block, Materials.AMBER.get()));
        add(Ores.TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.SANCTIFICATION_TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.CORRUPTION_TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.FLESHIFICATION_TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.DEEPSLATE_TOPAZ_ORE.get(), block -> createOreDrop(block, Materials.TOPAZ.get()));
        add(Ores.TR_EMERALD_ORE.get(), block -> createOreDrop(block, Materials.TR_EMERALD.get()));
        add(Ores.SANCTIFICATION_TR_EMERALD_ORE.get(), block -> createOreDrop(block, Materials.TR_EMERALD.get()));
        add(Ores.CORRUPTION_TR_EMERALD_ORE.get(), block -> createOreDrop(block, Materials.TR_EMERALD.get()));
        add(Ores.FLESHIFICATION_TR_EMERALD_ORE.get(), block -> createOreDrop(block, Materials.TR_EMERALD.get()));
        add(Ores.DEEPSLATE_TR_EMERALD_ORE.get(), block -> createOreDrop(block, Materials.TR_EMERALD.get()));
        add(Ores.SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.SANCTIFICATION_SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.CORRUPTION_SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.FLESHIFICATION_SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.DEEPSLATE_SAPPHIRE_ORE.get(), block -> createOreDrop(block, Materials.SAPPHIRE.get()));
        add(Ores.TR_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.TR_AMETHYST.get()));
        add(Ores.SANCTIFICATION_TR_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.TR_AMETHYST.get()));
        add(Ores.CORRUPTION_TR_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.TR_AMETHYST.get()));
        add(Ores.FLESHIFICATION_TR_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.TR_AMETHYST.get()));
        add(Ores.DEEPSLATE_TR_AMETHYST_ORE.get(), block -> createOreDrop(block, Materials.TR_AMETHYST.get()));
        add(Ores.SANCTIFICATION_EMERALD_ORE.get(), block -> createOreDrop(block, Items.EMERALD));
        add(Ores.CORRUPTION_EMERALD_ORE.get(), block -> createOreDrop(block, Items.EMERALD));
        add(Ores.FLESHIFICATION_EMERALD_ORE.get(), block -> createOreDrop(block, Items.EMERALD));
        add(Ores.SANCTIFICATION_DIAMOND_ORE.get(), block -> createOreDrop(block, Items.DIAMOND));
        add(Ores.CORRUPTION_DIAMOND_ORE.get(), block -> createOreDrop(block, Items.DIAMOND));
        add(Ores.FLESHIFICATION_DIAMOND_ORE.get(), block -> createOreDrop(block, Items.DIAMOND));

        add(Ores.METEORITE_ORE.get(), block -> createOreDrop(block, Materials.RAW_METEORITE.get()));
        add(Ores.EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.DEEPSLATE_EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.SANCTIFICATION_EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.CORRUPTION_EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.FLESHIFICATION_EBONY_ORE.get(), block -> createOreDrop(block, Materials.RAW_EBONY.get()));
        add(Ores.TR_CRIMSON_ORE.get(), block -> createOreDrop(block, Materials.RAW_TR_CRIMSON.get()));
        add(Ores.DEEPSLATE_TR_CRIMSON_ORE.get(), block -> createOreDrop(block, Materials.RAW_TR_CRIMSON.get()));
        add(Ores.DEEPSLATE_COBALT_ORE.get(), block -> createOreDrop(block, Materials.RAW_COBALT.get()));
        add(Ores.DEEPSLATE_PALLADIUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_PALLADIUM.get()));
        add(Ores.DEEPSLATE_MITHRIL_ORE.get(), block -> createOreDrop(block, Materials.RAW_MITHRIL.get()));
        add(Ores.DEEPSLATE_ORICHALCUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_ORICHALCUM.get()));
        add(Ores.DEEPSLATE_ADAMANTITE_ORE.get(), block -> createOreDrop(block, Materials.RAW_ADAMANTITE.get()));
        add(Ores.DEEPSLATE_TITANIUM_ORE.get(), block -> createOreDrop(block, Materials.RAW_TITANIUM.get()));
        add(Ores.CHLOROPHYTE_ORE.get(), block -> createOreDrop(block, Materials.RAW_CHLOROPHYTE.get()));
        add(Ores.HELLSTONE.get(), block -> createOreDrop(block, Materials.RAW_HELLSTONE.get()));
        add(Ores.ASH_HELLSTONE.get(), block -> createOreDrop(block, Materials.RAW_HELLSTONE.get()));
        // endregion ore

        // gems
        // torch
        for (Torches torch : Torches.values()) {
            dropSelf(torch.stand.get());
            dropSelf(torch.wall.get());
        }
        // region natural
        dropSelf(EBONY_COBBLESTONE.get());
        dropSelf(EBONY_SAND.get());
        dropSelf(PEARL_COBBLESTONE.get());
        dropSelf(PEARL_SAND.get());
        dropSelf(TR_CRIMSON_COBBLESTONE.get());
        dropSelf(TR_CRIMSON_SAND.get());
        dropSelf(ASH_BLOCK.get());
        dropOther(TR_CRIMSON_GRASS_BLOCK.get(), Items.DIRT);
        dropOther(CORRUPT_GRASS_BLOCK.get(), Items.DIRT);
        dropOther(HALLOW_GRASS_BLOCK.get(), Items.DIRT);
        dropOther(TR_CRIMSON_STONE.get(), TR_CRIMSON_COBBLESTONE.get());
        dropOther(EBONY_STONE.get(), EBONY_COBBLESTONE.get());
        dropOther(PEARL_STONE.get(), PEARL_COBBLESTONE.get());
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
        dropSelf(BIG_TR_AMETHYST_BLOCK.get());
        dropSelf(BIG_TR_EMERALD_BLOCK.get());

        dropSelf(HARDENED_SAND_BLOCK.get());
        dropSelf(RED_HARDENED_SAND_BLOCK.get());
        dropSelf(EBONY_HARDENED_SAND_BLOCK.get());
        dropSelf(PEARL_HARDENED_SAND_BLOCK.get());
        dropSelf(TR_CRIMSON_HARDENED_SAND_BLOCK.get());

        dropOther(LIFE_CRYSTAL_BLOCK.get(), Materials.LIFE_CRYSTAL.get());
        dropOther(MUSHROOM_GRASS_BLOCK.get(), Items.MUD);

        // 雕像
        dropSelf(STATUE_A.get());
        //chain
        dropSelf(RUBY_CHAIN.get());
        dropSelf(AMBER_CHAIN.get());
        dropSelf(TOPAZ_CHAIN.get());
        dropSelf(EMERALD_CHAIN.get());
        dropSelf(SAPPHIRE_CHAIN.get());
        dropSelf(DIAMOND_CHAIN.get());
        dropSelf(AMETHYST_CHAIN.get());
        dropSelf(SILK_CHAIN.get());
        dropSelf(BONE_CHAIN.get());

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
        for (RegistryObject<Block> decorativeBlocks : DecorativeBlocks.values()) {
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

        dropOther(TR_CRIMSON_MUSHROOM.get(), ModItems.TR_CRIMSON_MUSHROOM.get());
        dropOther(EBONY_MUSHROOM.get(), ModItems.EBONY_MUSHROOM.get());
        dropOther(GLOWING_MUSHROOM.get(), ModItems.GLOWING_MUSHROOM.get()); // TODO: 掉落概率不是100%；掉落蘑菇草种子
        dropOther(LIFE_MUSHROOM.get(), ModItems.LIFE_MUSHROOM.get());
        add(JUNGLE_SPORE.get(), LootTable.lootTable().withPool(LootPool.lootPool()
            .add(LootItem.lootTableItem(ModItems.JUNGLE_SPORE.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))));

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
    private void addHerbDrop(BaseHerbBlock block, Item herb, Item seed) {
        add(block, LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .setBonusRolls(ConstantValue.exactly(0.5f))
                .add(LootItem.lootTableItem(herb))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseHerbBlock.AGE, 2))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(seed)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseHerbBlock.AGE, 2))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(herb))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BaseHerbBlock.AGE, 1)))));
    }
}
