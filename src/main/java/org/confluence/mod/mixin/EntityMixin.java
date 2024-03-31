package org.confluence.mod.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.combat.IFireImmune;
import org.confluence.mod.item.curio.combat.ILavaReduce;
import org.confluence.mod.util.IEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {
    @Unique
    private boolean c$fireImmune = false;

    @Unique
    private boolean c$lavaReduce = false;

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
    public void c$freshLavaReduce(LivingEntity living) {
        AtomicBoolean reduce = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof ILavaReduce) {
                    reduce.set(true);
                    return;
                }
            }
        });
        this.c$lavaReduce = reduce.get();
    }

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    private void isFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if (c$fireImmune) {
            cir.setReturnValue(true);
        }
    }

    @ModifyConstant(method = "lavaHurt", constant = @Constant(floatValue = 4.0F))
    private float lavaDamage(float constant) {
        return c$lavaReduce ? constant / 2 : constant;
    }
}
