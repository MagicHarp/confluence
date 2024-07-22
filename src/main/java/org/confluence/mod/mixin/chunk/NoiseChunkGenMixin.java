package org.confluence.mod.mixin.chunk;

import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.confluence.mod.util.DynamicBiomeUtils;
import org.confluence.mod.util.IChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseChunkGenMixin {
    @Inject(method = "doCreateBiomes",at = @At("RETURN"))
    private void doCreateBiomes(Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk, CallbackInfo ci){
        for(LevelChunkSection section : pChunk.getSections()){
            if(DynamicBiomeUtils.getTypicalBiome(section, true, null) == null){
                ((IChunkSection) section).confluence$setBackupBiome(section.getBiomes());
            }
        }
    }
}
