package org.confluence.mod.block.common;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.TorchBlock;
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
import org.confluence.mod.util.EnumRegister;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum Torches implements EnumRegister<Torches.ColorfulTorchBlock> {
    PURPLE_TORCH("purple_torch", () -> new ColorfulTorchBlock(14.0F, 0.9F, 0.0F, 0.9F)), // 紫
    YELLOW_TORCH("yellow_torch", () -> new ColorfulTorchBlock(14.0F, 0.9F, 0.9F, 0.0F)), // 黄
    BLUE_TORCH("blue_torch", () -> new ColorfulTorchBlock(14.0F, 0.0F, 0.1F, 1.0F)), // 蓝
    GREEN_TORCH("green_torch", () -> new ColorfulTorchBlock(14.0F, 0.0F, 1.0F, 0.1F)), // 绿
    RED_TORCH("red_torch", () -> new ColorfulTorchBlock(14.0F, 1.0F, 0.1F, 0.1F)), // 红
    ORANGE_TORCH("orange_torch", () -> new ColorfulTorchBlock(14.0F, 0.8F, 0.5F, 0.3F)), // 橙
    WHITE_TORCH("white_torch", () -> new ColorfulTorchBlock(14.0F, 1.0F, 1.0F, 1.0F)), // 白
    ICE_TORCH("ice_torch", () -> new ColorfulTorchBlock(15.0F, 0.7F, 1.5F, 1.5F)), // 冰雪
    PINK_TORCH("pink_torch", () -> new ColorfulTorchBlock(14.0F, 1.0F, 0.0F, 1.0F)), // 粉
    BONE_TORCH("bone_torch", () -> new ColorfulTorchBlock(14.0F, 0.5F, 0.75F, 1.0F)), // 骨头
    ULTRABRIGHT_TORCH("ultrabright_torch", () -> new ColorfulTorchBlock(15.0F, 0.75F, 1.0F, 1.0F)), // 超亮
    DEMON_TORCH("demon_torch", () -> new NeedUpdate(DemonTorchColor.INSTANCE)), // 恶魔
    CURSED_TORCH("cursed_torch", () -> new ColorfulTorchBlock(14.0F, 0.4F, 1.0F, 0.1F)), // 诅咒
    ICHOR_TORCH("ichor_torch", () -> new ColorfulTorchBlock(14.0F, 1.0F, 1.0F, 0.7F)), // 灵液
    RAINBOW_TORCH("rainbow_torch", () -> new NeedUpdate(RainbowTorchColor.INSTANCE)), // 彩虹
    DESERT_TORCH("desert_torch", () -> new ColorfulTorchBlock(14.0F, 1.0F, 0.85F, 0.55F)), // 沙漠
    CORAL_TORCH("coral_torch", () -> new ColorfulTorchBlock(14.0F, 0.25F, 1.0F, 0.8F)), // 珊瑚
    CORRUPT_TORCH("corrupt_torch", () -> new ColorfulTorchBlock(14.0F, 0.95F, 0.4F, 1.0F)), // 腐化
    CRIMSON_TORCH("crimson_torch", () -> new ColorfulTorchBlock(14.0F, 0.9F, 0.2F, 0.3F)), // 猩红
    HALLOWED_TORCH("hallowed_torch", () -> new ColorfulTorchBlock(14.0F, 1.0F, 0.6F, 1.0F)),// 神圣
    JUNGLE_TORCH("jungle_torch", () -> new ColorfulTorchBlock(15.0F, -0.25F, 0.45F, -0.1F)), // 丛林
    //MUSHROOM_TORCH("mushroom_torch"), // 蘑菇
    //AETHER_TORCH("aether_torch"); // 以太
    ;

    private final RegistryObject<ColorfulTorchBlock> value;

    Torches(String id, Supplier<ColorfulTorchBlock> supplier) {
        this.value = ModBlocks.registerWithItem(id, supplier);
    }

    @Override
    public RegistryObject<ColorfulTorchBlock> getValue() {
        return value;
    }

    public static void init() {}

    public static class ColorfulTorchBlock extends TorchBlock {
        private final ColorPointLight.Template torchColor;

        public ColorfulTorchBlock(float radius, float r, float g, float b, float a) {
            this(new ColorPointLight.Template(radius, r, g, b, a));
        }

        public ColorfulTorchBlock(float radius, float r, float g, float b) {
            this(new ColorPointLight.Template(radius, r, g, b, 1.0F));
        }

        public ColorfulTorchBlock(ColorPointLight.Template torchColor) {
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
            level.setBlockAndUpdate(blockPos, blockState.cycle(NeedUpdate.UPDATE));
        }
    }

    public static class NeedUpdate extends ColorfulTorchBlock implements EntityBlock {
        public static final BooleanProperty UPDATE = BooleanProperty.create("update");

        public NeedUpdate(ColorPointLight.Template torchColor) {
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
}
