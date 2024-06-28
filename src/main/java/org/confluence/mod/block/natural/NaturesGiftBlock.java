package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.item.curio.CurioItems;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class NaturesGiftBlock extends BasePlantBlock {
    private static final VoxelShape SHAPE=Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public NaturesGiftBlock(){
        super(Set.of(Blocks.GRASS_BLOCK,Blocks.MOSS_BLOCK,Blocks.CLAY));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player){
        return new ItemStack(CurioItems.NATURES_GIFT.get());
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext){
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

}
