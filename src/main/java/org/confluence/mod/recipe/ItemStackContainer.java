package org.confluence.mod.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackContainer extends ItemStackHandler implements Container {
    public final BlockEntity blockEntity;

    public ItemStackContainer(BlockEntity blockEntity, int size) {
        super(size);
        this.blockEntity = blockEntity;
    }

    @Override
    public int getContainerSize() {
        return getSlots();
    }

    @Override
    public boolean isEmpty() {
        return stacks.isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return extractItem(pSlot, pAmount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return extractItem(pSlot, 64, true);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        setStackInSlot(pSlot, pStack);
    }

    @Override
    public void setChanged() {
        blockEntity.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        stacks.clear();
    }
}
