package org.confluence.mod.client.renderer.entity.boss;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.client.model.entity.CthulhuEyeModel;
import org.confluence.mod.client.post.DIYBlitTarget;
import org.confluence.mod.client.post.PostUtil;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CthulhuEyeRenderer extends GeoEntityRenderer<CthulhuEye> {
    //public static DIYBlitTarget tempBlurTarget;
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

        if(entity.stage>1){
            Object obj = entity.getUUID();
            AtomicBoolean exisit = new AtomicBoolean(false);
            PostUtil.blurList.forEach((k,v)->{ if(k==obj) exisit.set(true);});
            DIYBlitTarget target;
            Vector3f dir = entity.getDeltaMovement().toVector3f();
            new Matrix4f().rotate(Minecraft.getInstance().gameRenderer.getMainCamera().rotation().conjugate().normalize()).transformPosition(dir);
            float distance = Minecraft.getInstance().player.distanceTo(entity);
            distance /= Math.min(entity.getDeltaMovement().length(), 1);
            if(!exisit.get()){
                target = new DIYBlitTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(),
                        false,true,ModRenderTypes.Shaders.conv);
                target.setClearColor(0,0,0,0);

                PostUtil.blurList.put(obj,new PostUtil.blurTuple(target,distance,new Vector2f(dir.x,dir.y),true));
            }else{
                target = PostUtil.blurList.get(obj).fbo;

                PostUtil.blurList.get(obj).dir = new Vector2f(dir.x,dir.y);
                PostUtil.blurList.get(obj).distance = distance;
                PostUtil.blurList.get(obj).dirty = true;
            }
            ModRenderTypes.Shaders.cthSampler.setMultiOutTarget(target);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

    }

    public RenderType getRenderType(CthulhuEye animatable, ResourceLocation texture,
                                     @Nullable MultiBufferSource bufferSource,
                                     float partialTick) {
        return ModRenderTypes.cthRenderType(texture);
    }
}
