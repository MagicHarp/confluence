package org.confluence.mod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class WorkshopMenu extends AbstractContainerMenu {
    private final Inventory inventory;
    private final ContainerLevelAccess access;

    public WorkshopMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, ContainerLevelAccess.NULL);
    }

    public WorkshopMenu(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess) {
        super(ModMenus.WORKSHOP.get(), pContainerId);
        this.inventory = pPlayerInventory;
        this.access = pAccess;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(access, pPlayer, ModBlocks.WORKSHOP.get());
    }
}
