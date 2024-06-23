package org.confluence.mod.block.functional.mechanical;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.common.WrenchItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseMechanicalBlock extends Block implements EntityBlock {
    public BaseMechanicalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            PathService.INSTANCE.onBlockEntityUnload(entity);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.getItem() instanceof WrenchItem wrenchItem && pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            BlockPos pos = WrenchItem.readBlockPos(itemStack);
            if (pos == null) {
                WrenchItem.writeBlockPos(itemStack, pPos);
            } else {
                entity.connectedPoses.put(wrenchItem.color, pos);
                entity.markUpdated();
                WrenchItem.removeBlockPos(itemStack);
            }
            return InteractionResult.CONSUME;
        }
        WrenchItem.removeBlockPos(itemStack);
        return InteractionResult.PASS;
    }

    public static class Entity extends BlockEntity {
        private NetworkNode networkNode;
        public final Int2ObjectMap<BlockPos> connectedPoses;

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.MECHANICAL_BLOCK_ENTITY.get(), pPos, pBlockState);
            this.connectedPoses = new Int2ObjectOpenHashMap<>();
        }

        @Override
        public void onLoad() {
            super.onLoad();
            PathService.INSTANCE.onBlockEntityLoad(this);
        }

        @Override
        public void onChunkUnloaded() {
            super.onChunkUnloaded();
            PathService.INSTANCE.onBlockEntityUnload(this);
        }

        public void load(@NotNull CompoundTag nbt) {
            super.load(nbt);
            connectedPoses.clear();
            if (nbt.get("connectedPoses") instanceof ListTag list) {
                list.forEach(tag -> {
                    if (tag instanceof CompoundTag tag1) {
                        int color = tag1.getInt("color");
                        BlockPos pos = NbtUtils.readBlockPos(tag1);
                        connectedPoses.put(color, pos);
                    }
                });
            }
        }

        protected void saveAdditional(@NotNull CompoundTag nbt) {
            super.saveAdditional(nbt);
            deserializePoses(nbt);
        }

        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        public @NotNull CompoundTag getUpdateTag() {
            return deserializePoses(new CompoundTag());
        }

        public CompoundTag deserializePoses(CompoundTag nbt) {
            ListTag list = new ListTag();
            for (Int2ObjectMap.Entry<BlockPos> entry : connectedPoses.int2ObjectEntrySet()) {
                CompoundTag tag = NbtUtils.writeBlockPos(entry.getValue());
                tag.putInt("color", entry.getIntKey());
                list.add(tag);
            }
            nbt.put("connectedPoses", list);
            return nbt;
        }

        public void markUpdated() {
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
            }
        }

        public void setNetworkNode(NetworkNode node) {
            this.networkNode = node;
        }

        public NetworkNode getNetworkNode() {
            return networkNode;
        }
    }
}
