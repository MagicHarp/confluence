package org.confluence.mod.block.functional.mechanical;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMechanicalBlock extends Block implements EntityBlock {
    public AbstractMechanicalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            PathService.INSTANCE.onBlockEntityUnload(entity);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    /**
     * 默认的执行,比如红石激活
     */
    public void execute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        execute(pState, pLevel, pPos, -1);
    }

    /**
     * 执行机械方块的可执行代码,并执行联系的方块
     *
     * @param pPos  方块自己的坐标
     * @param color 由什么颜色的连线执行
     */
    public void execute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int color) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            if (color == -1) { // 激活所有网络
                entity.networkNode.getNetworks().values().stream()
                    .flatMap(network -> network.getNodes().stream().map(NetworkNode::getBlockEntity))
                    .collect(Collectors.toSet())
                    .forEach(entity1 -> internalExecute(pLevel, pPos, entity1));
            } else {
                Network network = entity.networkNode.getNetwork(color);
                if (network != null) network.getNodes().stream()
                    .map(NetworkNode::getBlockEntity)
                    .collect(Collectors.toSet())
                    .forEach(entity1 -> internalExecute(pLevel, pPos, entity1));
            }
            executable(pState, pLevel, pPos);
        }
    }

    private void internalExecute(ServerLevel pLevel, BlockPos pPos, Entity entity) {
        BlockState blockState = entity.getBlockState();
        BlockPos blockPos = entity.getBlockPos();
        if (blockPos.equals(pPos)) return; // 被直接激活的方块最后再执行
        if (blockState.getBlock() instanceof AbstractMechanicalBlock block) {
            block.executable(blockState, pLevel, blockPos);
        }
    }

    /**
     * 定义机械方块的可执行代码
     */
    public abstract void executable(BlockState pState, ServerLevel pLevel, BlockPos pPos);

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide) return InteractionResult.CONSUME;
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.getItem() instanceof WrenchItem wrenchItem && pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            BlockPos storedPos = WrenchItem.readBlockPos(itemStack);
            if (storedPos == null) {
                WrenchItem.writeBlockPos(itemStack, pPos);
            } else if (pLevel.getBlockEntity(storedPos) instanceof Entity relatedEntity) {
                entity.connectTo(wrenchItem.color, storedPos);
                relatedEntity.connectTo(wrenchItem.color, pPos);
                PathService.INSTANCE.addToQueue(entity);
                WrenchItem.removeBlockPos(itemStack);
            }
            return InteractionResult.CONSUME;
        }
        WrenchItem.removeBlockPos(itemStack);
        return InteractionResult.PASS;
    }

    public static class Entity extends BlockEntity {
        private NetworkNode networkNode;
        final Int2ObjectMap<Set<BlockPos>> connectedPoses;

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.MECHANICAL_BLOCK_ENTITY.get(), pPos, pBlockState);
            this.connectedPoses = new Int2ObjectOpenHashMap<>();
        }

        @Override
        public void onLoad() {
            super.onLoad();
            if (level != null && !level.isClientSide) {
                PathService.INSTANCE.onBlockEntityLoad(this);
            }
        }

        @Override
        public void onChunkUnloaded() {
            super.onChunkUnloaded();
            if (level != null && !level.isClientSide) {
                PathService.INSTANCE.onBlockEntityUnload(this);
            }
        }

        public void load(@NotNull CompoundTag nbt) {
            super.load(nbt);
            connectedPoses.clear();
            CompoundTag compoundTag = nbt.getCompound("connectedPoses");
            for (String key : compoundTag.getAllKeys()) {
                if (compoundTag.get(key) instanceof ListTag listTag) {
                    int color = Integer.parseInt(key);
                    Set<BlockPos> posSet = new HashSet<>();
                    listTag.forEach(tag -> {
                        if (tag instanceof CompoundTag tag1) {
                            posSet.add(NbtUtils.readBlockPos(tag1));
                        }
                    });
                    connectedPoses.put(color, posSet);
                }
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
            CompoundTag compoundTag = new CompoundTag();
            for (Int2ObjectMap.Entry<Set<BlockPos>> entry : connectedPoses.int2ObjectEntrySet()) {
                ListTag listTag = new ListTag();
                for (BlockPos blockPos : entry.getValue()) {
                    listTag.add(NbtUtils.writeBlockPos(blockPos));
                }
                compoundTag.put(String.valueOf(entry.getIntKey()), listTag);
            }
            nbt.put("connectedPoses", compoundTag);
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

        public void connectTo(int color, BlockPos pos) {
            if (pos.equals(getBlockPos())) return;
            connectedPoses.computeIfAbsent(color, i -> new HashSet<>()).add(pos);
            markUpdated();
        }

        public void disconnectWith(int color, BlockPos pos) {
            if (pos.equals(getBlockPos())) return;
            Set<BlockPos> posSet = connectedPoses.get(color);
            if (posSet != null) {
                posSet.remove(pos);
                if (posSet.isEmpty()) {
                    connectedPoses.remove(color);
                }
            }
            markUpdated();
        }
    }
}
