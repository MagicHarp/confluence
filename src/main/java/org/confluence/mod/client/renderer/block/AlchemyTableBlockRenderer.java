package org.confluence.mod.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.block.crafting.AlchemyTableBlock;
import org.confluence.mod.client.color.FloatRGBA;
import org.confluence.mod.util.ModUtils;
import org.joml.Quaternionf;

public class AlchemyTableBlockRenderer implements BlockEntityRenderer<AlchemyTableBlock.Entity> {
    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");

    public AlchemyTableBlockRenderer(BlockEntityRendererProvider.Context context){}

    @Override
    public void render(AlchemyTableBlock.Entity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, 1, 0);
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();

        var consumer = pBuffer.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false));

        if (pBlockEntity.firstColor != 0){
            ModUtils.renderCube(pPoseStack, consumer, new FloatRGBA(((pBlockEntity.firstColor >> 16) & 0xff) / 255f,
                    ((pBlockEntity.firstColor >> 8) & 0xff) / 255f,
                    ((pBlockEntity.firstColor) & 0xff) / 255f, 0.05F),0.25F,0.4F,0.25F,0,0);
        }
        if (pBlockEntity.secondColor != 0){
            pPoseStack.translate(0.75, 0, 0.75);
            ModUtils.renderCube(pPoseStack, consumer, new FloatRGBA(((pBlockEntity.secondColor >> 16) & 0xff) / 255f,
                    ((pBlockEntity.secondColor >> 8) & 0xff) / 255f,
                    ((pBlockEntity.secondColor) & 0xff) / 255f, 0.05F),0.25F,0.4F,0.25F,0,0);
        }
        if (pBlockEntity.operator != 0){
            pPoseStack.translate(0.5, 0.5, 0.5);
            pPoseStack.rotateAround(new Quaternionf().rotateY((float) Math.PI), 0, 1, 0);
            itemRenderer.render(new ItemStack(AlchemyTableBlock.getItemByOperator(pBlockEntity.operator)),
                    ItemDisplayContext.FIXED, true, pPoseStack, pBuffer, 0xF000F0, pPackedOverlay,
                    itemRenderer.getModel(new ItemStack(AlchemyTableBlock.getItemByOperator(pBlockEntity.operator))
                            , null, null, 0));
        }

        pPoseStack.popPose();
    }
}
