package org.confluence.mod.item.curio.missellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.CuriosUtils;

import java.util.List;

public interface IFlowerBoots {
    static void apply(LivingEntity living) {
        if (living.onGround() && living instanceof ServerPlayer && CuriosUtils.hasCurio(living, IFlowerBoots.class)) {
            ServerLevel serverLevel = (ServerLevel) living.level();
            BlockPos blockPos = living.getOnPosLegacy();
            if (!serverLevel.getBlockState(blockPos).is(ModTags.FLOWER_BOOTS_AVAILABLE)) return;
            BlockPos abovePos = blockPos.above();
            RandomSource random = serverLevel.random;
            for (BlockPos aroundPos : BlockPos.betweenClosed(abovePos.offset(-1, 0, -1), abovePos.offset(1, 0, 1))) {
                if (!serverLevel.getBlockState(aroundPos.below()).is(ModTags.FLOWER_BOOTS_AVAILABLE)) continue;
                if (serverLevel.getBlockState(aroundPos).isCollisionShapeFullBlock(serverLevel, aroundPos)) continue;
                if (random.nextFloat() < 0.3F && serverLevel.getBlockState(aroundPos).isAir()) {
                    List<ConfiguredFeature<?, ?>> list = serverLevel.getBiome(aroundPos).value()
                        .getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) continue;
                    ((RandomPatchConfiguration) list.get(0).config()).feature().value()
                        .place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, aroundPos);
                }
            }
        }
    }

    Component TOOLTIP = Component.translatable("item.confluence.flower_boots.tooltip");
}
