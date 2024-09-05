package org.confluence.mod.common.init.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.block.HellStoneBlock;
import org.confluence.mod.common.block.MeteoriteOre;

import java.util.function.Supplier;

public class ModOreBlocks {
    //TODO 未附带Tag标记

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Confluence.MODID);


    public static final Supplier<Block> SANCTIFICATION_COAL_ORE = copyBlockRegister("sanctification_coal_ore",Blocks.COAL_ORE);
    public static final Supplier<Block> CORRUPTION_COAL_ORE = copyBlockRegister("corruption_coal_ore", Blocks.COAL_ORE);
    public static final Supplier<Block> FLESHIFICATION_COAL_ORE = copyBlockRegister("fleshification_coal_ore",Blocks.COAL_ORE);

    public static final Supplier<Block> SANCTIFICATION_COPPER_ORE = copyBlockRegister("sanctification_copper_ore",Blocks.COPPER_ORE);
    public static final Supplier<Block> CORRUPTION_COPPER_ORE = copyBlockRegister("corruption_copper_ore", Blocks.COPPER_ORE);
    public static final Supplier<Block> FLESHIFICATION_COPPER_ORE = copyBlockRegister("fleshification_copper_ore", Blocks.COPPER_ORE);

    public static final Supplier<Block> TIN_ORE = copyBlockRegister("tin_ore", Blocks.COPPER_ORE);
    public static final Supplier<Block> DEEPSLATE_TIN_ORE = copyBlockRegister("deepslate_tin_ore", Blocks.DEEPSLATE_COPPER_ORE);
    public static final Supplier<Block> SANCTIFICATION_TIN_ORE = copyBlockRegister("sanctification_tin_ore", Blocks.COPPER_ORE);
    public static final Supplier<Block> CORRUPTION_TIN_ORE = copyBlockRegister("corruption_tin_ore", Blocks.COPPER_ORE);
    public static final Supplier<Block> FLESHIFICATION_TIN_ORE = copyBlockRegister("fleshification_tin_ore", Blocks.COPPER_ORE);

    public static final Supplier<Block> RAW_TIN_BLOCK = copyBlockRegister("raw_tin_block", Blocks.RAW_COPPER_BLOCK);
    public static final Supplier<Block> TIN_BLOCK = copyBlockRegister("tin_block", Blocks.COPPER_BLOCK);
    public static final Supplier<Block> SANCTIFICATION_IRON_ORE = copyBlockRegister("sanctification_iron_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_IRON_ORE = copyBlockRegister("corruption_iron_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_IRON_ORE = copyBlockRegister("fleshification_iron_ore", Blocks.IRON_ORE);

    public static final Supplier<Block> LEAD_ORE = copyBlockRegister("lead_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_LEAD_ORE = copyBlockRegister("deepslate_lead_ore", Blocks.DEEPSLATE_IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_LEAD_ORE = copyBlockRegister("sanctification_lead_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_LEAD_ORE = copyBlockRegister("corruption_lead_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_LEAD_ORE = copyBlockRegister("fleshification_lead_ore", Blocks.IRON_ORE);

    public static final Supplier<Block> RAW_LEAD_BLOCK = copyBlockRegister("raw_lead_block", Blocks.RAW_IRON_BLOCK);
    public static final Supplier<Block> LEAD_BLOCK = copyBlockRegister("lead_block", Blocks.IRON_BLOCK);

    public static final Supplier<Block> SILVER_ORE = copyBlockRegister("silver_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_SILVER_ORE = copyBlockRegister("deepslate_silver_ore", Blocks.DEEPSLATE_IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_SILVER_ORE = copyBlockRegister("sanctification_silver_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_SILVER_ORE = copyBlockRegister("corruption_silver_ore", Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_SILVER_ORE = copyBlockRegister("fleshification_silver_ore", Blocks.IRON_ORE);

    public static final Supplier<Block> RAW_SILVER_BLOCK = copyBlockRegister("raw_silver_block", Blocks.RAW_IRON_BLOCK);
    public static final Supplier<Block> SILVER_BLOCK = copyBlockRegister("silver_block", Blocks.IRON_BLOCK);

    public static final Supplier<Block> METEORITE_ORE = simpleBlockRegister("meteorite_ore", MeteoriteOre::new);
    public static final Supplier<Block> RAW_METEORITE_BLOCK = simpleBlockRegister("raw_meteorite_block", MeteoriteOre::new);
    public static final Supplier<Block> METEORITE_BLOCK = simpleBlockRegister("meteorite_block", MeteoriteOre::new);

    public static final Supplier<Block> STURDY_FOSSIL_BLOCK = copyBlockRegister("sturdy_fossil_block",Blocks.DIAMOND_BLOCK);

    public static final Supplier<Block> SANCTIFICATION_EMERALD_ORE = copyBlockRegister("sanctification_emerald_ore",Blocks.EMERALD_ORE);
    public static final Supplier<Block> CORRUPTION_EMERALD_ORE = copyBlockRegister("corruption_emerald_ore",Blocks.EMERALD_ORE);
    public static final Supplier<Block> FLESHIFICATION_EMERALD_ORE = copyBlockRegister("fleshification_emerald_ore",Blocks.EMERALD_ORE);

    public static final Supplier<Block> SANCTIFICATION_DIAMOND_ORE = copyBlockRegister("sanctification_diamond_ore",Blocks.DIAMOND_ORE);
    public static final Supplier<Block> CORRUPTION_DIAMOND_ORE = copyBlockRegister("corruption_diamond_ore",Blocks.DIAMOND_ORE);
    public static final Supplier<Block> FLESHIFICATION_DIAMOND_ORE = copyBlockRegister("fleshification_diamond_ore",Blocks.DIAMOND_ORE);

    public static final Supplier<Block> RUBY_ORE = copyBlockRegister("ruby_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_RUBY_ORE = copyBlockRegister("deepslate_ruby_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_RUBY_ORE = copyBlockRegister("sanctification_ruby_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_RUBY_ORE = copyBlockRegister("corruption_ruby_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_RUBY_ORE = copyBlockRegister("fleshification_ruby_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> TOPAZ_ORE = copyBlockRegister("topaz_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_TOPAZ_ORE = copyBlockRegister("deepslate_topaz_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_TOPAZ_ORE = copyBlockRegister("sanctification_topaz_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_TOPAZ_ORE = copyBlockRegister("corruption_topaz_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_TOPAZ_ORE = copyBlockRegister("fleshification_topaz_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> AMBER_ORE = copyBlockRegister("amber_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_AMBER_ORE = copyBlockRegister("deepslate_amber_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_AMBER_ORE = copyBlockRegister("sanctification_amber_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_AMBER_ORE = copyBlockRegister("corruption_amber_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_AMBER_ORE = copyBlockRegister("fleshification_amber_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> TR_AMETHYST_ORE = copyBlockRegister("tr_amethyst_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_TR_AMETHYST_ORE = copyBlockRegister("deepslate_tr_amethyst_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_TR_AMETHYST_ORE = copyBlockRegister("sanctification_tr_amethyst_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_TR_AMETHYST_ORE = copyBlockRegister("corruption_tr_amethyst_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_TR_AMETHYST_ORE = copyBlockRegister("fleshification_tr_amethyst_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> SAPPHIRE_ORE = copyBlockRegister("sapphire_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_SAPPHIRE_ORE = copyBlockRegister("deepslate_sapphire_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_SAPPHIRE_ORE = copyBlockRegister("sanctification_sapphire_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_SAPPHIRE_ORE = copyBlockRegister("corruption_sapphire_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_SAPPHIRE_ORE = copyBlockRegister("fleshification_sapphire_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> SANCTIFICATION_LAPIS_ORE = copyBlockRegister("sanctification_lapis_ore",Blocks.LAPIS_ORE);
    public static final Supplier<Block> CORRUPTION_LAPIS_ORE = copyBlockRegister("corruption_lapis_ore",Blocks.LAPIS_ORE);
    public static final Supplier<Block> FLESHIFICATION_LAPIS_ORE = copyBlockRegister("fleshification_lapis_ore",Blocks.LAPIS_ORE);

    public static final Supplier<Block> EBONY_ORE = copyBlockRegister("ebony_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_EBONY_ORE = copyBlockRegister("deepslate_ebony_ore",Blocks.DEEPSLATE_IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_EBONY_ORE = copyBlockRegister("sanctification_ebony_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_EBONY_ORE = copyBlockRegister("corruption_ebony_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_EBONY_ORE = copyBlockRegister("fleshification_ebony_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> EBONY_BLOCK = copyBlockRegister("ebony_block",Blocks.RAW_IRON_BLOCK);
    public static final Supplier<Block> RAW_EBONY_BLOCK = copyBlockRegister("raw_ebony_block",Blocks.IRON_BLOCK);

    public static final Supplier<Block> TR_CRIMSON_ORE = copyBlockRegister("tr_crimson_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> DEEPSLATE_TR_CRIMSON_ORE = copyBlockRegister("deepslate_tr_crimson_ore",Blocks.DEEPSLATE_IRON_ORE);
    public static final Supplier<Block> SANCTIFICATION_TR_CRIMSON_ORE = copyBlockRegister("sanctification_tr_crimson_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> CORRUPTION_TR_CRIMSON_ORE = copyBlockRegister("corruption_tr_crimson_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> FLESHIFICATION_TR_CRIMSON_ORE = copyBlockRegister("fleshification_tr_crimson_ore",Blocks.IRON_ORE);

    public static final Supplier<Block> RAW_TR_CRIMSON_BLOCK = copyBlockRegister("raw_tr_crimson_block",Blocks.IRON_BLOCK);
    public static final Supplier<Block> TR_CRIMSON_BLOCK = copyBlockRegister("tr_crimson_block",Blocks.RAW_IRON_BLOCK);

    public static final Supplier<Block> HALLOWED_BLOCK = copyBlockRegister("hallowed_block",Blocks.IRON_BLOCK);

    public static final Supplier<Block> CHLOROPHYTE_ORE = copyBlockRegister("chlorophyte_ore",Blocks.IRON_ORE);
    public static final Supplier<Block> RAW_CHLOROPHYTE_BLOCK = copyBlockRegister("raw_chlorophyte_block",Blocks.IRON_BLOCK);
    public static final Supplier<Block> CHLOROPHYTE_BLOCK = copyBlockRegister("chlorophyte_block",Blocks.RAW_IRON_BLOCK);
    
    public static final Supplier<Block> SHROOMITE_BLOCK = copyBlockRegister("shroomite_block",Blocks.IRON_BLOCK);

    public static final Supplier<Block> SPECTRE_BLOCK = copyBlockRegister("spectre_block",Blocks.IRON_BLOCK);

    public static final Supplier<Block> RAW_LUMINITE_BLOCK = copyBlockRegister("raw_luminite_block",Blocks.IRON_BLOCK);
    public static final Supplier<Block> LUMINITE_BLOCK = copyBlockRegister("luminite_block",Blocks.RAW_IRON_BLOCK);
    
    

    public static final Supplier<Block> HELLSTONE = simpleBlockRegister("hellstone", HellStoneBlock::new);
    public static final Supplier<Block> ASH_HELLSTONE = simpleBlockRegister("ash_hellstone", HellStoneBlock::new);
    public static final Supplier<Block> RAW_HELLSTONE_BLOCK = simpleBlockRegister("raw_hellstone_block", BlockBehaviour.Properties.of().requiresCorrectToolForDrops());
    public static final Supplier<Block> HELLSTONE_BLOCK = simpleBlockRegister("hellstone_block", BlockBehaviour.Properties.of().requiresCorrectToolForDrops());


//    TODO 隐藏矿物方块
//    public static final Supplier<Block> DEEPSLATE_COBALT_ORE = simpleBlockRegister("deepslate_cobalt_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_COBALT_BLOCK = simpleBlockRegister("raw_cobalt_block");
    public static final Supplier<Block> COBALT_BLOCK = simpleBlockRegister("cobalt_block");

//    public static final Supplier<Block> DEEPSLATE_PALLADIUM_ORE = simpleBlockRegister("deepslate_palladium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_PALLADIUM_BLOCK = simpleBlockRegister("raw_palladium_block");
    public static final Supplier<Block> PALLADIUM_BLOCK = simpleBlockRegister("palladium_block");

//    public static final Supplier<Block> DEEPSLATE_MITHRIL_ORE = simpleBlockRegister("deepslate_mithril_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_MITHRIL_BLOCK = simpleBlockRegister("raw_mithril_block");
    public static final Supplier<Block> MITHRIL_BLOCK = simpleBlockRegister("mithril_block");

//    public static final Supplier<Block> DEEPSLATE_ORICHALCUM_ORE = simpleBlockRegister("deepslate_orichalcum_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_ORICHALCUM_BLOCK = simpleBlockRegister("raw_orichalcum_block");
    public static final Supplier<Block> ORICHALCUM_BLOCK = simpleBlockRegister("orichalcum_block");

//    public static final Supplier<Block> DEEPSLATE_ADAMANTITE_ORE = simpleBlockRegister("deepslate_adamantite_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_ADAMANTITE_BLOCK = simpleBlockRegister("raw_adamantite_block");
    public static final Supplier<Block> ADAMANTITE_BLOCK = simpleBlockRegister("adamantite_block");

//    public static final Supplier<Block> DEEPSLATE_TITANIUM_ORE = simpleBlockRegister("deepslate_titanium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RAW_TITANIUM_BLOCK = simpleBlockRegister("raw_titanium_block");
    public static final Supplier<Block> TITANIUM_BLOCK = simpleBlockRegister("titanium_block");



    private static Supplier<Block> simpleBlockRegister(String name) {
        return BLOCKS.registerSimpleBlock(name,BlockBehaviour.Properties.of());
    }
    private static Supplier<Block> simpleBlockRegister(String name,Supplier<Block> blockSupplier) {
        return BLOCKS.register(name,blockSupplier);
    }
    private static Supplier<Block> simpleBlockRegister(String name,BlockBehaviour.Properties props) {
        return BLOCKS.registerSimpleBlock(name,props);
    }
    private static Supplier<Block> copyBlockRegister(String newName, Block originalBlock) {
        return BLOCKS.registerSimpleBlock(newName,BlockBehaviour.Properties.ofFullCopy(originalBlock));
    }

}
