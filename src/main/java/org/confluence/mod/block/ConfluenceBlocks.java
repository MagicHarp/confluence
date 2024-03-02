package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;

@SuppressWarnings("unused")
public class ConfluenceBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);

    public static final RegistryObject<Block> TIN_ORE = registerWithItem("tin_ore", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> WOLFRAM_ORE = registerWithItem("wolfram_ore", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> SILVER_ORE = registerWithItem("silver_ore", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)));
    public static final RegistryObject<Block> PLATINUM_ORE = registerWithItem("platinum_ore", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)));

    private static RegistryObject<Block> registerWithItem(String id, Block block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    private static RegistryObject<Block> registerWithItem(String id, Block block, Item.Properties properties) {
        RegistryObject<Block> object = BLOCKS.register(id, () -> block);
        ConfluenceItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }

    private static RegistryObject<Block> registerWithoutItem(String id, Block block) {
        return BLOCKS.register(id, () -> block);
    }
}
