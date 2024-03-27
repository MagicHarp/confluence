package org.confluence.mod.item.curio.movement;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.util.LivingMixin;
import top.theillusivec4.curios.api.CuriosApi;

public interface IJumpBoost {
    double getBoost();

    default void freshMaxBoost(LivingEntity living) {
        ((LivingMixin) living).c$freshMaxBoost();
    }

    static double getMaxBoost(LivingEntity living) {
        AtomicDouble maxBoost = new AtomicDouble(1.0);
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                if (itemHandlerModifiable.getStackInSlot(i).getItem() instanceof IJumpBoost iJumpBoost) {
                    double boost = iJumpBoost.getBoost() + 1.0;
                    if (boost > maxBoost.get()) {
                        maxBoost.set(boost);
                    }
                }
            }
        });
        return maxBoost.get();
    }
}
