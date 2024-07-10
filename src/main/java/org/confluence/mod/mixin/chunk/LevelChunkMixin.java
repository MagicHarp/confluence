package org.confluence.mod.mixin.chunk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.confluence.mod.Confluence;
import org.confluence.mod.util.IChunkSection;
import org.confluence.mod.worldgen.biome.ModBiomes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends ChunkAccess {
    @Shadow
    @Final
    Level level;

    @Unique private static final int BIOME_THRESHOLD = 600;

    private LevelChunkMixin(ChunkPos pChunkPos, UpgradeData pUpgradeData, LevelHeightAccessor pLevelHeightAccessor, Registry<Biome> pBiomeRegistry, long pInhabitedTime, @Nullable LevelChunkSection[] pSections, @Nullable BlendingData pBlendingData){
        super(pChunkPos, pUpgradeData, pLevelHeightAccessor, pBiomeRegistry, pInhabitedTime, pSections, pBlendingData);
    }

    @Inject(method = "setBlockState", at = @At("RETURN"))
    private void setBlock(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir){
        if(level.isClientSide()) return;
        int crimson = 0;
        int corrupt = 0;
        int hallow = 0;
        List<LevelChunkSection> sectionsToInfect = new ArrayList<>();
        List<ChunkAccess> effectedChunks = new ArrayList<>();
        //TODO: 只往外扩一格，但是周围的区块都要检查；把方法独立出来以便其他情况调用
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                LevelChunk chunk = level.getChunkAt(pPos.offset(x * 16, 0, z * 16));
                effectedChunks.add(chunk);
                for(int y = -2; y <= 2; y++){
                    SectionPos sectionPos = SectionPos.of(pPos).offset(x, y, z);
                    if(level.isOutsideBuildHeight(sectionPos.origin())){
                        continue;
                    }
                    IChunkSection section = (IChunkSection) chunk.getSection(getSectionIndexFromSectionY(sectionPos.y()));
                    sectionsToInfect.add((LevelChunkSection) section);
                    if(Math.abs(x) > 1 || Math.abs(y) > 1 || Math.abs(z) > 1){
                        continue;
                    }
                    crimson += section.confluence$getCrimson();
                    corrupt += section.confluence$getCorrupt();
                    hallow += section.confluence$getHallow();
                }
            }
        }
        Confluence.LOGGER.info("chunk={} crimson={} corrupt={} hallow={}", SectionPos.of(pPos), crimson, corrupt, hallow);

        // 同时存在400个猩红块和400个腐化块的时候，只要400个神圣块就能完全抵消，邪恶不会相加
        int evil = Math.max(crimson, corrupt);
        crimson -= hallow;
        corrupt -= hallow;
        hallow -= evil;
        if(corrupt >= BIOME_THRESHOLD && corrupt >= crimson){
            infect(sectionsToInfect,ModBiomes.THE_CORRUPTION);
        }else if(crimson >= BIOME_THRESHOLD){
            infect(sectionsToInfect,ModBiomes.ANOTHER_CRIMSON);
        }else if(hallow >= BIOME_THRESHOLD){
            infect(sectionsToInfect,ModBiomes.THE_HALLOW);
        }else {
            infect(sectionsToInfect,Biomes.PLAINS);
        }
        for(ChunkAccess chunk : effectedChunks){
            chunk.setUnsaved(true);
        }
        ((ServerLevel)level).getChunkSource().chunkMap.resendBiomesForChunks(effectedChunks);
    }

    private void infect(List<LevelChunkSection> sections, ResourceKey<Biome> biome){
        for(LevelChunkSection section : sections){
            section.fillBiomesFromNoise((pX, pY, pZ, pSampler) -> level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(biome), null, 0, 0, 0);
        }

    }
}
