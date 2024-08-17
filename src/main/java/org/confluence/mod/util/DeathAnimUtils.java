package org.confluence.mod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.Confluence;
import org.confluence.mod.mixin.accessor.LivingEntityRendererAccessor;
import org.confluence.mod.mixin.accessor.ModelPartAccessor;
import org.confluence.mod.mixinauxiliary.ILivingEntity;
import org.confluence.mod.mixinauxiliary.IModelPart;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;

import java.lang.reflect.Field;
import java.util.*;

public class DeathAnimUtils {
    public static void moveParts(PoseStack poseStack, Entity animatable, Object bone, float partialTick){
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
            motions = ((ILivingEntity) entity).confluence$getMotionsForPart(p);
            float[] parentMotions = ((IModelPart) (Object) p).confluence$parentOffset();
            List<ModelPart.Cube> cubes = ((ModelPartAccessor) (Object) p).getCubes();
            PartPose deathPose = ((ILivingEntity) entity).confluence$getPoseForPart(p);
            if(deathPose != null){
                p.loadPose(deathPose);
            }
//            p.resetPose();
            if(cubes.size() != 0){
                Map<String, ModelPart> children = ((ModelPartAccessor) (Object) p).getChildren();
                float[] bak = Arrays.copyOf(motions, 6);
                motions = Arrays.copyOf(motions, 6);
                for(int i = 0, motionsLength = motions.length; i < motionsLength; i++){
                    motions[i] -= parentMotions[i];
                }

                float tx = getOffset(entity.deathTime, motions[0], partialTick);
                float footY = motions[1];
                float tz = getOffset(entity.deathTime, motions[2], partialTick);
                float rx = getOffset(entity.deathTime, motions[3], partialTick);
                float ry = getOffset(entity.deathTime, motions[4], partialTick);
                float rz = getOffset(entity.deathTime, motions[5], partialTick);
                float ty = 0;
                double landMotionY = ((ILivingEntity) entity).confluence$landMotionY();
                if(!Double.isNaN(landMotionY)){
                    int landTick = ((ILivingEntity) entity).confluence$landTick();
                    float fallDistance = (float) -(Mth.lerp(partialTick, landMotionY * (landTick - 1), landMotionY * landTick));
                    ty = Math.min(fallDistance, footY);
//                    Confluence.LOGGER.info("{} {}", fallDistance, footY);
//                    ty=-2;
                }
//                Confluence.LOGGER.info("footY={}", footY);
                poseStack.translate(tx, ty, tz);
                poseStack.mulPose(Axis.XP.rotationDegrees(rx));
                poseStack.mulPose(Axis.YP.rotationDegrees(ry));
                poseStack.mulPose(Axis.ZP.rotationDegrees(rz));
                for(ModelPart child : children.values()){
                    ((IModelPart) (Object) child).confluence$parentOffset(bak);
                }

            }
        }
    }

    public static float getOffset(int tick, float max, float partialTick){
        float now = getPosition(tick, max);
        float last = getPosition(tick - 1, max);
        return Mth.wrapDegrees(Mth.lerp(partialTick, last, now));
    }

    public static float[] createOffsets(RandomSource random, Vec3 motion, ModelPart part){
        Vector3f dir = createSpread(random, motion);
        List<ModelPart.Cube> cubes = ((ModelPartAccessor) (Object) part).getCubes();
        float max = Float.NEGATIVE_INFINITY;
        for(ModelPart.Cube cube : cubes){
            max = Math.max(cube.maxY, max);
        }
        return new float[]{
            dir.x,
            max == Float.NEGATIVE_INFINITY ? 0 : Math.max(0, (24 - (max + part.y)) / 16),
            dir.z,
            random.nextFloat() * 60 - 30,
            random.nextFloat() * 540 - 270,
            random.nextFloat() * 60 - 30
        };
    }

    public static float[] createOffsets(RandomSource random, Vec3 motion, float height){
        Vector3f dir = createSpread(random, motion);
        return new float[]{
            dir.x,
            -height / 16,
            dir.z,
            random.nextFloat() * 60 - 30,
            random.nextFloat() * 540 - 270,
            random.nextFloat() * 60 - 30
        };
    }


    private static Vector3f createSpread(RandomSource random, Vec3 motion){
        float yRot = random.nextFloat() * 180 - 90;
        if(motion.equals(Vec3.ZERO)){
            motion = Vec3.ZERO.offsetRandom(random, 1);
        }
        float[] rots = ModUtils.dirToRot(motion);
        rots[0] += yRot;
        return Vec3.directionFromRotation(0, rots[0]).normalize().scale(1.2).toVector3f();
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
        float p0 = 0f;
        float p1 = 1f;
        float p2 = 1f;
        float p3 = 1f;
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
//                    ret.addAll(part.getAllParts().toList());
                    ret.addAll(((IModelPart) (Object) part).confluence$root().getAllParts().toList());
                    break;
//                    float min = Float.POSITIVE_INFINITY;
//                    float max = Float.NEGATIVE_INFINITY;
//                    for(ModelPart.Cube cube : ((ModelPartAccessor) (Object) part).getCubes()){
//                        ModelPart.Polygon[] polygons = ((ModelPart$CubeAccessor) cube).getPolygons();
//                        for(ModelPart.Polygon polygon : polygons){
//                            Vector3f pos = polygon.vertices[0].pos;
//                            max = Math.max(pos.y, max);
//                            min = Math.min(pos.y, min);
//                        }
//                    }
//                    Confluence.LOGGER.info("{} y={} minY={} maxY={}", field.getName(),part.y, min, max);
//                    List<ModelPart.Cube> cubes = ((ModelPartAccessor)(Object) part).getCubes();
//                    if(cubes.size() > 0){
//                        ModelPart.Cube cube = cubes.get(0);
//                        Confluence.LOGGER.info("{} y={} minY={} maxY={}", field.getName(), part.y, cube.minY, cube.maxY);
//                    }
                }
//                else if(ModelPart[].class.isAssignableFrom(field.getType())){
//                    field.setAccessible(true);
//                    ModelPart[] parts = (ModelPart[]) field.get(model);
//                    for(ModelPart part : parts){
//                        ret.add(part);
//                        ret.addAll(part.getAllParts().toList());
//                    }
//                }
            }catch(IllegalAccessException e){
                Confluence.LOGGER.error("field.get: ", e);
            }
        }
        if(ret.isEmpty() && EntityModel.class.isAssignableFrom(finding.getSuperclass())){
            ret.addAll(findAllModelPart(model, finding.getSuperclass()));
        }
        return ret;
    }
}
