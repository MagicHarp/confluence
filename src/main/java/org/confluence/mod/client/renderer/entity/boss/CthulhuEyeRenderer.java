package org.confluence.mod.client.renderer.entity.boss;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import org.confluence.mod.client.model.entity.CthulhuEyeModel;
import org.confluence.mod.client.post.postUtil;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import org.joml.Vector4f;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CthulhuEyeRenderer extends GeoEntityRenderer<CthulhuEye> {
    public CthulhuEyeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CthulhuEyeModel());
    }

    @Override
    protected void applyRotations(CthulhuEye animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick){
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);

        var syncRot = animatable.getRot();
        poseStack.mulPose(Axis.XP.rotationDegrees(-syncRot.x));
        poseStack.translate(0,0.5,0);
    }

    @Override
    protected float getDeathMaxRotation(CthulhuEye animatable){
        return 0;
    }

    @Override
    public void render(CthulhuEye entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        ModRenderTypes.Shaders.cth.setUniforms(ump -> {
//            ump.setUniform("samcolor", new Vector4f(0,1,0,1));
//        });
        ModRenderTypes.Shaders.cth.out = true;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    public RenderType getRenderType(CthulhuEye animatable, ResourceLocation texture,
                                     @Nullable MultiBufferSource bufferSource,
                                     float partialTick) {
        return ModRenderTypes.cthRenderType(texture);
    }
}
