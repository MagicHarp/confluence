package org.confluence.mod.block.functional.mechanical;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
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
import org.confluence.mod.client.handler.InformationHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public abstract class AbstractMechanicalBlock extends Block implements EntityBlock {
    public AbstractMechanicalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            PathService.INSTANCE.onBlockEntityUnload(entity);
            // 根据relativePoses,断开与该方块的连接
            for (Int2ObjectMap.Entry<Set<BlockPos>> entry : entity.relativePoses.int2ObjectEntrySet()) {
                int color = entry.getIntKey();
                for (BlockPos pos : entry.getValue()) {
                    if (pLevel.getBlockEntity(pos) instanceof Entity entity1) {
                        Set<BlockPos> posSet = entity1.connectedPoses.get(color);
                        if (posSet == null) continue;
                        posSet.remove(pPos);
                        if (posSet.isEmpty()) entity1.connectedPoses.remove(color);
                        entity1.markUpdated();
                    }
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    /**
     * 默认的执行,比如红石激活
     */
    public void execute(BlockState pState, ServerLevel pLevel, BlockPos pPos, boolean hasSignal) {
        execute(pState, pLevel, pPos, -1, hasSignal);
    }

    /**
     * 执行机械方块的可执行代码,并执行联系的方块
     *
     * @param pPos  方块自己的坐标
     * @param color 由什么颜色的连线执行
     */
    public void execute(BlockState pState, ServerLevel pLevel, BlockPos pPos, int color, boolean hasSignal) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            if (color == -1) { // 激活所有网络
                entity.networkNode.getNetworks().values().stream()
                    .filter(network -> hasSignal != network.hasSignal()) // 只处理不同的信号
                    .peek(network -> network.setSignal(hasSignal))
                    .flatMap(network -> network.getNodes().stream().map(NetworkNode::getBlockEntity))
                    .collect(Collectors.toSet())
                    .forEach(entity1 -> internalExecute(pLevel, pPos, entity1, hasSignal));
            } else {
                Network network = entity.networkNode.getNetwork(color);
                if (network != null && hasSignal != network.hasSignal()) { // 同样只处理不同的信号
                    network.setSignal(hasSignal);
                    network.getNodes().stream()
                        .map(NetworkNode::getBlockEntity)
                        .collect(Collectors.toSet())
                        .forEach(entity1 -> internalExecute(pLevel, pPos, entity1, hasSignal));
                }
            }
            onExecute(pState, pLevel, pPos);
        }
    }

    private void internalExecute(ServerLevel pLevel, BlockPos pPos, Entity entity, boolean hasSignal) {
        BlockState blockState = entity.getBlockState();
        BlockPos blockPos = entity.getBlockPos();
        if (blockPos.equals(pPos)) return; // 被直接激活的方块最后再执行
        if (blockState.getBlock() instanceof AbstractMechanicalBlock block) {
            if (hasSignal) {
                block.onExecute(blockState, pLevel, blockPos);
            } else {
                block.onUnExecute(blockState, pLevel, blockPos);
            }
        }
    }

    /**
     * 正脉冲执行的代码
     */
    public abstract void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos);

    /**
     * 负脉冲执行的代码
     */
    public void onUnExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    public static class Entity extends BlockEntity {
        private NetworkNode networkNode;
        public final Int2ObjectMap<Set<BlockPos>> connectedPoses; // 主动连接,用于寻路与渲染
        public final Int2ObjectMap<Set<BlockPos>> relativePoses; // 被动连接,用于朔源

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
            serializePoses(nbt, "connectedPoses", connectedPoses);
            serializePoses(nbt, "relativePoses", relativePoses);
        }

        private void serializePoses(@NotNull CompoundTag nbt, String posesKey, Int2ObjectMap<Set<BlockPos>> map) {
            map.clear();
            CompoundTag compoundTag = nbt.getCompound(posesKey);
            for (String key : compoundTag.getAllKeys()) {
                if (compoundTag.get(key) instanceof ListTag listTag) {
                    int color = Integer.parseInt(key);
                    Set<BlockPos> posSet = new HashSet<>();
                    listTag.forEach(tag -> {
                        if (tag instanceof CompoundTag tag1) {
                            posSet.add(NbtUtils.readBlockPos(tag1));
                        }
                    });
                    map.put(color, posSet);
                }
            }
        }

        protected void saveAdditional(@NotNull CompoundTag nbt) {
            super.saveAdditional(nbt);
            deserializePoses(nbt, "connectedPoses", connectedPoses);
            deserializePoses(nbt, "relativePoses", relativePoses);
        }

        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        public @NotNull CompoundTag getUpdateTag() {
            return deserializePoses(super.getUpdateTag(), "connectedPoses", connectedPoses);
        }

        public CompoundTag deserializePoses(CompoundTag nbt, String posesKey, Int2ObjectMap<Set<BlockPos>> map) {
            CompoundTag compoundTag = new CompoundTag();
            for (Int2ObjectMap.Entry<Set<BlockPos>> entry : map.int2ObjectEntrySet()) {
                ListTag listTag = new ListTag();
                for (BlockPos blockPos : entry.getValue()) {
                    listTag.add(NbtUtils.writeBlockPos(blockPos));
                }
                compoundTag.put(String.valueOf(entry.getIntKey()), listTag);
            }
            nbt.put(posesKey, compoundTag);
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

        public void connectTo(int color, BlockPos relatedPos, Entity related) {
            if (relatedPos.equals(getBlockPos())) return;
            connectedPoses.computeIfAbsent(color, i -> new HashSet<>()).add(relatedPos);
            markUpdated();
            related.relativePoses.computeIfAbsent(color, i -> new HashSet<>()).add(getBlockPos());
            related.markUpdated();
        }

        public void disconnectWith(int color, BlockPos relatedPos, Entity related) {
            if (relatedPos.equals(getBlockPos())) return;
            Set<BlockPos> posSet = connectedPoses.get(color);
            if (posSet != null) {
                posSet.remove(relatedPos);
                if (posSet.isEmpty()) connectedPoses.remove(color);
                markUpdated();
            }
            posSet = related.relativePoses.get(color);
            if (posSet != null) {
                posSet.remove(getBlockPos());
                if (posSet.isEmpty()) related.relativePoses.remove(color);
                related.markUpdated();
            }
        }
    }
}
