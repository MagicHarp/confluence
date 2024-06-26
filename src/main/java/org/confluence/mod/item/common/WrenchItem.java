package org.confluence.mod.item.common;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.block.functional.mechanical.PathService;
import org.confluence.mod.datagen.limit.ReversalImage16x;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WrenchItem extends Item implements ReversalImage16x {
    public final int color;

    public WrenchItem(int color) {
        super(new Properties().stacksTo(1).rarity(ModRarity.BLUE));
        this.color = color;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return containsPos(pStack);
    }

    public static boolean containsPos(ItemStack pStack) {
        return pStack.getTag() != null && pStack.getTag().contains("blockPos");
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        if (pLevel.isClientSide) return InteractionResult.SUCCESS;

        ItemStack itemStack = pContext.getItemInHand();
        BlockPos pPos = pContext.getClickedPos();
        if (pLevel.getBlockEntity(pPos) instanceof AbstractMechanicalBlock.Entity entity) {
            BlockPos storedPos = readBlockPos(itemStack);
            if (storedPos == null) {
                writeBlockPos(itemStack, pPos);
            } else if (pLevel.getBlockEntity(storedPos) instanceof AbstractMechanicalBlock.Entity entity1) {
                if (entity1.connectedPoses.int2ObjectEntrySet().stream()
                    .noneMatch(entry -> entry.getIntKey() == color && entry.getValue().contains(pPos))
                ) {
                    entity.connectTo(color, storedPos, entity1);
                    PathService.INSTANCE.addToQueue(entity);
                }
                removeBlockPos(itemStack);
            }
            return InteractionResult.CONSUME;
        }
        removeBlockPos(itemStack);
        return InteractionResult.PASS;
    }

    public static void writeBlockPos(ItemStack itemStack, BlockPos pos) {
        itemStack.getOrCreateTag().put("blockPos", NbtUtils.writeBlockPos(pos));
    }

    public static @Nullable BlockPos readBlockPos(ItemStack itemStack) {
        if (itemStack.getTag() == null) return null;
        return itemStack.getTag().contains("blockPos") ? NbtUtils.readBlockPos(itemStack.getTag().getCompound("blockPos")) : null;
    }

    public static void removeBlockPos(ItemStack itemStack) {
        itemStack.getOrCreateTag().remove("blockPos");
    }
}
