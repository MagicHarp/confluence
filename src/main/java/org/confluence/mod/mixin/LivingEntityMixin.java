package org.confluence.mod.mixin;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.ThinIceBlock;
import org.confluence.mod.item.curio.combat.ICriticalHit;
import org.confluence.mod.item.curio.movement.IFallResistance;
import org.confluence.mod.item.curio.movement.IJumpBoost;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.ILivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ILivingEntity {
    @Unique
    private double c$jumpBoost = 1.0;
    @Unique
    private int c$fallResistance = 0;
    @Unique
    private int c$invulnerableTime = 20;
    @Unique
    private float c$criticalChance = 0.0F;

    @Unique
    @Override
    public void c$freshJumpBoost(LivingEntity living) {
        AtomicDouble maxBoost = new AtomicDouble();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IJumpBoost iJumpBoost) {
                    maxBoost.set(Math.max(iJumpBoost.getBoost(), maxBoost.get()));
                }
            }
        });
        this.c$jumpBoost = maxBoost.get();
    }

    @Unique
    @Override
    public void c$freshFallResistance(LivingEntity living) {
        AtomicInteger fallResistance = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IFallResistance iFallResistance) {
                    int distance = iFallResistance.getFallResistance();
                    if (distance < 0) {
                        fallResistance.set(-1);
                        return;
                    } else {
                        fallResistance.set(Math.max(distance, fallResistance.get()));
                    }
                }
            }
        });
        this.c$fallResistance = fallResistance.get();
    }

    @Unique
    @Override
    public void c$setInvulnerableTime(int time) {
        this.c$invulnerableTime = time;
    }

    @Unique
    @Override
    public void c$freshCriticalChance(LivingEntity living) {
        AtomicDouble maxChance = new AtomicDouble();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ICriticalHit iCriticalHit) {
                    maxChance.set(Math.max(iCriticalHit.getChance(), maxChance.get()));
                }
            }
        });
        this.c$criticalChance = maxChance.floatValue();
    }

    @Unique
    @Override
    public float c$getCriticalChance() {
        return c$criticalChance;
    }

    @Inject(method = "getJumpPower", at = @At("RETURN"), cancellable = true)
    private void multiY(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) (cir.getReturnValue() * c$jumpBoost));
    }

    @Inject(method = "calculateFallDamage", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void fallDamage(float fallDistance, float multiply, CallbackInfoReturnable<Integer> cir) {
        if (c$fallResistance < 0) {
            cir.setReturnValue(0);
        } else {
            cir.setReturnValue(cir.getReturnValue() - c$fallResistance);
        }
    }

    @ModifyConstant(method = "hurt", constant = @Constant(intValue = 20))
    private int invulnerable1(int constant) {
        return c$invulnerableTime;
    }

    @ModifyConstant(method = "handleDamageEvent", constant = @Constant(intValue = 20))
    private int invulnerable2(int constant) {
        return c$invulnerableTime;
    }

    @Inject(method = "checkFallDamage", at = @At("HEAD"), cancellable = true)
    private void thinIceBlock(double motionY, boolean onGround, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.fallDistance > 3.0F && blockState.is(ModBlocks.THIN_ICE_BLOCK.get()) && CuriosUtils.noSameCurio(self, ThinIceBlock.IceSafe.class)) {
            self.level().destroyBlock(blockPos, true, self);
            ci.cancel();
        }
    }
}
