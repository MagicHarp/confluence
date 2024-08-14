package org.confluence.mod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.mixin.accessor.LivingEntityRendererAccessor;
import org.confluence.mod.mixin.accessor.ModelPartAccessor;
import org.confluence.mod.mixinauxiliary.ILivingEntity;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DeathAnimUtils {
    public static void moveParts(PoseStack poseStack,Entity animatable,Object bone,float partialTick){
        if(!(animatable instanceof LivingEntity entity) || entity.isAlive()) return;
        float[] motions;
        if(bone instanceof GeoBone b){ // TODO: children
            motions = ((ILivingEntity) entity).confluence$getMotionsForBone(b);
            poseStack.translate(getOffset(entity.deathTime, motions[0], partialTick),
                getOffset(entity.deathTime, motions[1], partialTick),
                getOffset(entity.deathTime, motions[2], partialTick));
            poseStack.mulPose(Axis.XP.rotationDegrees(getOffset(entity.deathTime, motions[3], partialTick)));
            poseStack.mulPose(Axis.YP.rotationDegrees(getOffset(entity.deathTime, motions[4], partialTick)));
            poseStack.mulPose(Axis.ZP.rotationDegrees(getOffset(entity.deathTime, motions[5], partialTick)));
        }else if(bone instanceof ModelPart p){
            p.resetPose();
            motions = ((ILivingEntity) entity).confluence$getMotionsForPart(p);
            List<ModelPart.Cube> cubes = ((ModelPartAccessor) (Object) p).getCubes();
            if(cubes.size() != 0){
                ModelPart.Cube cube = cubes.get(0);
                poseStack.translate(getOffset(entity.deathTime, motions[0], partialTick) - p.x / 16,
                    getOffset(entity.deathTime, motions[1] - ((cube.maxY - cube.minY) / 16) + /*entity.getEyeHeight()*/entity.getDimensions(Pose.STANDING).height, partialTick),
                    getOffset(entity.deathTime, motions[2], partialTick) - p.z / 16);
                poseStack.mulPose(Axis.XP.rotationDegrees(getOffset(entity.deathTime, motions[3], partialTick)));
                poseStack.mulPose(Axis.YP.rotationDegrees(getOffset(entity.deathTime, motions[4], partialTick)));
                poseStack.mulPose(Axis.ZP.rotationDegrees(getOffset(entity.deathTime, motions[5], partialTick)));
            }
        }
    }

    public static float getOffset(int tick, float max, float partialTick){
        float now = getPosition(tick, max);
        float last = getPosition(tick - 1, max);
        return Mth.wrapDegrees(Mth.lerp(partialTick, last, now));
    }

    public static float[] createOffsets(RandomSource random, Vec3 motion, double height){
        // [-90,-45] 并 [45,90]
        float yRot = random.nextBoolean() ? random.nextFloat() * 45 + 45 : random.nextFloat() * 45 - 90;
        float xRot = random.nextFloat() * 90 - 45;
        Vector3f dir = motion.normalize().scale(2).xRot(xRot).yRot(yRot).toVector3f();
        return new float[]{
            dir.x,
            (float) -height / 16,
            dir.z,
            random.nextFloat() * 90 - 45,
            random.nextFloat() * 360 - 180,
            random.nextFloat() * 90 - 45
        };
    }

    private static float cubicBezier(float t, float p0, float p1, float p2, float p3){
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        return uuu * p0 + 3 * uu * t * p1 + 3 * u * tt * p2 + ttt * p3;
    }

    public static float getPosition(int tick, float max){
        if(tick < 0) tick = 0;
        if(tick > 20) tick = 20;
        float t = tick / 20.0f;

        // 贝塞尔控制点
        float p0 = 0.0f;
        float p1 = 1f;
        float p2 = 1.0f;
        float p3 = 1.0f;
        float bezierValue = cubicBezier(t, p0, p1, p2, p3);
        return bezierValue * max;
    }

    public static List<ModelPart> findAllModelPart(LivingEntityRenderer<?, ?> renderer){
        EntityModel<?> model = renderer.getModel();
        List<ModelPart> ret = findAllModelPart(model, model.getClass());
        for(RenderLayer<LivingEntity, EntityModel<LivingEntity>> layer : ((LivingEntityRendererAccessor) renderer).getLayers()){
            for(Field field : layer.getClass().getDeclaredFields()){
                if(EntityModel.class.isAssignableFrom(field.getType())){
                    field.setAccessible(true);
                    try{
                        EntityModel<?> layerModel = (EntityModel<?>) field.get(layer);
                        ret.addAll(findAllModelPart(layerModel, layerModel.getClass()));
                    }catch(IllegalAccessException e){
                        Confluence.LOGGER.error("field.get: ", e);
                    }
                }
            }

        }
        return ret;
    }

    public static List<ModelPart> findAllModelPart(EntityModel<?> model, Class<?> finding){
        List<ModelPart> ret = new ArrayList<>();
        for(Field field : finding.getDeclaredFields()){
            try{
                if(ModelPart.class.isAssignableFrom(field.getType())){
                    field.setAccessible(true);
                    ModelPart part = (ModelPart) field.get(model);
                    ret.add(part);
                    ret.addAll(part.getAllParts().toList());
                }else if(ModelPart[].class.isAssignableFrom(field.getType())){
                    field.setAccessible(true);
                    ModelPart[] parts = (ModelPart[]) field.get(model);
                    for(ModelPart part : parts){
                        ret.add(part);
                        ret.addAll(part.getAllParts().toList());
                    }
                }
            }catch(IllegalAccessException e){
                Confluence.LOGGER.error("field.get: ", e);
            }
        }
        if(EntityModel.class.isAssignableFrom(finding.getSuperclass())){
            ret.addAll(findAllModelPart(model, finding.getSuperclass()));
        }
        return ret;
    }
}
