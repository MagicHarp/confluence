package org.confluence.mod.item.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicConch extends Item {
    public MagicConch() {
        super(new Properties().stacksTo(1).rarity(ModRarity.BLUE));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        boolean isClientSide = pContext.getLevel().isClientSide;
        BlockPos clickedPos = pContext.getClickedPos();
        if (!isClientSide && pContext.getClickedFace() == Direction.UP && pContext.getLevel().getBiome(clickedPos).is(BiomeTags.IS_OCEAN)) {
            CompoundTag tag = pContext.getItemInHand().getOrCreateTag();
            if (!tag.contains("pos1")) {
                tag.put("pos1", NbtUtils.writeBlockPos(clickedPos));
            } else if (!tag.contains("pos2")) {
                tag.put("pos2", NbtUtils.writeBlockPos(clickedPos));
            } else {
                double distanceToPos1 = clickedPos.distSqr(NbtUtils.readBlockPos(tag.getCompound("pos1")));
                double distanceToPos2 = clickedPos.distSqr(NbtUtils.readBlockPos(tag.getCompound("pos2")));
                tag.put(distanceToPos1 > distanceToPos2 ? "pos2" : "pos1", NbtUtils.writeBlockPos(clickedPos));
            }
        }
        return InteractionResult.sidedSuccess(isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLevel.isClientSide) return pStack;
        CompoundTag tag = pStack.getTag();
        if (tag == null || (tag.get("pos1") == null && tag.get("pos2") == null)) {
            return pStack;
        }
        if (tag.contains("pos1")) {
            if (tag.contains("pos2")) {
                BlockPos pos = pLivingEntity.blockPosition();
                BlockPos pos1 = NbtUtils.readBlockPos(tag.getCompound("pos1"));
                double distanceToPos1 = pos.distSqr(pos1);
                BlockPos pos2 = NbtUtils.readBlockPos(tag.getCompound("pos2"));
                double distanceToPos2 = pos.distSqr(pos2);
                BlockPos target = distanceToPos1 > distanceToPos2 ? pos1 : pos2;
                pLivingEntity.teleportTo(target.getX(), target.getY(), target.getZ());
            } else {
                BlockPos pos1 = NbtUtils.readBlockPos(tag.getCompound("pos1"));
                pLivingEntity.teleportTo(pos1.getX(), pos1.getY(), pos1.getZ());
            }
        } else { // pos1不存在时，pos2必存在
            BlockPos pos2 = NbtUtils.readBlockPos(tag.getCompound("pos2"));
            pLivingEntity.teleportTo(pos2.getX(), pos2.getY(), pos2.getZ());
        }
        return pStack;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 30;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        if (tag == null) return;
        if (tag.contains("pos1")) {
            BlockPos pos1 = NbtUtils.readBlockPos(tag.getCompound("pos1"));
            pTooltipComponents.add(Component.translatable("item.confluence.magic_conch.pos", pos1.getX(), pos1.getY(), pos1.getZ()));
        }
        if (tag.contains("pos2")) {
            BlockPos pos2 = NbtUtils.readBlockPos(tag.getCompound("pos2"));
            pTooltipComponents.add(Component.translatable("item.confluence.magic_conch.pos", pos2.getX(), pos2.getY(), pos2.getZ()));
        }
    }
}
