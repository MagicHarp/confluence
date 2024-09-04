package org.confluence.mod.menu;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.recipe.ModRecipes;
import org.confluence.mod.recipe.WorkshopRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WorkshopMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 4);
    private final ResultContainer resultSlots = new ResultContainer();

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
        addSlot(new AmountResultSlot(craftSlots, resultSlots, 0, 80, 35));

        addSlot(new Slot(craftSlots, 0, 53, 8));
        addSlot(new Slot(craftSlots, 1, 71, 8));
        addSlot(new Slot(craftSlots, 2, 89, 8));
        addSlot(new Slot(craftSlots, 3, 107, 8));
        addSlot(new Slot(craftSlots, 4, 107, 26));
        addSlot(new Slot(craftSlots, 5, 107, 44));
        addSlot(new Slot(craftSlots, 6, 107, 62));
        addSlot(new Slot(craftSlots, 7, 89, 62));
        addSlot(new Slot(craftSlots, 8, 71, 62));
        addSlot(new Slot(craftSlots, 9, 53, 62));
        addSlot(new Slot(craftSlots, 10, 53, 44));
        addSlot(new Slot(craftSlots, 11, 53, 26));

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 9; l++) {
                addSlot(new Slot(pPlayerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
            }
        }
        for (int m = 0; m < 9; m++) {
            addSlot(new Slot(pPlayerInventory, m, 8 + m * 18, 142));
        }
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
        return pSlot.container != resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        access.execute((level, pos) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack itemstack = ItemStack.EMPTY;
                Optional<WorkshopRecipe> optional = level.getRecipeManager().getRecipeFor(ModRecipes.WORKSHOP_TYPE.get(), pContainer, level);
                if (optional.isPresent()) {
                    itemstack = optional.get().getResultItem(level.registryAccess());
                    if (getSlot(0) instanceof AmountResultSlot resultSlot) {
                        resultSlot.setCurrentRecipe(optional.get());
                    }
                }
                resultSlots.setItem(0, itemstack);
                setRemoteSlot(0, itemstack);
                serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(containerId, incrementStateId(), 0, itemstack));
            }
        });
    }
}
