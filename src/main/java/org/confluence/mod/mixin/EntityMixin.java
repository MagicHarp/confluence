package org.confluence.mod.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaHurtReduce;
import org.confluence.mod.item.curio.combat.ILavaImmune;
import org.confluence.mod.util.IEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {
    @Shadow
    public abstract void setSecondsOnFire(int p_20255_);

    @Shadow
    public abstract boolean hurt(DamageSource p_19946_, float p_19947_);

    @Shadow
    public abstract DamageSources damageSources();

    @Shadow
    public abstract void playSound(SoundEvent p_19938_, float p_19939_, float p_19940_);

    @Shadow
    @Final
    protected RandomSource random;
    @Unique
    private boolean c$fireImmune = false;
    @Unique
    private boolean c$lavaHurtReduce = false;
    @Unique
    private int c$maxLavaImmuneTicks = 0;
    @Unique
    private int c$remainLavaImmuneTicks = 0;

    @Unique
    @Override
    public void c$freshFireImmune(LivingEntity living) {
        AtomicBoolean fireImmune = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IFireImmune) {
                    fireImmune.set(true);
                    return;
                }
            }
        });
        this.c$fireImmune = fireImmune.get();
    }

    @Unique
    @Override
    public void c$freshLavaHurtReduce(LivingEntity living) {
        AtomicBoolean reduce = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ILavaHurtReduce) {
                    reduce.set(true);
                    return;
                }
            }
        });
        this.c$lavaHurtReduce = reduce.get();
    }

    @Unique
    @Override
    public void c$freshLavaImmuneTicks(LivingEntity living) {
        AtomicInteger lavaImmuneTicks = new AtomicInteger();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ILavaImmune iLavaImmune) {
                    lavaImmuneTicks.set(Math.max(iLavaImmune.getLavaImmuneTicks(), lavaImmuneTicks.get()));
                }
            }
        });
        this.c$maxLavaImmuneTicks = lavaImmuneTicks.get();
    }

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    private void isFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (c$fireImmune) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInLava()Z", ordinal = 1))
    private boolean resetLavaImmune(Entity instance) {
        boolean inLava = instance.isInLava();
        if (inLava) {
            if (c$remainLavaImmuneTicks > 0) {
                this.c$remainLavaImmuneTicks--;
                return false;
            }
        } else {
            this.c$remainLavaImmuneTicks = c$maxLavaImmuneTicks;
        }
        return inLava;
    }

    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    private void currentLavaHurt(CallbackInfo ci) {
        setSecondsOnFire(c$lavaHurtReduce ? 7 : 15);
        if (hurt(damageSources().lava(), c$lavaHurtReduce ? 2.0F : 4.0F)) {
            playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + random.nextFloat() * 0.4F);
        }
        ci.cancel();
    }
}
