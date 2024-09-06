package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;

import java.util.function.Supplier;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);

    public static final RegistryObject<WorkshopBlock> WORKSHOP = registerWithItem("workshop", WorkshopBlock::new, new Item.Properties());

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block, Item.Properties properties) {
        RegistryObject<B> object = BLOCKS.register(id, block);
        ModItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }
}
