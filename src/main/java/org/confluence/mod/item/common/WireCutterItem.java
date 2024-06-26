package org.confluence.mod.item.common;

import net.minecraft.core.BlockPos;
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

public class WireCutterItem extends Item implements ReversalImage16x {
    public WireCutterItem() {
        super(new Properties().stacksTo(1).rarity(ModRarity.BLUE));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return WrenchItem.containsPos(pStack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;

        ItemStack itemStack = pContext.getItemInHand();
        BlockPos pPos = pContext.getClickedPos();
        if (level.getBlockEntity(pPos) instanceof AbstractMechanicalBlock.Entity entity) {
            BlockPos storedPos = WrenchItem.readBlockPos(itemStack);
            if (storedPos == null) {
                WrenchItem.writeBlockPos(itemStack, pPos);
            } else if (level.getBlockEntity(storedPos) instanceof AbstractMechanicalBlock.Entity entity1) {
                entity.connectedPoses.int2ObjectEntrySet().stream()
                    .filter(entry -> entry.getValue().contains(storedPos))
                    .forEach(entry -> entity.disconnectWith(entry.getIntKey(), storedPos, entity1));

                entity1.connectedPoses.int2ObjectEntrySet().stream()
                    .filter(entry -> entry.getValue().contains(pPos))
                    .forEach(entry -> entity1.disconnectWith(entry.getIntKey(), pPos, entity));

                PathService.INSTANCE.onBlockEntityUnload(entity);
                WrenchItem.removeBlockPos(itemStack);
            }
            return InteractionResult.CONSUME;
        }
        WrenchItem.removeBlockPos(itemStack);
        return InteractionResult.PASS;
    }
}
