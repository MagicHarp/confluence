package org.confluence.mod.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CuriosUtils {
    public static boolean noSameCurio(LivingEntity living, Predicate<ItemStack> predicate) {
        AtomicBoolean isEmpty = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living)
            .ifPresent(handler -> isEmpty.set(handler.findCurios(predicate).isEmpty()));
        return isEmpty.get();
    }

    public static <C extends BaseCurioItem> boolean noSameCurio(LivingEntity living, C curio) {
        AtomicBoolean isEmpty = new AtomicBoolean();
        CuriosApi.getCuriosInventory(living)
            .ifPresent(handler -> isEmpty.set(handler.findCurios(itemStack -> itemStack.getItem() == curio).isEmpty()));
        return isEmpty.get();
    }
}
