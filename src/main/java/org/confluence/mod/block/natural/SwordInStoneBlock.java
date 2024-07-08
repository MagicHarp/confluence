package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.functional.StateProperties;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

public class SwordInStoneBlock extends Block implements CustomModel, CustomItemModel {
    public static final EnumProperty<StateProperties.SwordType> swordType = EnumProperty.create("sword_type", StateProperties.SwordType.class);

    public SwordInStoneBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak());
        this.registerDefaultState(this.getStateDefinition().any().setValue(swordType, StateProperties.SwordType.Null));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        super.getShape(state, world, pos, context);
        if (state.getValue(swordType) == StateProperties.SwordType.Null) {
            return Shapes.or(box(2, 0, 2, 14, 3, 14), box(3, 3, 3, 13, 6, 13));
        } else {
            return Shapes.or(box(2, 0, 2, 14, 3, 14), box(3, 3, 3, 13, 6, 13), box(5, 6, 5, 11, 16, 11));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(swordType);
    }
}
