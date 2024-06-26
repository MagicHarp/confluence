package org.confluence.mod.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.handler.InformationHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MechanicalBlockRenderer<E extends AbstractMechanicalBlock.Entity> implements BlockEntityRenderer<E> {
    private final Int2ObjectMap<float[]> map;

    public MechanicalBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.map = new Int2ObjectOpenHashMap<>();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull E pBlockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(AbstractMechanicalBlock.@NotNull Entity pBlockEntity, Vec3 pCameraPos) {
        return pBlockEntity.getBlockPos().getCenter().multiply(1.0, 0.0, 1.0).closerThan(pCameraPos.multiply(1.0, 0.0, 1.0), getViewDistance());
    }

    @Override
    public void render(@NotNull E pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (InformationHandler.hasMechanicalView()) {
            long gameTime = pBlockEntity.getLevel().getGameTime();
            Vec3 vec31 = pBlockEntity.getBlockPos().getCenter();
            for (Int2ObjectMap.Entry<Set<BlockPos>> entry : pBlockEntity.connectedPoses.int2ObjectEntrySet()) {
                float[] colors = map.computeIfAbsent(entry.getIntKey(), i -> FloatRGB.fromInteger(i).toArray());
                for (BlockPos pos : entry.getValue()) {
                    pPoseStack.pushPose();
                    Vec3 subtract = pos.getCenter().subtract(vec31);
                    Vec3 normalize = subtract.normalize();
                    pPoseStack.translate(0.5, 0.5, 0.5);
                    pPoseStack.mulPose(Axis.YP.rotation(Mth.HALF_PI - (float) Math.atan2(normalize.z, normalize.x)));
                    pPoseStack.mulPose(Axis.XP.rotation((float) Math.acos(normalize.y)));
                    pPoseStack.translate(-0.5, 0.0, -0.5);
                    int height = (int) Math.round(subtract.length());
                    BeaconRenderer.renderBeaconBeam(pPoseStack, pBuffer, BeaconRenderer.BEAM_LOCATION, pPartialTick, 1.0F, gameTime, 0, height, colors, 0.2F, 0.25F);
                    pPoseStack.popPose();
                }
            }
        }
    }
}
