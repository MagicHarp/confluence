package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public interface IMagicQuiver {
    static void applyToArrow(LivingEntity living, AbstractArrow arrow) {
        AtomicBoolean isEmpty = new AtomicBoolean(true);
        AtomicBoolean hasFire = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    Item item = stack.getItem();
                    if (!stack.isEmpty() && item instanceof MagicQuiver) {
                        isEmpty.set(false);
                        if (item == CurioItems.MOLTEN_QUIVER.get()) {
                            hasFire.set(true);
                            return;
                        }
                    }
                }
            }
        });
        if (!isEmpty.get()) arrow.setDeltaMovement(arrow.getDeltaMovement().scale(1.2));
        if (hasFire.get()) arrow.setSecondsOnFire(100);
    }

    static boolean shouldConsume(LivingEntity living) {
        return living.getRandom().nextFloat() >= 0.2F || CuriosUtils.noSameCurio(living, MagicQuiver.class);
    }
}
