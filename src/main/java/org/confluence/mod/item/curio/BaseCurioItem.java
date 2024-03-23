package org.confluence.mod.item.curio;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class BaseCurioItem extends Item implements ICurioItem {
    public BaseCurioItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public BaseCurioItem() {
        this(new Properties());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return ifEmpty(slotContext, itemStack -> itemStack.getItem() == this);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ifEmpty(slotContext, itemStack -> itemStack.getItem() == this);
    }

    public static boolean ifEmpty(SlotContext slotContext, Predicate<ItemStack> predicate) {
        AtomicBoolean isEmpty = new AtomicBoolean();
        CuriosApi.getCuriosInventory(slotContext.entity())
            .ifPresent(handler -> isEmpty.set(handler.findCurios(predicate).isEmpty()));
        return isEmpty.get();
    }
}
