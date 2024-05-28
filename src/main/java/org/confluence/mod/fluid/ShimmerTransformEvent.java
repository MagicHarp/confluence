package org.confluence.mod.fluid;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.*;
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
            RegistryAccess registryAccess = source.level().registryAccess();
            for (Recipe<?> recipe : ((ServerLevel) source.level()).getServer().getRecipeManager().getRecipes()) {
                if (recipe.isSpecial() || recipe.isIncomplete()) continue;
                ItemStack resultItem = recipe.getResultItem(registryAccess);
                if (sourceItem.getCount() >= resultItem.getCount() && ItemStack.isSameItem(sourceItem, resultItem)) {
                    int times = sourceItem.getCount() / resultItem.getCount();
                    List<ItemStack> results = new ArrayList<>();
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        ItemStack[] itemStacks = ingredient.getItems();
                        if (itemStacks.length == 0) continue;
                        ItemStack result = itemStacks[source.level().random.nextInt(itemStacks.length)].copy();
                        int count = result.getCount() * times;
                        while (count > 64) {
                            ItemStack copy = result.copy();
                            copy.setCount(64);
                            results.add(copy);
                            count -= 64;
                        }
                        result.setCount(count);
                        results.add(result);
                    }
                    if (results.isEmpty()) return null;
                    sourceItem.shrink(resultItem.getCount() * times);
                    return results;
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
        ITEM_TRANSFORM.put(itemStack -> ItemStack.matches(itemStack, source), Collections.singletonList(target));
    }

    public static void add(Predicate<ItemStack> source, ItemStack target) {
        ITEM_TRANSFORM.put(source, Collections.singletonList(target));
    }

    public static void add(ItemStack source, List<ItemStack> target) {
        ITEM_TRANSFORM.put(itemStack -> ItemStack.matches(itemStack, source), target);
    }

    public static void add(Item source, Item target) {
        ITEM_TRANSFORM.put(itemStack -> itemStack.is(source), Collections.singletonList(new ItemStack(target)));
    }
}
