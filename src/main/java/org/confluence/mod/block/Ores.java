package org.confluence.mod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.cloaked.StepRevealingBlock;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.common.MeteoriteOre;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Ores implements EnumRegister<Block> {
    TIN_ORE("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    DEEPSLATE_TIN_ORE("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE))),
    RAW_TIN_BLOCK("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK))),
    TIN_BLOCK("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),

    LEAD_ORE("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_LEAD_ORE("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    RAW_LEAD_BLOCK("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    LEAD_BLOCK("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    SILVER_ORE("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_SILVER_ORE("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    RAW_SILVER_BLOCK("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    SILVER_BLOCK("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    TUNGSTEN_ORE("tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_TUNGSTEN_ORE("deepslate_tungesten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    RAW_TUNGSTEN_BLOCK("raw_tungsten_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    TUNGSTEN_BLOCK("tungsten_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    PLATINUM_ORE("platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    DEEPSLATE_PLATINUM_ORE("deepslate_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE))),
    RAW_PLATINUM_BLOCK("raw_platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK))),
    PLATINUM_BLOCK("platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK))),
    //  陨铁
    METEORITE_ORE("meteorite_ore", MeteoriteOre::new),

    //  钴
    DEEPSLATE_COBALT_ORE("deepslate_cobalt_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
    RAW_COBALT_BLOCK("raw_cobalt_block", () -> new BaseBlock()),
    COBALT_BLOCK("cobalt_block", () -> new BaseBlock()),
    //  钯金
    DEEPSLATE_PALLADIUM_ORE("deepslate_palladium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
    RAW_PALLADIUM_BLOCK("raw_palladium_block", () -> new BaseBlock()),
    PALLADIUM_BLOCK("palladium_block", () -> new BaseBlock()),
    //  秘银
    DEEPSLATE_MITHRIL_ORE("deepslate_mithril_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
    RAW_MITHRIL_BLOCK("raw_mithril_block", () -> new BaseBlock()),
    MITHRIL_BLOCK("mithril_block", () -> new BaseBlock()),
    //  山铜
    DEEPSLATE_ORICHALCUM_ORE("deepslate_orichalcum_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
    RAW_ORICHALCUM_BLOCK("raw_orichalcum_block", () -> new BaseBlock()),
    ORICHALCUM_BLOCK("orichalcum_block", () -> new BaseBlock()),
    //  精金
    DEEPSLATE_ADAMANTITE_ORE("deepslate_adamantite_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
    RAW_ADAMANTITE_BLOCK("raw_adamantite_block", () -> new BaseBlock()),
    ADAMANTITE_BLOCK("adamantite_block", () -> new BaseBlock()),
    //  钛金
    DEEPSLATE_TITANIUM_ORE("deepslate_titanium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
    RAW_TITANIUM_BLOCK("raw_titanium_block", () -> new BaseBlock()),
    TITANIUM_BLOCK("titanium_block", () -> new BaseBlock());

    private final RegistryObject<Block> value;

    Ores(String id, Supplier<Block> ore) {
        this.value = ConfluenceBlocks.registerWithItem(id, ore);
    }

    @Override
    public RegistryObject<Block> getValue() {
        return value;
    }

    static void init() {
        Confluence.LOGGER.info("Registering ores");
    }
}
