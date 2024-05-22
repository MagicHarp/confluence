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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorfulTorchBlock extends TorchBlock {
    private final ColorPointLight.Template torchColor;

    public ColorfulTorchBlock(float radius, float r, float g, float b) {
        this(new ColorPointLight.Template(radius, r, g, b, 1.0F));
    }

    public ColorfulTorchBlock(ColorPointLight.Template torchColor) {
        super(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(state -> (int) torchColor.radius), ParticleTypes.FLAME);
        this.torchColor = torchColor;
    }

    public ColorPointLight.Template getColor() {
        return torchColor;
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

    public static class Entity extends BlockEntity {
        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.COLORFUL_TORCH_ENTITY.get(), pPos, pBlockState);
        }

        public static void tick(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
            level.setBlockAndUpdate(blockPos, blockState.cycle(NeedUpdate.UPDATE));
        }
    }
}
