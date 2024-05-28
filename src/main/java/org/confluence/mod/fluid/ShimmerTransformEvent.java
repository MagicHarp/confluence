package org.confluence.mod.fluid;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Cancelable
public class ShimmerTransformEvent extends Event {
    static final Hashtable<Predicate<ItemStack>, List<ItemStack>> ITEM_TRANSFORM = new Hashtable<>();
    private final ItemEntity source;
    private @Nullable List<ItemStack> targets;
    private int transformTime = 20;
    private int coolDown;

    public ShimmerTransformEvent(ItemEntity source) {
        this.source = source;
        this.coolDown = source.lifespan;
    }

    public ItemEntity getSource() {
        return source;
    }

    public void setTargets(@Nullable List<ItemStack> targets) {
        this.targets = targets;
    }

    public @Nullable List<ItemStack> getTargets() {
        if (targets == null) {
            ItemStack sourceItem = source.getItem();
            for (Map.Entry<Predicate<ItemStack>, List<ItemStack>> entry : ITEM_TRANSFORM.entrySet()) {
                if (entry.getKey().test(sourceItem)) {
                    this.targets = entry.getValue();
                    return targets;
                }
            }
            return null;
        }
        return targets;
    }

    public void setTransformTime(int transformTime) {
        this.transformTime = transformTime;
    }

    public int getTransformTime() {
        return transformTime;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public static void add(Predicate<ItemStack> source, List<ItemStack> target) {
        ITEM_TRANSFORM.put(source, target);
    }

    public static void add(ItemStack source, ItemStack target) {
        ITEM_TRANSFORM.put(itemStack -> ItemStack.isSameItem(itemStack, source), Collections.singletonList(target));
    }

    public static void add(Predicate<ItemStack> source, ItemStack target) {
        ITEM_TRANSFORM.put(source, Collections.singletonList(target));
    }

    public static void add(ItemStack source, List<ItemStack> target) {
        ITEM_TRANSFORM.put(itemStack -> ItemStack.isSameItem(itemStack, source), target);
    }

    public static void add(Item source, Item target) {
        ITEM_TRANSFORM.put(itemStack -> itemStack.is(source), Collections.singletonList(new ItemStack(target)));
    }
}
