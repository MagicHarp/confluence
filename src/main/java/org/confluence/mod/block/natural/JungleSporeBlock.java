package org.confluence.mod.block.natural;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class JungleSporeBlock extends BasePlantBlock{
    private static final ColorPointLight.Template LIGHT = new ColorPointLight.Template(4, 0.7f, 0.8f, 0.16f, 1);
    private static final VoxelShape SHAPE=Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);


    public JungleSporeBlock(){
        super(Set.of(Blocks.GRASS_BLOCK), BlockBehaviour.Properties.copy(Blocks.DANDELION).lightLevel(value -> 4)); //TODO: 丛林草
    }

    public ColorPointLight.Template getColor(){
        return LIGHT;
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext){
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player){
        return new ItemStack(ModItems.JUNGLE_SPORE.get());
    }
}
