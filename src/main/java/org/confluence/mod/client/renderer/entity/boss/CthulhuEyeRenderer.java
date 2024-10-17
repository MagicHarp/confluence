package org.confluence.mod.client.renderer.entity.boss;

import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.model.entity.CthulhuEyeModel;
import org.confluence.mod.client.post.DIYBlitTarget;
import org.confluence.mod.client.post.DIYShaderInstance;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import org.joml.Vector4f;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

import static com.mojang.blaze3d.platform.GlStateManager.glActiveTexture;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;

public class CthulhuEyeRenderer extends GeoEntityRenderer<CthulhuEye> {
    public static DIYBlitTarget tempBlurTarget;
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
//        ModRenderTypes.Shaders.cthSampler.setUniforms(ump -> {
//            ump.setUniform("samcolor", new Vector4f(0,1,0,1));
//        });
        if(tempBlurTarget==null)  {
            tempBlurTarget=new DIYBlitTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(),
                    false,true,ModRenderTypes.Shaders.conv,shader->{
                shader.setSampler("att",  tempBlurTarget.getColorTextureId());
            });
//            tempBlurTarget=new TextureTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(),false,true);
            tempBlurTarget.setClearColor(0,0,0,0);
        }


        ModRenderTypes.Shaders.cthSampler.setMultiOutTarget(tempBlurTarget);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);


/*
        Minecraft.getInstance().getMainRenderTarget().unbindWrite();

        tempBlurTarget.bindWrite(true);
        tempBlurTarget.setBlitShader(ModRenderTypes.Shaders.conv);
        tempBlurTarget.getBlitShader().setUniforms(ump -> {ump.setUniform("offs", 10);});
        tempBlurTarget.blitToScreen(Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(),false);

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,  Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
*/
    }

    public RenderType getRenderType(CthulhuEye animatable, ResourceLocation texture,
                                     @Nullable MultiBufferSource bufferSource,
                                     float partialTick) {
        return ModRenderTypes.cthRenderType(texture);
    }
}
