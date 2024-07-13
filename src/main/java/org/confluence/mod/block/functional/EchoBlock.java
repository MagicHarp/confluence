package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.StateProperties;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EchoBlock extends HalfTransparentBlock implements CustomModel, CustomItemModel {
    public EchoBlock() {
        super(BlockBehaviour.Properties.of().isSuffocating((blockState, blockGetter, blockPos) -> false).noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(StateProperties.VISIBLE, false));
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (context instanceof EntityCollisionContext context1 && context1.getEntity() instanceof Player player) {
            Optional<ItemStack> curio = CuriosUtils.findCurio(player, CurioItems.SPECTRE_GOGGLES.get());
            if (curio.isPresent()) {
                ItemStack itemStack = curio.get();
                if (itemStack.getTag() != null && itemStack.getTag().getBoolean("enable")) {
                    return Shapes.empty();
                }
                return Shapes.block();
            }
        }
        return Shapes.block();
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        return getCollisionShape(blockState, blockGetter, blockPos, context);
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(StateProperties.VISIBLE);
    }

    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return 1.0F;
    }
}
