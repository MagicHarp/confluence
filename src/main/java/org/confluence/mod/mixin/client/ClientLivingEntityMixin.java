package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.confluence.mod.client.handler.GravitationHandler;
import org.confluence.mod.client.handler.StepStoolHandler;
import org.confluence.mod.mixin.accessor.EntityAccessor;
import org.confluence.mod.mixinauxiliary.IClientLivingEntity;
import org.confluence.mod.mixinauxiliary.SelfGetter;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.c2s.FallDistancePacketC2S;
import org.confluence.mod.util.DeathAnimOptions;
import org.confluence.mod.util.DeathAnimUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class ClientLivingEntityMixin extends Entity implements IClientLivingEntity, SelfGetter<LivingEntity> {
    // 位移，旋转
    @Unique private final Map<CoreGeoBone, float[]> confluence$boneMotions = new HashMap<>();
    @Unique private final Map<ModelPart, float[]> confluence$partMotions = new HashMap<>();
    @Unique private final Map<ModelPart, PartPose> confluence$deathPose = new HashMap<>();
    @Unique private Vec3 confluence$lastMotion = Vec3.ZERO;
    @Unique private int confluence$landTicks = 0;
    @Unique private Vec3 confluence$landMotion = new Vec3(0, Double.NaN, 0);

    private ClientLivingEntityMixin(EntityType<?> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    @Shadow @NotNull public abstract EntityDimensions getDimensions(@NotNull Pose pPose);

    @Inject(method = "checkFallDamage", at = @At("HEAD"))
    private void fall(double motionY, boolean onGround, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity self = self();
        if (motionY > 0.0 && self instanceof LocalPlayer && GravitationHandler.isShouldRot()) {
            self.fallDistance += motionY;
            NetworkHandler.CHANNEL.sendToServer(new FallDistancePacketC2S(self.fallDistance));
        }
    }

    @ModifyArg(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"), index = 2)
    private double fall2(double pPosY) {
        if (self() instanceof Player player) {
            if (player.isLocalPlayer() && GravitationHandler.isShouldRot()) {
                return pPosY + getDimensions(player.getPose()).height;
            }
        }
        return pPosY;
    }

    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 confused(Vec3 vec3) {
        if (self() instanceof LocalPlayer) {
            if (GravitationHandler.isShouldRot()) {
                return new Vec3(vec3.x * -1.0, vec3.y, vec3.z);
            } else if (StepStoolHandler.onStool()) {
                return Vec3.ZERO;
            }
        }
        return vec3;
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "tickDeath", at = @At("HEAD"))
    private void tickDeath(CallbackInfo ci){
        DeathAnimOptions options = DeathAnimUtils.getDeathAnimOptions(self());
        if(options == null) return;
        if(self().deathTime > 0){
            Vec3 motion = getDeltaMovement();
            Vec3 clip = ((EntityAccessor) self()).callCollide(motion);
            if(clip.y > -0.1 && clip.y <= 0 && Double.isNaN(confluence$landMotion.y) && onGround()){
                confluence$landMotion = confluence$lastMotion.y < -0.1 ? confluence$lastMotion : motion.y < -0.1 ? motion : motion.with(Direction.Axis.Y, -0.1);
                if(confluence$landMotion.y <= -0.25){
                    confluence$landMotion.with(Direction.Axis.Y, confluence$landMotion.y * 0.6);
                }
                confluence$landTicks = 0;
            }
            if(!Double.isNaN(confluence$landMotion.y)){
                confluence$landTicks++;
            }else{
                confluence$lastMotion = motion;
            }
        }
        if(self().deathTime != 0){
            return;
        }
        if(self() instanceof GeoAnimatable animatable){  // TODO: 原版生物能穿geo甲，geo生物能穿原版甲
            GeoModel<GeoAnimatable> model = (GeoModel<GeoAnimatable>) RenderUtils.getGeoModelForEntity(self());
            confluence$initGeoDeathAnim(model, options,animatable);
        }else{
            confluence$initVanillaDeathAnim(options);
        }
        confluence$initGeoArmorAnim(options);
    }

    @Unique
    private <T extends Item & GeoItem> void confluence$initGeoArmorAnim(DeathAnimOptions options){
        ItemStack head = self().getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = self().getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = self().getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = self().getItemBySlot(EquipmentSlot.FEET);

        IClientItemExtensions.of(head).getHumanoidArmorModel(self(), head, EquipmentSlot.HEAD, null);
        for(Object model : new Object[]{IClientItemExtensions.of(head).getHumanoidArmorModel(self(), head, EquipmentSlot.HEAD, null),
            IClientItemExtensions.of(head).getHumanoidArmorModel(self(), chest, EquipmentSlot.CHEST, null),
            IClientItemExtensions.of(legs).getHumanoidArmorModel(self(), legs, EquipmentSlot.LEGS, null),
            IClientItemExtensions.of(feet).getHumanoidArmorModel(self(), feet, EquipmentSlot.FEET, null)}){
            if(model instanceof GeoArmorRenderer<?>){
                GeoArmorRenderer<T> gr = (GeoArmorRenderer<T>) model;
                confluence$initGeoDeathAnim(gr.getGeoModel(), options,gr.getAnimatable());
            }
        }
    }

    @Unique
    private <T extends GeoAnimatable> void confluence$initGeoDeathAnim(GeoModel<T> model, DeathAnimOptions options,T animatable){
//        GeoModel<GeoAnimatable> model = (GeoModel<GeoAnimatable>) RenderUtils.getGeoModelForEntity(self());
        RandomSource random = self().level().getRandom();
        if(model != null){
            BakedGeoModel bakedModel = model.getBakedModel(model.getModelResource(animatable));
            for(CoreGeoBone bone : DeathAnimUtils.findAllBones(bakedModel)){
//            for(GeoBone bone : bakedModel.topLevelBones()){
                confluence$boneMotions.put(bone, DeathAnimUtils.createOffsets(random, self().getDeltaMovement(), bone.getPosY(), options));
            }
        }
    }

    @Unique
    private void confluence$initVanillaDeathAnim(DeathAnimOptions options){
        EntityRenderer<?> r = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(self());
        if(r instanceof LivingEntityRenderer<?, ?> renderer){
            RandomSource random = self().level().getRandom();
            for(ModelPart modelPart : DeathAnimUtils.findAllModelPart(renderer)){
                confluence$partMotions.putIfAbsent(modelPart, DeathAnimUtils.createOffsets(random, self().getDeltaMovement(), modelPart, options));
                confluence$deathPose.putIfAbsent(modelPart, modelPart.storePose()); // 记录死亡瞬间的姿势
            }
        }
    }

    @Override
    public int confluence$landTicks(){
        return confluence$landTicks;
    }

    @Override
    public Vec3 confluence$landMotion(){
        return confluence$landMotion;
    }

    @Override
    public float[] confluence$getMotionsForBone(GeoBone bone){
        float[] f = confluence$boneMotions.get(bone);
        return f != null ? Arrays.copyOf(f, 6) : new float[6];
    }

    @Override
    public float[] confluence$getMotionsForPart(ModelPart part){
        float[] f = confluence$partMotions.get(part);
        return f != null ? Arrays.copyOf(f, 6) : new float[6];
    }

    @Override
    public PartPose confluence$getPoseForPart(ModelPart part){
        return confluence$deathPose.get(part);
    }


}
