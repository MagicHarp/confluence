package org.confluence.mod.block.functional;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.functional.network.INetworkBlock;
import org.confluence.mod.block.functional.network.INetworkEntity;
import org.confluence.mod.block.functional.network.NetworkNode;
import org.confluence.mod.client.handler.InformationHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@SuppressWarnings("deprecation")
public abstract class AbstractMechanicalBlock extends Block implements EntityBlock, INetworkBlock {
    public AbstractMechanicalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        onNodeRemove(pState, pLevel, pPos, pNewState);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    public static class Entity extends BlockEntity implements INetworkEntity {
        private NetworkNode networkNode;
        private final Int2ObjectMap<Set<BlockPos>> connectedPoses;
        private final Int2ObjectMap<Set<BlockPos>> relativePoses;

        public Entity(BlockEntityType<? extends Entity> entityType, BlockPos pPos, BlockState pBlockState) {
            super(entityType, pPos, pBlockState);
            this.connectedPoses = new Int2ObjectOpenHashMap<>();
            this.relativePoses = new Int2ObjectOpenHashMap<>();
        }

        public Entity(BlockPos pPos, BlockState pBlockState) {
            this(ModBlocks.MECHANICAL_BLOCK_ENTITY.get(), pPos, pBlockState);
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public AABB getRenderBoundingBox() { // 使其能在屏幕外渲染
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
        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
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

        @Nullable
        @Override
        public NetworkNode getNetworkNode() {
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
