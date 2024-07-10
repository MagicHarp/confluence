package org.confluence.mod.mixin.chunk;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.util.IChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelChunkSection.class)
public abstract class LevelChunkSectionMixin implements IChunkSection {
    @Unique public int confluence$crimsonCount;
    @Unique public int confluence$corruptCount;
    @Unique public int confluence$hallowCount;

    @Override
    public void confluence$countCrimson(int count){
        confluence$crimsonCount += count;
    }

    @Override
    public void confluence$countCorrupt(int count){
        confluence$corruptCount += count;
    }

    @Override
    public void confluence$countHallow(int count){
        confluence$hallowCount += count;
    }

    @Override
    public int confluence$getCrimson(){
        return confluence$crimsonCount;
    }

    @Override
    public int confluence$getCorrupt(){
        return confluence$corruptCount;
    }

    @Override
    public int confluence$getHallow(){
        return confluence$hallowCount;
    }

    // 世界生成的方块放置也是调这个方法
    @Inject(method = "setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;",at = @At("RETURN"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void setBlockState(int pX, int pY, int pZ, BlockState targetState, boolean pUseLocks, CallbackInfoReturnable<BlockState> cir,BlockState beforeState){
        if(beforeState.getBlock() instanceof ISpreadable spreadable){
            switch(spreadable.getType()){
                case CRIMSON -> confluence$crimsonCount--;
                case CORRUPT -> confluence$corruptCount--;
                case HALLOW -> confluence$hallowCount--;
            }
        }
        if(targetState.getBlock() instanceof ISpreadable spreadable){
            switch(spreadable.getType()){
                case CRIMSON -> confluence$crimsonCount++;
                case CORRUPT -> confluence$corruptCount++;
                case HALLOW -> confluence$hallowCount++;
            }
        }
    }
}
