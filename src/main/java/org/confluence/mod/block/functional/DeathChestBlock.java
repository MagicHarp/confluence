package org.confluence.mod.block.functional;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.BaseChestBlock;
import org.confluence.mod.block.functional.network.INetworkBlock;
import org.confluence.mod.block.functional.network.INetworkEntity;
import org.confluence.mod.block.functional.network.NetworkNode;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.mixin.accessor.ChestBlockEntityAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@SuppressWarnings("deprecation")
public class DeathChestBlock extends BaseChestBlock implements INetworkBlock {
    public DeathChestBlock() {
        super(Properties.copy(Blocks.TRAPPED_CHEST), ModBlocks.DEATH_CHEST_BLOCK_ENTITY::get);
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    protected @NotNull Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    public boolean isSignalSource(@NotNull BlockState pState) {
        return true;
    }

    public int getSignal(@NotNull BlockState pBlockState, @NotNull BlockGetter pBlockAccess, @NotNull BlockPos pPos, @NotNull Direction pSide) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(pBlockAccess, pPos), 0, 15);
    }

    public int getDirectSignal(@NotNull BlockState pBlockState, @NotNull BlockGetter pBlockAccess, @NotNull BlockPos pPos, @NotNull Direction pSide) {
        return pSide == Direction.UP ? pBlockState.getSignal(pBlockAccess, pPos, pSide) : 0;
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        onNodeRemove(pState, pLevel, pPos, pNewState);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (cannotInteractWith(pPlayer.getItemInHand(pHand).getItem())) {
            return InteractionResult.PASS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack itemStack = new ItemStack(this);
        if (level.getBlockEntity(pos) instanceof Entity entity) {
            return setData(itemStack, entity.variant);
        }
        return itemStack;
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {}

    public static ItemStack setData(ItemStack itemStack, Variant variant) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("VariantId", variant.getId());
        itemStack.setHoverName(Component.translatable("block.confluence.base_chest_block." + variant.getSerializedName().replace("unlocked", "death")));
        return itemStack;
    }

    public static class Entity extends BaseChestBlock.Entity implements INetworkEntity {
        private NetworkNode networkNode;
        private final Int2ObjectMap<Set<BlockPos>> connectedPoses;
        private final Int2ObjectMap<Set<BlockPos>> relativePoses;

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.DEATH_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
            this.connectedPoses = new Int2ObjectOpenHashMap<>();
            this.relativePoses = new Int2ObjectOpenHashMap<>();
        }

        protected void signalOpenCount(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, int pEventId, int pEventParam) {
            super.signalOpenCount(pLevel, pPos, pState, pEventId, pEventParam);
            if (pEventId != pEventParam) {
                Block block = pState.getBlock();
                pLevel.updateNeighborsAt(pPos, block);
                pLevel.updateNeighborsAt(pPos.below(), block);
            }
        }

        @Override
        public void startOpen(@NotNull Player pPlayer) {
            super.startOpen(pPlayer);
            if (((ChestBlockEntityAccessor) this).getOpenersCounter().getOpenerCount() == 1) {
                ((INetworkBlock) getBlockState().getBlock()).execute(getBlockState(), (ServerLevel) getLevel(), getBlockPos(), true);
            }
        }

        @Override
        public void stopOpen(@NotNull Player pPlayer) {
            super.stopOpen(pPlayer);
            if (((ChestBlockEntityAccessor) this).getOpenersCounter().getOpenerCount() == 0) {
                ((INetworkBlock) getBlockState().getBlock()).execute(getBlockState(), (ServerLevel) getLevel(), getBlockPos(), false);
            }
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public net.minecraft.world.phys.AABB getRenderBoundingBox() {
            return InformationHandler.hasMechanicalView() ? INFINITE_EXTENT_AABB : super.getRenderBoundingBox();
        }

        @Override
        public void onLoad() {
            super.onLoad();
            onNodeLoad();
        }

        @Override
        public void onChunkUnloaded() {
            super.onChunkUnloaded();
            onNodeUnload();
        }

        @Override
        public void load(@NotNull CompoundTag nbt) {
            super.load(nbt);
            deserializePoses(nbt, "connectedPoses", connectedPoses);
            deserializePoses(nbt, "relativePoses", relativePoses);
        }

        @Override
        protected void saveAdditional(@NotNull CompoundTag nbt) {
            super.saveAdditional(nbt);
            serializePoses(nbt, "connectedPoses", connectedPoses);
            serializePoses(nbt, "relativePoses", relativePoses);
        }

        @Override
        public @NotNull CompoundTag getUpdateTag() {
            return serializePoses(super.getUpdateTag(), "connectedPoses", connectedPoses);
        }

        @Override
        public BlockEntity getSelf() {
            return this;
        }

        @Override
        public void setNetworkNode(NetworkNode node) {
            this.networkNode = node;
        }

        @Override
        public @Nullable NetworkNode getNetworkNode() {
            return networkNode;
        }

        @Override
        public Int2ObjectMap<Set<BlockPos>> getConnectedPoses() {
            return connectedPoses;
        }

        @Override
        public Int2ObjectMap<Set<BlockPos>> getRelativePoses() {
            return relativePoses;
        }
    }
}
