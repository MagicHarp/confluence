package org.confluence.mod.fluid;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

/**
 * This event is called when an ItemEntity toss in shimmer.
 * <p>
 * This event is {@link net.minecraftforge.eventbus.api.Cancelable}
 * and Server side only.
 */
public abstract class ShimmerTransmutationEvent extends Event {
    static final Hashtable<Predicate<ItemStack>, Tuple<List<ItemStack>, Integer>> ITEM_TRANSFORM = new Hashtable<>();
    protected final ItemEntity source;
    protected @Nullable List<ItemStack> targets;
    protected int coolDown;
    protected int shrink = 0;

    public ShimmerTransmutationEvent(ItemEntity source) {
        this.source = source;
        this.coolDown = source.lifespan;
    }

    public ItemEntity getSource() {
        return source;
    }

    public void setShrink(int count) {
        this.shrink = count;
    }

    public int getShrink() {
        return shrink;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getCoolDown() {
        return coolDown;
    }

    @Cancelable
    public static class Pre extends ShimmerTransmutationEvent {
        protected int transformTime = 20;

        public Pre(ItemEntity source) {
            super(source);
        }

        public void setTransformTime(int transformTime) {
            this.transformTime = transformTime;
        }

        public int getTransformTime() {
            return transformTime;
        }
    }

    public static class Post extends ShimmerTransmutationEvent {
        private @Nullable List<ItemStack> targets;

        public Post(ItemEntity source) {
            super(source);
        }

        public void setTargets(@Nullable List<ItemStack> targets) {
            this.targets = targets;
        }

        public @Nullable List<ItemStack> getTargets() {
            if (targets == null) {
                ItemStack sourceItem = source.getItem();
                for (Map.Entry<Predicate<ItemStack>, Tuple<List<ItemStack>, Integer>> entry : ITEM_TRANSFORM.entrySet()) {
                    if (entry.getKey().test(sourceItem)) {
                        int shrink = entry.getValue().getB();
                        int times = sourceItem.getCount() / shrink;
                        List<ItemStack> results = new ArrayList<>();
                        for (ItemStack result : entry.getValue().getA()) {
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
                        this.shrink = shrink * times;
                        this.targets = results;
                        return targets;
                    }
                }
                if (sourceItem.getDamageValue() != 0) return null;
                RegistryAccess registryAccess = source.level().registryAccess();
                boolean isHardCore = ConfluenceData.get((ServerLevel) source.level()).isHardCore();
                for (Recipe<?> recipe : ((ServerLevel) source.level()).getServer().getRecipeManager().getRecipes()) {
                    if (recipe.isSpecial() || recipe.isIncomplete() || recipe instanceof AbstractCookingRecipe) continue;
                    ItemStack resultItem = recipe.getResultItem(registryAccess);
                    if (sourceItem.getCount() >= resultItem.getCount() && ItemStack.isSameItem(sourceItem, resultItem)) {
                        int times = sourceItem.getCount() / resultItem.getCount();
                        List<ItemStack> results = new ArrayList<>();
                        for (Ingredient ingredient : recipe.getIngredients()) {
                            ItemStack[] itemStacks = ingredient.getItems();
                            if (itemStacks.length == 0 || Arrays.stream(itemStacks).allMatch(itemStack -> itemStack.is(ModTags.Items.HARDCORE))) {
                                continue;
                            }
                            ItemStack input = itemStacks[source.level().random.nextInt(itemStacks.length)];
                            while (!isHardCore && input.is(ModTags.Items.HARDCORE)) {
                                input = itemStacks[source.level().random.nextInt(itemStacks.length)];
                            }
                            ItemStack result = input.copy();
                            if (result.getItem().hasCraftingRemainingItem(result)) continue;
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
                        if (results.isEmpty()) continue;
                        this.shrink = resultItem.getCount() * times;
                        this.targets = results;
                        return targets;
                    }
                }
                return null;
            }
            return targets;
        }
    }

    public static void add(Predicate<ItemStack> source, List<ItemStack> target, int shrink) {
        ITEM_TRANSFORM.put(source, new Tuple<>(target, shrink));
    }

    public static void add(ItemStack source, ItemStack target) {
        ITEM_TRANSFORM.put(itemStack -> ItemStack.matches(itemStack, source), new Tuple<>(Collections.singletonList(target), source.getCount()));
    }

    public static void add(ItemStack source, List<ItemStack> target) {
        ITEM_TRANSFORM.put(itemStack -> ItemStack.matches(itemStack, source), new Tuple<>(target, source.getCount()));
    }

    public static void add(Item source, Item target) {
        ITEM_TRANSFORM.put(itemStack -> itemStack.is(source), new Tuple<>(Collections.singletonList(new ItemStack(target)), 1));
    }
}
