package org.confluence.mod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class WorkshopMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 4);
    private final ResultContainer resultSlots = new ResultContainer();

    public WorkshopMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, ContainerLevelAccess.NULL);
    }

    public WorkshopMenu(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess) {
        super(ModMenus.WORKSHOP.get(), pContainerId);
        this.player = pPlayerInventory.player;
        this.access = pAccess;
        addSlot(new ResultSlot(player, craftSlots, resultSlots, 0, 143, 33));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                addSlot(new Slot(craftSlots, j + i * 3, 13 + j * 18, 8 + i * 18));
            }
        }
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
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(access, pPlayer, ModBlocks.WORKSHOP.get());
    }

    @Override
    public void removed(@NotNull Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, blockPos) -> clearContainer(pPlayer, craftSlots));
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack pStack, Slot pSlot) {
        return pSlot.container != resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }
}
