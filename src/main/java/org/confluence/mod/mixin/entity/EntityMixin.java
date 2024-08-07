package org.confluence.mod.mixin.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidType;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.effect.beneficial.ObsidianSkinEffect;
import org.confluence.mod.effect.neutral.ShimmerEffect;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.fluid.ShimmerEntityTransmutationEvent;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.IHurtEvasion;
import org.confluence.mod.item.curio.expert.RoyalGel;
import org.confluence.mod.item.curio.expert.ShieldOfCthulhu;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.mixinauxiliary.IEntity;
import org.confluence.mod.mixinauxiliary.IFishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {
    @Unique
    private static final Vec3 ANTI_GRAVITY = new Vec3(0.0, -5.0E-4F, 0.0);

    @Shadow
    public abstract DamageSources damageSources();

    @Shadow
    protected abstract BlockPos getOnPos(float p_216987_);

    @Shadow(remap = false)
    public abstract FluidType getEyeInFluidType();

    @Shadow
    public abstract void discard();

    @Shadow
    public abstract void setNoGravity(boolean pNoGravity);

    @Shadow
    public abstract void setGlowingTag(boolean pHasGlowingTag);

    @Unique
    private int confluence$cthulhuSprintingTime = 0;
    @Unique
    private boolean confluence$isShouldRot = false;
    @Unique
    private boolean confluence$isInShimmer = false;
    @Unique
    private boolean confluence$wasInShimmer = false;
    @Unique
    private int confluence$entity_coolDown = 0;
    @Unique
    private int confluence$entity_transforming = 0;
    @Unique
    private byte confluence$transformData = HAD_SETUP;

    @Override
    public void confluence$entity_setCoolDown(int ticks) {
        this.confluence$entity_coolDown = ticks;
    }

    @Override
    public void confluence$setOriginalNoGravity(boolean bool) {
        this.confluence$transformData = bool ? NO_GRAVITY : HAS_GRAVITY;
    }

    @Override
    public int confluence$getCthulhuSprintingTime() {
        return confluence$cthulhuSprintingTime;
    }

    @Override
    public void confluence$setCthulhuSprintingTime(int amount) {
        this.confluence$cthulhuSprintingTime = amount;
    }

    @Override
    public void confluence$setShouldRot(boolean bool) {
        this.confluence$isShouldRot = bool;
    }

    @Override
    public boolean confluence$isShouldRot() {
        return confluence$isShouldRot;
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInLava()Z"))
    private boolean resetLavaImmune(Entity instance) {
        AtomicBoolean inLava = new AtomicBoolean(instance.isInLava());
        if (confluence$getSelf() instanceof Player living) {
            BlockPos onPos = living.getOnPos();
            if (living.level().getFluidState(onPos).is(FluidTags.LAVA) && !living.level().getFluidState(onPos.above()).is(FluidTags.LAVA)) return false;
            living.getCapability(AbilityProvider.CAPABILITY).ifPresent(playerAbility -> {
                if (inLava.get()) {
                    if (playerAbility.decreaseLavaImmuneTicks()) {
                        inLava.set(false);
                    }
                } else {
                    playerAbility.increaseLavaImmuneTicks();
                }
            });
        }
        return inLava.get();
    }

    @Inject(method = "isInvulnerableTo", at = @At("RETURN"), cancellable = true)
    private void immune(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        if (confluence$getSelf() instanceof LivingEntity living) {
            if (IHurtEvasion.isInvul(living) ||
                IFallResistance.isInvul(living, damageSource) ||
                IFireImmune.isInvul(living, damageSource) ||
                ObsidianSkinEffect.isInvul(living, damageSource) ||
                ShimmerEffect.isInvul(living, damageSource) ||
                RoyalGel.isInvul(living, damageSource) ||
                ShieldOfCthulhu.isInvul(living)
            ) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "setSprinting", at = @At("TAIL"))
    private void sprinting(boolean bool, CallbackInfo ci) {
        if (bool && confluence$getSelf() instanceof Player living) {
            if (confluence$cthulhuSprintingTime == 0 && CuriosUtils.hasCurio(living, CurioItems.SHIELD_OF_CTHULHU.get())) {
                ShieldOfCthulhu.apply(living);
                this.confluence$cthulhuSprintingTime = 32;
            }
        }
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE))
    private void tickProfiler(CallbackInfo ci) {
        if (confluence$cthulhuSprintingTime > 0) this.confluence$cthulhuSprintingTime--;
        if (confluence$entity_coolDown < 0) this.confluence$entity_coolDown = 0;
        this.confluence$isInShimmer = getEyeInFluidType() == ModFluids.SHIMMER.fluidType().get();
        Entity self = confluence$getSelf();
        if (confluence$isInShimmer) {
            if (!self.level().isClientSide && confluence$entity_coolDown == 0) {
                ShimmerEntityTransmutationEvent.Pre pre = new ShimmerEntityTransmutationEvent.Pre(self);
                if (MinecraftForge.EVENT_BUS.post(pre)) {
                    confluence$setup(self, pre.getCoolDown(), pre.getSpeedY());
                } else if (confluence$entity_transforming < pre.getTransformTime()) {
                    this.confluence$entity_transforming++;
                    self.addDeltaMovement(ANTI_GRAVITY);
                } else {
                    ShimmerEntityTransmutationEvent.Post post = new ShimmerEntityTransmutationEvent.Post(self);
                    MinecraftForge.EVENT_BUS.post(post);
                    Entity target = post.getTarget();
                    if (target != null) {
                        discard();
                        confluence$setup(target, post.getCoolDown(), post.getSpeedY());
                        self.level().addFreshEntity(target);
                        return;
                    }
                }
            }

            if (!confluence$wasInShimmer && self instanceof Projectile projectile) {
                Vec3 motion = projectile.getDeltaMovement();
                projectile.setDeltaMovement(motion.x, -motion.y, motion.z);
                this.confluence$wasInShimmer = true;
            }
        } else {
            this.confluence$entity_transforming = 0;
            if (confluence$entity_coolDown > 0) this.confluence$entity_coolDown--;
            if (confluence$entity_coolDown == 0 && confluence$transformData != HAD_SETUP && !(self instanceof ItemEntity)) {
                setGlowingTag(false);
                if (confluence$transformData == 0) {
                    setNoGravity(false);
                }
                this.confluence$transformData = HAD_SETUP;
            }
            this.confluence$wasInShimmer = false;
        }
    }

    @Inject(method = "playerTouch", at = @At("TAIL"))
    private void collidingCheck(Player player, CallbackInfo ci) {
        IEntity iEntity = (IEntity) player;
        if (iEntity.confluence$isOnCthulhuSprinting()) {
            Entity self = confluence$getSelf();
            Vec3 vector = player.getDeltaMovement();
            self.addDeltaMovement(new Vec3(vector.x * 1.6, 0.6, vector.z * 1.6));
            self.hurt(damageSources().playerAttack(player), 7.8F);
            player.setDeltaMovement(vector.scale(-0.9));
            iEntity.confluence$setCthulhuSprintingTime(20);
        }
    }

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    private void fireProof(CallbackInfoReturnable<Boolean> cir) {
        if (confluence$getSelf() instanceof IFishingHook fishingHook) {
            cir.setReturnValue(fishingHook.confluence$isLavaHook());
        }
    }

    @Unique
    private Entity confluence$getSelf() {
        return (Entity) (Object) this;
    }

    @Unique
    private static void confluence$setup(Entity entity, int coolDown, double y) {
        IEntity iEntity = (IEntity) entity;
        iEntity.confluence$setOriginalNoGravity(entity.isNoGravity());
        iEntity.confluence$entity_setCoolDown(coolDown);
        entity.setNoGravity(true);
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x, y, motion.z);
        entity.setGlowingTag(true);
    }
}
