package org.confluence.mod.block.common;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.shimmer.DemonTorchColor;
import org.confluence.mod.client.shimmer.RainbowTorchColor;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Torches {
    PURPLE_TORCH("purple", 14.0F, 0.9F, 0.0F, 0.9F), // 紫
    YELLOW_TORCH("yellow", 14.0F, 0.9F, 0.9F, 0.0F), // 黄
    BLUE_TORCH("blue", 14.0F, 0.0F, 0.1F, 1.0F), // 蓝
    GREEN_TORCH("green", 14.0F, 0.0F, 1.0F, 0.1F), // 绿
    RED_TORCH("red", 14.0F, 1.0F, 0.1F, 0.1F), // 红
    ORANGE_TORCH("orange", 14.0F, 0.8F, 0.5F, 0.3F), // 橙
    WHITE_TORCH("white", 14.0F, 1.0F, 1.0F, 1.0F), // 白
    ICE_TORCH("ice", 15.0F, 0.7F, 1.5F, 1.5F), // 冰雪
    PINK_TORCH("pink", 14.0F, 1.0F, 0.0F, 1.0F), // 粉
    BONE_TORCH("bone", 14.0F, 0.5F, 0.75F, 1.0F), // 骨头
    ULTRABRIGHT_TORCH("ultrabright", 15.0F, 0.75F, 1.0F, 1.0F), // 超亮
    DEMON_TORCH("demon", DemonTorchColor.INSTANCE), // 恶魔
    CURSED_TORCH("cursed", 14.0F, 0.4F, 1.0F, 0.1F), // 诅咒
    ICHOR_TORCH("ichor", 14.0F, 1.0F, 1.0F, 0.7F), // 灵液
    RAINBOW_TORCH("rainbow", RainbowTorchColor.INSTANCE), // 彩虹
    DESERT_TORCH("desert", 14.0F, 1.0F, 0.85F, 0.55F), // 沙漠
    CORAL_TORCH("coral", 14.0F, 0.25F, 1.0F, 0.8F), // 珊瑚
    CORRUPT_TORCH("corrupt", 14.0F, 0.95F, 0.4F, 1.0F), // 腐化
    CRIMSON_TORCH("crimson", 14.0F, 0.9F, 0.2F, 0.3F), // 猩红
    HALLOWED_TORCH("hallowed", 14.0F, 1.0F, 0.6F, 1.0F),// 神圣
    JUNGLE_TORCH("jungle", 15.0F, -0.25F, 0.45F, -0.1F), // 丛林
    //MUSHROOM_TORCH("mushroom"), // 蘑菇
    //AETHER_TORCH("aether"); // 以太
    ;

    public final RegistryObject<ColorfulTorchBlock> stand;
    public final RegistryObject<ColorfulWallTorchBlock> wall;
    public final RegistryObject<StandingAndWallBlockItem> item;

    Torches(String id, float radius, float r, float g, float b, float a) {
        this.stand = ModBlocks.registerWithoutItem(id + "_torch", () -> new ColorfulTorchBlock(radius, r, g, b, a));
        this.wall = ModBlocks.registerWithoutItem(id + "_wall_torch", () -> new ColorfulWallTorchBlock(radius, r, g, b, a));
        this.item = ModItems.ITEMS.register(id + "_torch", () -> new StandingAndWallBlockItem(stand.get(), wall.get(), new Item.Properties(), Direction.DOWN));
    }

    Torches(String id, float radius, float r, float g, float b) {
        this(id, radius, r, g, b, 1.0F);
    }

    Torches(String id, ColorPointLight.Template torchColor) {
        this.stand = ModBlocks.registerWithoutItem(id + "_torch", () -> new UpdatingColorfulTorchBlock(torchColor));
        this.wall = ModBlocks.registerWithoutItem(id + "_wall_torch", () -> new UpdatingColorfulWallTorchBlock(torchColor));
        this.item = ModItems.ITEMS.register(id + "_torch", () -> new StandingAndWallBlockItem(stand.get(), wall.get(), new Item.Properties(), Direction.DOWN));
    }

    public static void init() {}

    public static class ColorfulTorchBlock extends TorchBlock implements CustomModel, CustomItemModel {
        private final ColorPointLight.Template torchColor;

        public ColorfulTorchBlock(float radius, float r, float g, float b, float a) {
            this(new ColorPointLight.Template(radius, r, g, b, a));
        }

        public ColorfulTorchBlock(ColorPointLight.Template torchColor) {
            super(Properties.copy(Blocks.TORCH).lightLevel(state -> (int) torchColor.radius), ParticleTypes.FLAME);
            this.torchColor = torchColor;
        }

        public ColorPointLight.Template getColor() {
            return torchColor;
        }
    }

    public static class ColorfulWallTorchBlock extends WallTorchBlock implements CustomModel, CustomItemModel {
        private final ColorPointLight.Template torchColor;

        public ColorfulWallTorchBlock(float radius, float r, float g, float b, float a) {
            this(new ColorPointLight.Template(radius, r, g, b, a));
        }

        public ColorfulWallTorchBlock(ColorPointLight.Template torchColor) {
            super(Properties.copy(Blocks.TORCH).lightLevel(state -> (int) torchColor.radius), ParticleTypes.FLAME);
            this.torchColor = torchColor;
        }

        public ColorPointLight.Template getColor() {
            return torchColor;
        }
    }

    public static class Entity extends BlockEntity {
        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.COLORFUL_TORCH_ENTITY.get(), pPos, pBlockState);
        }

        public static void tick(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
            level.setBlockAndUpdate(blockPos, blockState.cycle(UPDATE));
        }
    }

    public static final BooleanProperty UPDATE = BooleanProperty.create("update");

    public static class UpdatingColorfulTorchBlock extends ColorfulTorchBlock implements EntityBlock {
        public UpdatingColorfulTorchBlock(ColorPointLight.Template torchColor) {
            super(torchColor);
            registerDefaultState(stateDefinition.any().setValue(UPDATE, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
            pBuilder.add(UPDATE);
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
            return new Entity(pPos, pState);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
            return pLevel.isClientSide ? null : ModUtils.getTicker(pBlockEntityType, ModBlocks.COLORFUL_TORCH_ENTITY.get(), Entity::tick);
        }
    }

    public static class UpdatingColorfulWallTorchBlock extends ColorfulWallTorchBlock implements EntityBlock {
        public UpdatingColorfulWallTorchBlock(ColorPointLight.Template torchColor) {
            super(torchColor);
            registerDefaultState(stateDefinition.any().setValue(UPDATE, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
            super.createBlockStateDefinition(pBuilder);
            pBuilder.add(UPDATE);
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
            return new Entity(pPos, pState);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
            return pLevel.isClientSide ? null : ModUtils.getTicker(pBlockEntityType, ModBlocks.COLORFUL_TORCH_ENTITY.get(), Entity::tick);
        }
    }
}
