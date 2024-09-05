package org.confluence.mod.menu;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.recipe.AbstractAmountRecipe;
import org.confluence.mod.recipe.ModRecipes;
import org.confluence.mod.recipe.WorkshopRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkshopMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 4);
    private final ResultContainer resultSlot = new ResultContainer();
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private List<WorkshopRecipe> recipes = new ArrayList<>();

    public WorkshopMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, ContainerLevelAccess.NULL);
    }

    /*
     * 00 01 02 03
     * 11       04
     * 10       05
     * 09 08 07 06
     */
    public WorkshopMenu(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess) {
        super(ModMenus.WORKSHOP.get(), pContainerId);
        this.player = pPlayerInventory.player;
        this.access = pAccess;
        addSlot(new AmountResultSlot(craftSlots, resultSlot, 0, 62, 35) {
            @Override
            public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
                if (recipe != null) {
                    AbstractAmountRecipe.extractIngredients(crafting, recipe.getIngredients());
                    WorkshopMenu.this.setupResultSlot();
                }
            }
        });

        addSlot(new Slot(craftSlots, 0, 35, 8));
        addSlot(new Slot(craftSlots, 1, 53, 8));
        addSlot(new Slot(craftSlots, 2, 71, 8));
        addSlot(new Slot(craftSlots, 3, 89, 8));
        addSlot(new Slot(craftSlots, 4, 89, 26));
        addSlot(new Slot(craftSlots, 5, 89, 44));
        addSlot(new Slot(craftSlots, 6, 89, 62));
        addSlot(new Slot(craftSlots, 7, 71, 62));
        addSlot(new Slot(craftSlots, 8, 53, 62));
        addSlot(new Slot(craftSlots, 9, 35, 62));
        addSlot(new Slot(craftSlots, 10, 35, 44));
        addSlot(new Slot(craftSlots, 11, 35, 26));

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 9; l++) {
                addSlot(new Slot(pPlayerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
            }
        }
        for (int m = 0; m < 9; m++) {
            addSlot(new Slot(pPlayerInventory, m, 8 + m * 18, 142));
        }

        addDataSlot(selectedRecipeIndex);
    }

    public int getCurrentIndex() {
        return selectedRecipeIndex.get();
    }

    public int getRecipesAmount() {
        return recipes.size();
    }

    public ItemStack getUpResult() {
        int index = getUpIndex();
        if (index == -1) return resultSlot.getItem(0);
        return recipes.get(index).getResultItem(null);
    }

    public int getUpIndex() {
        if (recipes.isEmpty()) return -1;
        if (recipes.size() == 1) {
            return 0;
        } else if (isValidRecipeIndex(selectedRecipeIndex.get())) {
            if (selectedRecipeIndex.get() == 0) {
                return recipes.size() - 1;
            } else {
                return selectedRecipeIndex.get() - 1;
            }
        }
        return -1;
    }

    public ItemStack getDownResult() {
        int index = getDownIndex();
        if (index == -1) return resultSlot.getItem(0);
        return recipes.get(index).getResultItem(null);
    }

    public int getDownIndex() {
        if (recipes.isEmpty()) return -1;
        if (recipes.size() == 1) {
            return 0;
        } else if (isValidRecipeIndex(selectedRecipeIndex.get())) {
            int next = selectedRecipeIndex.get() + 1;
            if (next == recipes.size()) {
                return 0;
            } else {
                return next;
            }
        }
        return -1;
    }

    private boolean isValidRecipeIndex(int pRecipeIndex) {
        return pRecipeIndex >= 0 && pRecipeIndex < recipes.size();
    }

    @Override
    public boolean clickMenuButton(@NotNull Player pPlayer, int pId) {
        if (isValidRecipeIndex(pId)) {
            selectedRecipeIndex.set(pId);
            setupResultSlot();
        }
        return true;
    }

    private void setupResultSlot() {
        if (!recipes.isEmpty() && isValidRecipeIndex(selectedRecipeIndex.get())) {
            WorkshopRecipe recipe = recipes.get(selectedRecipeIndex.get());
            ItemStack itemStack = recipe.getResultItem(null).copy();
            if (itemStack.isItemEnabled(player.level().enabledFeatures())) {
                resultSlot.setItem(0, itemStack);
                if (getSlot(0) instanceof AmountResultSlot amountResultSlot) {
                    amountResultSlot.setCurrentRecipe(recipe);
                }
            } else {
                resultSlot.setItem(0, ItemStack.EMPTY);
            }
        } else {
            resultSlot.setItem(0, ItemStack.EMPTY);
        }
        broadcastChanges();
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(access, pPlayer, ModBlocks.WORKSHOP.get());
    }

    @Override
    public void removed(@NotNull Player pPlayer) {
        super.removed(pPlayer);
        access.execute((level, blockPos) -> clearContainer(pPlayer, craftSlots));
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack pStack, Slot pSlot) {
        return pSlot.container != resultSlot && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        recipes.clear();
        this.recipes = player.level().getRecipeManager().getRecipesFor(ModRecipes.WORKSHOP_TYPE.get(), pContainer, player.level());
        access.execute((level, pos) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack itemStack = ItemStack.EMPTY;
                if (!recipes.isEmpty()) {
                    WorkshopRecipe recipe = recipes.get(0);
                    itemStack = recipe.getResultItem(level.registryAccess()).copy();
                    if (getSlot(0) instanceof AmountResultSlot amountResultSlot) {
                        amountResultSlot.setCurrentRecipe(recipe);
                    }
                }
                resultSlot.setItem(0, itemStack);
                setRemoteSlot(0, itemStack);
                serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(containerId, incrementStateId(), 0, itemStack));
            }
        });
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            itemStack = slotItem.copy();
            if (pIndex == 0) { // resultSlot
                access.execute((level, blockPos) -> slotItem.getItem().onCraftedBy(slotItem, level, pPlayer));
                if (!moveItemStackTo(slotItem, 13, 49, true)) { // playerInventory(ALL)
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotItem, itemStack);
            } else if (pIndex >= 13 && pIndex < 49) { // playerInventory(ALL)
                if (!moveItemStackTo(slotItem, 1, 13, false)) { // craftSlots
                    if (pIndex < 40) {
                        if (!moveItemStackTo(slotItem, 40, 49, false)) { // playerInventory(HOT BAR)
                            return ItemStack.EMPTY;
                        }
                    } else if (!moveItemStackTo(slotItem, 13, 40, false)) { // playerInventory(MAIN)
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!moveItemStackTo(slotItem, 13, 49, false)) { // playerInventory(ALL)
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, slotItem);
            if (pIndex == 0) { // resultSlot
                pPlayer.drop(slotItem, false);
            }
        }

        return itemStack;
    }
}
