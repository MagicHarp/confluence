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
import org.confluence.mod.client.post.effect.MotionBlur;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

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
        MotionBlur mb = PostUtil.motionBlur;
        if(mb!=null && entity.stage>1 && entity.getDeltaMovement().length() > 0.3){
            Object obj = entity.getUUID();
            AtomicBoolean exisit = new AtomicBoolean(false);
            mb.blurList.forEach((k,v)->{ if(k==obj) exisit.set(true);});
            DIYBlitTarget target;
            // 运动方向
            Vector3f dir = entity.getDeltaMovement().toVector3f();
            // 旋转到相机坐标系
            new Matrix4f().rotate(Minecraft.getInstance().gameRenderer.getMainCamera().rotation().conjugate().normalize()).transformPosition(dir);
            // 长度与距离相关
            float distance = Minecraft.getInstance().player.distanceTo(entity);
            // 长度与移速相关
            distance /= (float) Math.min(entity.getDeltaMovement().length(), 1);
            // 长度与角度相关
            //distance *= Math.max(Math.cos((dir.angle(new Vector3f(1,0,0)))),0.3f);
            if(!exisit.get()){
                target = new DIYBlitTarget(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(),
                        false,true,ModRenderTypes.Shaders.motion_blur);
                target.setClearColor(0,0,0,0);
                target.clear(true);
                mb.blurList.put(obj,new MotionBlur.blurTuple(target,distance,new Vector2f(dir.x,dir.y),true));
            }else{
                target = mb.blurList.get(obj).fbo;

                mb.blurList.get(obj).dir = new Vector2f(dir.x,dir.y);
                mb.blurList.get(obj).distance = distance;
                mb.blurList.get(obj).dirty = true;
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
