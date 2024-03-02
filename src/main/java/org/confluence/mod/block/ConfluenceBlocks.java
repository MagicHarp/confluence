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
