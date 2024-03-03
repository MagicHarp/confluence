package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.DecorationLogBlocks;

@SuppressWarnings("unused")
public class ConfluenceBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);

    public static final DecorationLogBlocks EBONY_LOG_BLOCKS = new DecorationLogBlocks("ebony");
    public static final RegistryObject<Block> EBONY_STONE = registerWithItem("ebony_stone", new BaseBlock());
    public static final RegistryObject<Block> EBONY_SANDSTONE = registerWithItem("ebony_sandstone", new BaseBlock());
    public static final RegistryObject<Block> EBONY_COBBLE_STONE = registerWithItem("ebony_cobble_stone", new BaseBlock());
    public static final DecorationLogBlocks PEARL_LOG_BLOCKS = new DecorationLogBlocks("pearl");
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", new BaseBlock());
    public static final RegistryObject<Block> PEARL_SANDSTONE = registerWithItem("pearl_sandstone", new BaseBlock());
    public static final RegistryObject<Block> PEARL_COBBLE_STONE = registerWithItem("pearl_cobble_stone", new BaseBlock());
    public static final DecorationLogBlocks SHADOW_LOG_BLOCKS = new DecorationLogBlocks("shadow");
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE = registerWithItem("another_crimson_stone", new BaseBlock());
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SANDSTONE = registerWithItem("another_crimson_sandstone", new BaseBlock());
    public static final RegistryObject<Block> ANOTHER_CRIMSON_COBBLE_STONE = registerWithItem("another_crimson_cobble_stone", new BaseBlock());
    public static final DecorationLogBlocks PALM_LOG_BLOCKS = new DecorationLogBlocks("palm");
    public static final RegistryObject<Block> BIG_RUBY_BLOCK = registerWithItem("big_ruby_block", new BaseBlock());
    public static final RegistryObject<Block> BIG_AMBER_BLOCK = registerWithItem("big_amber_block", new BaseBlock());
    public static final RegistryObject<Block> BIG_TOPAZ_BLOCK = registerWithItem("big_topaz_block", new BaseBlock());
    public static final RegistryObject<Block> BIG_ANOTHER_EMERALD_BLOCK = registerWithItem("big_another_emerald_block", new BaseBlock());
    public static final RegistryObject<Block> BIG_SAPPHIRE_BLOCK = registerWithItem("big_sapphire_block", new BaseBlock());
    public static final RegistryObject<Block> BIG_ANOTHER_AMETHYST_BLOCK = registerWithItem("big_another_amethyst_block", new BaseBlock());
    public static final RegistryObject<Block> EMBERS_BLOCK = registerWithItem("embers_block", new BaseBlock());


    public enum Ores {
        TIN_ORE("tin_ore", new BaseBlock()),
        TIN_BLOCK("tin_block", new BaseBlock()),
        WOLFRAM_ORE("wolfram_ore", new BaseBlock()),
        WOLFRAM_BLOCK("wolfram_block", new BaseBlock()),
        SILVER_ORE("silver_ore", new BaseBlock()),
        SILVER_BLOCK("silver_block", new BaseBlock()),
        PLATINUM_ORE("platinum_ore", new BaseBlock()),
        PLATINUM_BLOCK("platinum_block", new BaseBlock());

        private final RegistryObject<Block> value;

        Ores(String id, Block ore) {
            this.value = registerWithItem(id, ore);
        }

        public RegistryObject<Block> getValue() {
            return value;
        }

        public Block get() {
            return value.get();
        }
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, B block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, B block, Item.Properties properties) {
        RegistryObject<B> object = BLOCKS.register(id, () -> block);
        ConfluenceItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }
}
