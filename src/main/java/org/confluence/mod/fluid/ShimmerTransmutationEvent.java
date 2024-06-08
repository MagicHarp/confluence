package org.confluence.mod.fluid;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.command.GamePhase;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This event is Server side only.
 */
public abstract class ShimmerTransmutationEvent extends Event {
    public static final ArrayList<Transmutation> ITEM_TRANSMUTATION = new ArrayList<>();
    protected final ItemEntity source;
    protected int coolDown;
    protected int shrink = 0;

    public ShimmerTransmutationEvent(ItemEntity source) {
        this.source = source;
        this.coolDown = source.lifespan;
    }

    public ItemEntity getSource() {
        return source;
    }

    /**
     * Shrink item stack's count. Defaults to 0.
     */
    public void setShrink(int count) {
        this.shrink = count;
    }

    public int getShrink() {
        return shrink;
    }

    /**
     * Determines how long could ItemEntity transmutation next time.
     * <p>
     * Defaults to ItemEntity's lifespan(most of the time it is 6000).
     */
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getCoolDown() {
        return coolDown;
    }

    /**
     * This event fired when an ItemEntity toss in shimmer.
     * <p>
     * This event is {@link net.minecraftforge.eventbus.api.Cancelable}
     */
    @Cancelable
    public static class Pre extends ShimmerTransmutationEvent {
        private int transformTime = 20;

        public Pre(ItemEntity source) {
            super(source);
        }

        /**
         * Determines how long should ItemEntity transmutation before.
         */
        public void setTransformTime(int transformTime) {
            this.transformTime = transformTime;
        }

        public int getTransformTime() {
            return transformTime;
        }
    }

    /**
     * This event fired when an ItemEntity trying to transmutation.
     */
    public static class Post extends ShimmerTransmutationEvent {
        private @Nullable List<ItemStack> targets;

        public Post(ItemEntity source) {
            super(source);
        }

        public void setTargets(@Nullable List<ItemStack> targets) {
            this.targets = targets;
        }

        /**
         * Determines what will ItemEntity transmutation to,
         * <p>
         * If targets have not set yet, it will try to generate targets.
         */
        public @Nullable List<ItemStack> getTargets() {
            if (targets == null) {
                ItemStack sourceItem = source.getItem();
                for (Transmutation transmutation : ITEM_TRANSMUTATION) {
                    if (transmutation.source.test(sourceItem)) {
                        int times = sourceItem.getCount() / transmutation.shrink;
                        List<ItemStack> results = new ArrayList<>();
                        for (ItemStack result : transmutation.target) {
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
                        this.shrink = transmutation.shrink * times;
                        this.targets = results;
                        return targets;
                    }
                }
                if (sourceItem.getDamageValue() != 0) return null;
                RegistryAccess registryAccess = source.level().registryAccess();
                boolean isHardCore = ConfluenceData.get((ServerLevel) source.level()).isHardcore();
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

    public static void add(TagKey<Item> source, List<ItemStack> target, int shrink) {
        ITEM_TRANSMUTATION.add(new Transmutation(Ingredient.of(source), target, shrink, GamePhase.BEFORE_SKELETRON));
    }

    public static void add(Ingredient source, List<ItemStack> target, int shrink) {
        ITEM_TRANSMUTATION.add(new Transmutation(source, target, shrink, GamePhase.BEFORE_SKELETRON));
    }

    public static void add(Item source, Item target, int shrink) {
        ITEM_TRANSMUTATION.add(new Transmutation(Ingredient.of(source), Collections.singletonList(new ItemStack(target)), shrink, GamePhase.BEFORE_SKELETRON));
    }

    public static void add(Item source, Item target) {
        ITEM_TRANSMUTATION.add(new Transmutation(Ingredient.of(source), Collections.singletonList(new ItemStack(target)), 1, GamePhase.BEFORE_SKELETRON));
    }

    public static void add(TagKey<Item> source, List<ItemStack> target, int shrink, GamePhase gamePhase) {
        ITEM_TRANSMUTATION.add(new Transmutation(Ingredient.of(source), target, shrink, gamePhase));
    }

    public static void add(Ingredient source, List<ItemStack> target, int shrink, GamePhase gamePhase) {
        ITEM_TRANSMUTATION.add(new Transmutation(source, target, shrink, gamePhase));
    }

    public static void add(Item source, Item target, int shrink, GamePhase gamePhase) {
        ITEM_TRANSMUTATION.add(new Transmutation(Ingredient.of(source), Collections.singletonList(new ItemStack(target)), shrink, gamePhase));
    }

    public static void add(Item source, Item target, GamePhase gamePhase) {
        ITEM_TRANSMUTATION.add(new Transmutation(Ingredient.of(source), Collections.singletonList(new ItemStack(target)), 1, gamePhase));
    }

    public record Transmutation(Ingredient source, List<ItemStack> target, int shrink, GamePhase gamePhase){}
}