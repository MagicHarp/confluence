package org.confluence.mod.block.natural.herbs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DeathWeed extends BaseHerbBlock {
    public static final IntegerProperty PROP_LIGHT = IntegerProperty.create("level", 0, 5);

    public DeathWeed(){
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks().lightLevel(value -> value.getValue(AGE) == MAX_AGE ? value.getValue(PROP_LIGHT) : 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(PROP_LIGHT);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId(){
        return ModItems.DEATHWEED_SEED.get();
    }

    @Override
    public boolean canBloom(ServerLevel world, BlockState state){
        return world.isNight() && (world.getMoonPhase() == 0 || ConfluenceData.get(world).getSpecificMoon().isBloodyMoon());
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        if(getAge(pState) != MAX_AGE) return;
        int r = pRandom.nextInt(200);
        int brightness;
        if(r < 10){
            pLevel.setBlockAndUpdate(pPos, pState.setValue(PROP_LIGHT, 5));
            Vec3 center = pPos.getCenter().offsetRandom(pRandom,0.6f);
            pLevel.addParticle(new DustParticleOptions(new Vector3f(1, 0, 1), 1), center.x, center.y / 2, center.z, 10,10,10);
            center = pPos.getCenter().offsetRandom(pRandom,0.6f);
            pLevel.addParticle(new DustParticleOptions(new Vector3f(1, 0, 1), 1), center.x, center.y / 2, center.z, 10,10,10);
        }else if(r < 160 && (brightness = pState.getValue(PROP_LIGHT)) > 0){
            pLevel.setBlockAndUpdate(pPos, pState.setValue(PROP_LIGHT, brightness - 1));
        }
    }
}
