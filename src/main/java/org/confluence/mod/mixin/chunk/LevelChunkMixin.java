package org.confluence.mod.mixin.chunk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.ticks.LevelChunkTicks;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.IChunkSection;
import org.confluence.mod.worldgen.biome.ModBiomes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends ChunkAccess {
    @Shadow
    @Final
    Level level;

    @Unique private static final int BIOME_THRESHOLD = 256;
    @Unique private static Holder<Biome> BIOME_CRIMSON;
    @Unique private static Holder<Biome> BIOME_CORRUPT;
    @Unique private static Holder<Biome> BIOME_HALLOW;
    @Unique private static Holder<Biome> BIOME_PLAINS;
    @Unique private ServerLevel confluence$serverLevel;

    private LevelChunkMixin(ChunkPos pChunkPos, UpgradeData pUpgradeData, LevelHeightAccessor pLevelHeightAccessor, Registry<Biome> pBiomeRegistry, long pInhabitedTime, @Nullable LevelChunkSection[] pSections, @Nullable BlendingData pBlendingData){
        super(pChunkPos, pUpgradeData, pLevelHeightAccessor, pBiomeRegistry, pInhabitedTime, pSections, pBlendingData);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/world/level/chunk/UpgradeData;Lnet/minecraft/world/ticks/LevelChunkTicks;Lnet/minecraft/world/ticks/LevelChunkTicks;J[Lnet/minecraft/world/level/chunk/LevelChunkSection;Lnet/minecraft/world/level/chunk/LevelChunk$PostLoadProcessor;Lnet/minecraft/world/level/levelgen/blending/BlendingData;)V", at = @At("RETURN"))
    private void constr(Level pLevel, ChunkPos pPos, UpgradeData pData, LevelChunkTicks pBlockTicks, LevelChunkTicks pFluidTicks, long pInhabitedTime, LevelChunkSection[] pSections, LevelChunk.PostLoadProcessor pPostLoad, BlendingData pBlendingData, CallbackInfo ci){
        if(level.isClientSide()) return;
        BIOME_CRIMSON = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(ModBiomes.ANOTHER_CRIMSON);
        BIOME_CORRUPT = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(ModBiomes.THE_CORRUPTION);
        BIOME_HALLOW = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(ModBiomes.THE_HALLOW);
        BIOME_PLAINS = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(Biomes.PLAINS);
        confluence$serverLevel = (ServerLevel) level;
    }

    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void setBlock(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir, int i, LevelChunkSection section, boolean flag, int j, int k, int l, BlockState beforeState){
        if(level.isClientSide()) return;

        IChunkSection counter = (IChunkSection) section;
        int crimson = counter.confluence$getCrimson();
        int corrupt = counter.confluence$getCorrupt();
        int hallow = counter.confluence$getHallow();

        // (假设)同时存在400个猩红块和400个腐化块的时候，只要400个神圣块就能完全抵消，邪恶不会相加
        int evil = Math.max(crimson, corrupt);
        crimson -= hallow;
        corrupt -= hallow;
        hallow -= evil;

        if(corrupt >= BIOME_THRESHOLD && corrupt >= crimson){
            infect(section, pPos, BIOME_CORRUPT, true);
        }else if(crimson >= BIOME_THRESHOLD){
            infect(section, pPos, BIOME_CRIMSON, true);
        }else if(hallow >= BIOME_THRESHOLD){
            infect(section, pPos, BIOME_HALLOW, true);
        }else{
            checkBelow(section, pPos);
        }
    }

    private Holder<Biome> checkCross(LevelChunkSection centerSection, BlockPos centerPos){
        Holder<Biome> centerBiome = centerSection.getNoiseBiome(0, 0, 0);
        if(!centerBiome.is(ModTags.SPREADING)){
            return BIOME_PLAINS;
        }
        for(LevelChunk c : List.of(level.getChunkAt(centerPos.south(16)),
            level.getChunkAt(centerPos.north(16)),
            level.getChunkAt(centerPos.east(16)),
            level.getChunkAt(centerPos.west(16)))){
            if(!confluence$serverLevel.getChunkSource().isPositionTicking(c.getPos().toLong())
                || c.getSection(getSectionIndexFromSectionY(SectionPos.blockToSectionCoord(centerPos.getY()))).getNoiseBiome(0, 0, 0).get() != centerBiome.get()){
                return BIOME_PLAINS;
            }
        }
        return centerBiome;
    }

    private void checkBelow(LevelChunkSection selfSection, BlockPos pPos){
        BlockPos belowPos = pPos.offset(0, -16, 0);
        if(level.isOutsideBuildHeight(belowPos.getY())) return;
        LevelChunkSection belowSection = getSection(getSectionIndexFromSectionY(SectionPos.blockToSectionCoord(belowPos.getY())));
        Holder<Biome> targetBiome = checkCross(belowSection, belowPos);
        infect(selfSection, pPos, targetBiome,true);
    }

    private void infect(LevelChunkSection section, BlockPos pPos, Holder<Biome> biome, boolean checkAbove){
        BlockPos abovePos = pPos.above(16);
        Holder<Biome> beforeBiome = section.getNoiseBiome(0, 0, 0);
        if(biome.get() == beforeBiome.get()){
            return;
        }
        section.fillBiomesFromNoise((pX, pY, pZ, pSampler) -> biome, null, 0, 0, 0);
        if(checkAbove && !level.isOutsideBuildHeight(abovePos)){
            infect(getSection(getSectionIndexFromSectionY(SectionPos.blockToSectionCoord(abovePos.getY()))), abovePos, checkCross(section, pPos),false);
        }
        confluence$serverLevel.getChunkSource().chunkMap.resendBiomesForChunks(List.of(this));
    }
}
