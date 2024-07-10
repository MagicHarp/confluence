package org.confluence.mod.mixin.chunk;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.util.IChunkSection;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.level.chunk.LevelChunkSection$1BlockCounter")
public abstract class BlockCounterMixin {
    // 用 @Shadow(aliases="this$0") 甚至编译都编不过，先这么凑合
    private LevelChunkSection this$0; // 开发环境
    private LevelChunkSection f_204440_; // 生产环境
    @Unique IChunkSection confluence$section;

    @Dynamic // 抑制一下报错
    @Inject(method = "accept", at = @At("RETURN"))
    private void accept(BlockState state, int count, CallbackInfo ci){
//        Confluence.LOGGER.info("$0: {} state: {}",confluence$section,state);
        if(confluence$section == null) return;
        if(state.getBlock() instanceof ISpreadable spreadable){
            switch(spreadable.getType()){
                case CRIMSON -> confluence$section.confluence$countCrimson(count);
                case CORRUPT -> confluence$section.confluence$countCorrupt(count);
                case HALLOW -> confluence$section.confluence$countHallow(count);
            }
        }
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constr(CallbackInfo ci){
        if(this$0 != null){
            confluence$section = (IChunkSection) this$0;
        }else if(f_204440_ != null){
            confluence$section = (IChunkSection) f_204440_;
        }
    }
}
