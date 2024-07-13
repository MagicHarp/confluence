package org.confluence.mod.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.functional.DeathChestBlock;
import org.confluence.mod.client.color.FloatRGB;
import org.confluence.mod.client.handler.InformationHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DeathChestBlockRenderer extends ChestRenderer<DeathChestBlock.Entity> { // 死人箱没有上锁变种
    public static final Material DEATH_GOLDEN = chest("death_golden");
    public static final Material DEATH_GOLDEN_LEFT = chest("death_golden_left");
    public static final Material DEATH_GOLDEN_RIGHT = chest("death_golden_right");

    private final Int2ObjectMap<float[]> map;

    public DeathChestBlockRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
        this.map = new Int2ObjectOpenHashMap<>();
    }

    @Override
    public int getViewDistance() {
        return InformationHandler.hasMechanicalView() ? 256 : super.getViewDistance();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull DeathChestBlock.Entity pBlockEntity) {
        return InformationHandler.hasMechanicalView();
    }

    @Override
    public boolean shouldRender(@NotNull DeathChestBlock.Entity pBlockEntity, @NotNull Vec3 pCameraPos) {
        return InformationHandler.hasMechanicalView() && pBlockEntity.getBlockPos().getCenter().multiply(1.0, 0.0, 1.0).closerThan(pCameraPos.multiply(1.0, 0.0, 1.0), getViewDistance());
    }

    @Override
    public void render(DeathChestBlock.@NotNull Entity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        if (InformationHandler.hasMechanicalView()) {
            long gameTime = pBlockEntity.getLevel().getGameTime();
            Vec3 vec31 = pBlockEntity.getBlockPos().getCenter();
            for (Int2ObjectMap.Entry<Set<BlockPos>> entry : pBlockEntity.getConnectedPoses().int2ObjectEntrySet()) {
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

    @Override
    protected @NotNull Material getMaterial(DeathChestBlock.Entity blockEntity, @NotNull ChestType chestType) {
        return switch (blockEntity.variant) {
            default -> chooseMaterial(chestType, DEATH_GOLDEN, DEATH_GOLDEN_LEFT, DEATH_GOLDEN_RIGHT);
        };
    }

    private static Material chest(String pChestName) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Confluence.MODID, "entity/chest/" + pChestName));
    }

    private static Material chooseMaterial(ChestType pChestType, Material pDoubleMaterial, Material pLeftMaterial, Material pRightMaterial) {
        return switch (pChestType) {
            case LEFT -> pLeftMaterial;
            case RIGHT -> pRightMaterial;
            case SINGLE -> pDoubleMaterial;
        };
    }
}
