package org.confluence.mod.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CuriosUtils {
    public static boolean noSameCurio(LivingEntity living, Class<?> clazz) {
        return noSameCurio(living, (Predicate<ItemStack>) itemStack -> clazz.isInstance(itemStack.getItem()));
    }

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

    public static boolean hasCurio(LivingEntity living, Class<?> clazz) {
        return !noSameCurio(living, clazz);
    }

    public static boolean hasCurio(LivingEntity living, Predicate<ItemStack> predicate) {
        return !noSameCurio(living, predicate);
    }

    public static <C extends BaseCurioItem> boolean hasCurio(LivingEntity living, C curio) {
        return !noSameCurio(living, curio);
    }

    public static <C> Optional<C> findCurio(LivingEntity living, Class<C> clazz) {
        AtomicReference<Optional<C>> atomic = new AtomicReference<>(Optional.empty());
        CuriosApi.getCuriosInventory(living)
            .ifPresent(handler -> {
                List<SlotResult> results = handler.findCurios(itemStack -> clazz.isInstance(itemStack.getItem()));
                if (results.isEmpty()) return;
                atomic.set(Optional.of(clazz.cast(results.get(0).stack().getItem())));
            });
        return atomic.get();
    }

    public static <C extends BaseCurioItem> ItemStack findCurio(LivingEntity living, C curio) {
        AtomicReference<ItemStack> atomic = new AtomicReference<>(ItemStack.EMPTY);
        CuriosApi.getCuriosInventory(living)
            .ifPresent(handler -> {
                List<SlotResult> results = handler.findCurios(itemStack -> itemStack.getItem() == curio);
                if (results.isEmpty()) return;
                atomic.set(results.get(0).stack());
            });
        return atomic.get();
    }

    public static ArrayList<ItemStack> getCurios(LivingEntity living) {
        ArrayList<ItemStack> items = new ArrayList<>();
        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                if (!itemStack.isEmpty()) items.add(itemStack);
            }
        });
        return items;
    }
}
