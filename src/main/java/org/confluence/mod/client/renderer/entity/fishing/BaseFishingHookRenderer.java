package org.confluence.mod.client.renderer.entity.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import org.confluence.mod.client.color.IntegerRGB;
import org.confluence.mod.client.model.entity.fishing.BaseFishingHookModel;
import org.confluence.mod.entity.fishing.BaseFishingHook;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class BaseFishingHookRenderer extends EntityRenderer<BaseFishingHook> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/fishing/wood.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/reinforced.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/souls.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/flesh.png"),
        new ResourceLocation(MODID, "textures/entity/fishing/scarab.png")
    };
    private static final IntegerRGB[] COLORS = new IntegerRGB[]{
        IntegerRGB.BLACK,
        IntegerRGB.GRAY,
        IntegerRGB.PURPLE,
        IntegerRGB.LIGHT_RED,
        IntegerRGB.BLUE
    };
    private final BaseFishingHookModel[] MODELS;

    public BaseFishingHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.MODELS = new BaseFishingHookModel[]{
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.WOOD)),
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.REINFORCED)),
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.SOULS)),
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.FLESH)),
            new BaseFishingHookModel(pContext.bakeLayer(BaseFishingHookModel.SCARAB))
        };
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BaseFishingHook pEntity) {
        return TEXTURES[pEntity.getVariant().getId()];
    }

    @Override
    public void render(BaseFishingHook pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        int id = pEntity.getVariant().getId();
        BaseFishingHookModel model = MODELS[id];
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        renderString(entityRenderDispatcher, pEntity, pPartialTicks, pPoseStack, pBuffer, id);
    }

    static void renderString(EntityRenderDispatcher entityRenderDispatcher, BaseFishingHook pEntity, float pPartialTicks, @NotNull PoseStack pPoseStack, MultiBufferSource pBuffer, int id) {
        Player player = pEntity.getPlayerOwner();
        if (player == null) return;
        pPoseStack.pushPose();
        int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack itemstack = player.getMainHandItem();
        if (!itemstack.canPerformAction(ToolActions.FISHING_ROD_CAST)) {
            i = -i;
        }

        float f = player.getAttackAnim(pPartialTicks);
        float f1 = Mth.sin(Mth.sqrt(f) * Mth.PI);
        float f2 = Mth.lerp(pPartialTicks, player.yBodyRotO, player.yBodyRot) * Mth.DEG_TO_RAD;
        double d0 = Mth.sin(f2);
        double d1 = Mth.cos(f2);
        double d2 = i * 0.35;
        double d4, d5, d6;
        float f3;
        if (entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
            double d7 = 960.0 / entityRenderDispatcher.options.fov().get();
            Vec3 vec3 = entityRenderDispatcher.camera.getNearPlane().getPointOnPlane(i * 0.525F, -0.1F);
            vec3 = vec3.scale(d7);
            vec3 = vec3.yRot(f1 * 0.5F);
            vec3 = vec3.xRot(-f1 * 0.7F);
            d4 = Mth.lerp(pPartialTicks, player.xo, player.getX()) + vec3.x;
            d5 = Mth.lerp(pPartialTicks, player.yo, player.getY()) + vec3.y;
            d6 = Mth.lerp(pPartialTicks, player.zo, player.getZ()) + vec3.z;
            f3 = player.getEyeHeight();
        } else {
            d4 = Mth.lerp(pPartialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.8;
            d5 = player.yo + player.getEyeHeight() + (player.getY() - player.yo) * pPartialTicks - 0.45;
            d6 = Mth.lerp(pPartialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.8;
            f3 = player.isCrouching() ? -0.1875F : 0.0F;
        }

        double d9 = Mth.lerp(pPartialTicks, pEntity.xo, pEntity.getX());
        double d10 = Mth.lerp(pPartialTicks, pEntity.yo, pEntity.getY()) + 0.25;
        double d8 = Mth.lerp(pPartialTicks, pEntity.zo, pEntity.getZ());
        float f4 = (float) (d4 - d9);
        float f5 = (float) (d5 - d10) + f3;
        float f6 = (float) (d6 - d8);
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.lineStrip());
        PoseStack.Pose last = pPoseStack.last();

        for (int k = 0; k <= 16; ++k) {
            stringVertex(f4, f5, f6, vertexConsumer, last, k / 16.0F, (k + 1) / 16.0F, COLORS[id]);
        }
        pPoseStack.popPose();
    }

    static void stringVertex(float pX, float pY, float pZ, VertexConsumer pConsumer, PoseStack.Pose pPose, float up, float down, IntegerRGB color) {
        float f = pX * up;
        float f1 = pY * (up * up + up) * 0.5F + 0.25F;
        float f2 = pZ * up;
        float f3 = pX * down - f;
        float f4 = pY * (down * down + down) * 0.5F + 0.25F - f1;
        float f5 = pZ * down - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        pConsumer.vertex(pPose.pose(), f, f1, f2).color(color.red(), color.green(), color.blue(), 255).normal(pPose.normal(), f3 / f6, f4 / f6, f5 / f6).endVertex();
    }
}
