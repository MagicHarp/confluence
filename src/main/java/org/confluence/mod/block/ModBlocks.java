package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.entity.ActuatorsBlockEntity;
import org.confluence.mod.block.functional.ActuatorsBlock;
import org.confluence.mod.block.functional.EchoBlock;
import org.confluence.mod.block.natural.ISpreadable;
import org.confluence.mod.block.natural.SpreadingBlock;
import org.confluence.mod.block.natural.SpreadingGrassBlock;
import org.confluence.mod.block.natural.SpreadingSandBlock;
import org.confluence.mod.item.ModItems;

import java.util.function.Supplier;

import static org.confluence.mod.block.WoodSetType.*;

@SuppressWarnings("unused")
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Confluence.MODID);

    // region registries
    // ebony
    public static final DecorationLogBlocks EBONY_LOG_BLOCKS = new DecorationLogBlocks("ebony", EBONY);
    public static final RegistryObject<Block> EBONY_STONE = registerWithItem("ebony_stone", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_SAND = registerWithItem("ebony_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CORRUPT, 0x372B4B, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CORRUPT_GRASS_BLOCK = registerWithItem("corrupt_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    // hallow
    public static final DecorationLogBlocks PEARL_LOG_BLOCKS = new DecorationLogBlocks("pearl", PEARL);
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_SAND = registerWithItem("pearl_sand", () -> new SpreadingSandBlock(ISpreadable.Type.HALLOW, 0xEDD5F6, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HALLOW_GRASS_BLOCK = registerWithItem("hallow_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    // crimson
    public static final DecorationLogBlocks SHADOW_LOG_BLOCKS = new DecorationLogBlocks("shadow", SHADOW);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE = registerWithItem("another_crimson_stone", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SAND = registerWithItem("another_crimson_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CRIMSON, 0x5313E0, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_GRASS_BLOCK = registerWithItem("another_crimson_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    // desert
    public static final DecorationLogBlocks PALM_LOG_BLOCKS = new DecorationLogBlocks("palm", PALM);
    // ash
    public static final DecorationLogBlocks ASH_LOG_BLOCKS = new DecorationLogBlocks("ash", ASH.SET, ASH.TYPE, true, false);
    public static final RegistryObject<Block> ASH_BLOCK = registerWithItem("ash_block", BaseBlock::new);
    // glowing
    public static final RegistryObject<Block> GLOWING_GRASS_BLOCK = registerWithItem("glowing_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.GLOWING, BlockBehaviour.Properties.of()));
    // jewelry
    public static final RegistryObject<Block> BIG_RUBY_BLOCK = registerWithItem("big_ruby_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_AMBER_BLOCK = registerWithItem("big_amber_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_TOPAZ_BLOCK = registerWithItem("big_topaz_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_SAPPHIRE_BLOCK = registerWithItem("big_sapphire_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_ANOTHER_AMETHYST_BLOCK = registerWithItem("big_another_amethyst_block", BaseBlock::new);
    // other environmental stones
    public static final RegistryObject<Block> ANOTHER_POLISHED_GRANITE = registerWithItem("another_polished_granite", BaseBlock::new);
    public static final RegistryObject<Block> POLISHED_MARBLE = registerWithItem("polished_marble", BaseBlock::new);
    // decorative blocks
    public static final DecorationLogBlocks SPOOKY_LOG_BLOCKS = new DecorationLogBlocks("spooky", SPOOKY.SET, SPOOKY.TYPE, false, true);

    // functional block
    public static final RegistryObject<EchoBlock> ECHO_BLOCK = registerWithItem("echo_block", EchoBlock::new);
    public static final RegistryObject<ActuatorsBlock> ACTUATORS = registerWithItem("actuators", ActuatorsBlock::new);
    public static final RegistryObject<BlockEntityType<ActuatorsBlockEntity>> ACTUATORS_ENTITY = BLOCK_ENTITIES.register("actuators_entity", () -> BlockEntityType.Builder.of(ActuatorsBlockEntity::new, ACTUATORS.get()).build(null));

    // endregion registries

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block, Item.Properties properties) {
        RegistryObject<B> object = BLOCKS.register(id, block);
        ModItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }

    public static <B extends Block> RegistryObject<B> registerWithoutItem(String id, Supplier<B> block) {
        return BLOCKS.register(id, block);
    }

    public static void register(IEventBus bus) {
        Ores.init();
        DecorativeBlocks.init();
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}
