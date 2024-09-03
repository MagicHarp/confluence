package org.confluence.mod.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class AmountIngredient extends AbstractIngredient {
    public static final AmountIngredient EMPTY = new AmountIngredient();
    private final ItemStack itemStack;

    protected AmountIngredient(ItemStack itemStack) {
        super(Stream.of(new AmountItemValue(itemStack)));
        this.itemStack = itemStack;
    }

    AmountIngredient() {
        super(Stream.empty());
        this.itemStack = ItemStack.EMPTY;
    }

    public static AmountIngredient of(ItemStack itemStack) {
        return new AmountIngredient(itemStack);
    }

    public Item getItem() {
        return itemStack.getItem();
    }

    public int getCount() {
        return itemStack.getCount();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public ItemStack @NotNull [] getItems() {
        return new ItemStack[]{itemStack};
    }

    @Override
    public boolean test(@Nullable ItemStack pStack) {
        if (pStack == null) return false;
        if (pStack == itemStack) {
            return true;
        } else {
            return pStack.getCount() >= itemStack.getCount() && ItemStack.isSameItemSameTags(pStack, itemStack);
        }
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    @Override
    public @NotNull IIngredientSerializer<AmountIngredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "confluence:amount");
        serialize(jsonObject, itemStack);
        return jsonObject;
    }

    public static void serialize(JsonObject jsonObject, ItemStack itemStack) {
        jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
        jsonObject.addProperty("count", itemStack.getCount());
        if (itemStack.getTag() != null) jsonObject.addProperty("nbt", itemStack.getTag().toString());
    }

    public static class Serializer implements IIngredientSerializer<AmountIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull AmountIngredient parse(FriendlyByteBuf buffer) {
            return new AmountIngredient(buffer.readItem());
        }

        @Override
        public @NotNull AmountIngredient parse(@NotNull JsonObject json) {
            return new AmountIngredient(CraftingHelper.getItemStack(json, true));
        }

        @Override
        public void write(FriendlyByteBuf buffer, AmountIngredient ingredient) {
            buffer.writeItem(ingredient.itemStack);
        }
    }

    public static class AmountItemValue implements Value {
        private final Collection<ItemStack> itemStacks;

        public AmountItemValue(ItemStack itemStack) {
            this.itemStacks = Collections.singleton(itemStack);
        }

        @Override
        public @NotNull Collection<ItemStack> getItems() {
            return itemStacks;
        }

        @Override
        public @NotNull JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            ItemStack itemStack = itemStacks.iterator().next();
            AmountIngredient.serialize(jsonObject, itemStack);
            return jsonObject;
        }
    }
}
