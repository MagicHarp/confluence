package org.confluence.mod.block.functional;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.mixin.accessor.BlockItemAccessor;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ActuatorsBlock extends Block implements EntityBlock, IMechanical, CustomModel, CustomItemModel {
    public ActuatorsBlock() {
        super(BlockBehaviour.Properties.of()
            .pushReaction(PushReaction.BLOCK).mapColor(MapColor.COLOR_ORANGE)
            .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false)
            .isRedstoneConductor((blockState, blockGetter, blockPos) -> true)
        );
        registerDefaultState(stateDefinition.any()
            .setValue(StateProperties.DRIVE, false)
            .setValue(StateProperties.VISIBLE, false)
        );
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(StateProperties.DRIVE, StateProperties.VISIBLE);
    }

    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof Entity actuatorsBlockEntity) {
            return isCollisionShapeFullBlock(actuatorsBlockEntity.getContain(), blockGetter, blockPos) ? 0.2F : 1.0F;
        }
        return 0.2F;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new Entity(blockPos, blockState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blockHitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof ActuatorsBlock) return InteractionResult.PASS;
            BlockState resultState = block.getStateForPlacement(new BlockPlaceContext(level, player, hand, itemStack, blockHitResult));
            if (resultState != null && level.getBlockEntity(blockPos) instanceof Entity blockEntity) {
                if (blockEntity.getContain().is(block)) return InteractionResult.PASS;
                resultState = ((BlockItemAccessor) blockItem).callUpdateBlockStateFromTag(blockPos, level, itemStack, resultState);
                BlockEntity resultEntity = null;
                if (block instanceof EntityBlock entityBlock) {
                    resultEntity = entityBlock.newBlockEntity(blockPos, resultState);
                    CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack);
                    if (compoundTag != null && resultEntity != null && (!resultEntity.onlyOpCanSetNbt() || player.canUseGameMasterBlocks())) {
                        CompoundTag compoundtag1 = resultEntity.saveWithoutMetadata();
                        resultEntity.load(compoundtag1.merge(compoundTag));
                    }
                }
                blockEntity.setContain(resultState, resultEntity);
                if (!player.getAbilities().instabuild) itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    public void neighborChanged(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Block block, @NotNull BlockPos blockPos1, boolean b) {
        if (level.isClientSide) return;
        boolean drive = blockState.getValue(StateProperties.DRIVE);
        if (drive == level.hasNeighborSignal(blockPos)) return;
        if (drive) {
            level.scheduleTick(blockPos, this, 1);
        } else {
            level.setBlock(blockPos, blockState.cycle(StateProperties.DRIVE), Block.UPDATE_CLIENTS);
        }
    }

    public void tick(BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (blockState.getValue(StateProperties.DRIVE) && !serverLevel.hasNeighborSignal(blockPos)) {
            serverLevel.setBlock(blockPos, blockState.cycle(StateProperties.DRIVE), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (blockGetter.getBlockEntity(blockPos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getShape(blockGetter, blockPos, context);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (blockGetter.getBlockEntity(blockPos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getVisualShape(blockGetter, blockPos, context);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getBlockSupportShape(blockGetter, blockPos);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getOcclusionShape(blockGetter, blockPos);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (!blockState.getValue(StateProperties.DRIVE)) {
            if (blockGetter.getBlockEntity(blockPos) instanceof Entity actuatorsBlockEntity) {
                BlockState contain = actuatorsBlockEntity.getContain();
                if (contain.getBlock() instanceof EchoBlock echoBlock) {
                    return echoBlock.getCollisionShape(contain, blockGetter, blockPos, context);
                }
                return contain.getCollisionShape(blockGetter, blockPos);
            }
        }
        return Shapes.empty();
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState pState, @NotNull Player pPlayer, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getDestroyProgress(pPlayer, pLevel, pPos);
        }
        return super.getDestroyProgress(pState, pPlayer, pLevel, pPos);
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable net.minecraft.world.entity.Entity entity) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getFriction(level, pos, entity);
        }
        return super.getFriction(state, level, pos, entity);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getLightEmission(level, pos);
        }
        return super.getLightEmission(state, level, pos);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getFireSpreadSpeed(level, pos, direction);
        }
        return super.getFireSpreadSpeed(state, level, pos, direction);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getEnchantPowerBonus(level, pos);
        }
        return super.getEnchantPowerBonus(state, level, pos);
    }

    @Override
    public @Nullable BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getAdjacentBlockPathType(level, pos, mob, originalType);
        }
        return super.getAdjacentBlockPathType(state, level, pos, mob, originalType);
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getBlockPathType(level, pos, mob);
        }
        return super.getBlockPathType(state, level, pos, mob);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getExplosionResistance(level, pos, explosion);
        }
        return super.getExplosionResistance(state, level, pos, explosion);
    }

    @Override
    public float @Nullable [] getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getBeaconColorMultiplier(level, pos, beaconPos);
        }
        return super.getBeaconColorMultiplier(state, level, pos, beaconPos);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof Entity actuatorsBlockEntity) {
            BlockState contain = actuatorsBlockEntity.getContain();
            return contain.getFlammability(level, pos, direction);
        }
        return super.getFlammability(state, level, pos, direction);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull PathComputationType type) {
        return !blockState.getValue(StateProperties.DRIVE);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel && pLevel.getBlockEntity(pPos) instanceof Entity blockEntity) {
            BlockState contain = blockEntity.getContain();
            if (!contain.is(Blocks.BEDROCK)) serverLevel.setBlockAndUpdate(pPos, contain);
            BlockEntity containEntity = blockEntity.getContainEntity();
            if (containEntity == null) pLevel.removeBlockEntity(pPos);
            else serverLevel.setBlockEntity(containEntity);
        } else super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return ModUtils.getTicker(pBlockEntityType, ModBlocks.ACTUATORS_ENTITY.get(), Entity::tick);
    }

    public static void blockItemPlace(PlayerInteractEvent.RightClickBlock event) {
        boolean success = false;
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        Level level = event.getLevel();
        if (!player.isCrouching() && itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ActuatorsBlock) {
            BlockPos blockPos = event.getPos();
            BlockState blockState = level.getBlockState(blockPos);
            if (!blockState.is(ModBlocks.ACTUATORS.get()) && blockState.getDestroySpeed(level, blockPos) >= 0.0F) {
                BlockEntity entity = level.getBlockEntity(blockPos);
                if (entity != null) {
                    entity = BlockEntity.loadStatic(blockPos, blockState, entity.saveWithFullMetadata());
                    level.removeBlockEntity(blockPos);
                }
                level.setBlockAndUpdate(blockPos, ModBlocks.ACTUATORS.get().defaultBlockState());
                if (level.getBlockEntity(blockPos) instanceof Entity blockEntity) {
                    blockEntity.setContain(blockState, entity);
                    if (!player.getAbilities().instabuild) itemStack.shrink(1);
                    success = true;
                }
            }
        }
        if (success) {
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
        }
        player.swing(event.getHand());
    }

    public static class Entity extends BlockEntity {
        private BlockState contain;
        private BlockEntity containEntity;

        public Entity(BlockPos blockPos, BlockState blockState) {
            super(ModBlocks.ACTUATORS_ENTITY.get(), blockPos, blockState);
            this.contain = Blocks.BEDROCK.defaultBlockState();
        }

        public BlockState getContain() {
            return contain;
        }

        public BlockEntity getContainEntity() {
            return containEntity;
        }

        public void setContain(BlockState contain, BlockEntity containEntity) {
            this.contain = contain;
            this.containEntity = contain.hasBlockEntity() ? containEntity : null;
            markUpdated();
        }

        public void load(@NotNull CompoundTag nbt) {
            super.load(nbt);
            try {
                this.contain = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), nbt.getString("contain"), true).blockState();
                if (nbt.contains("containEntity")) {
                    CompoundTag tag = nbt.getCompound("containEntity");
                    this.containEntity = tag.isEmpty() ? null : BlockEntity.loadStatic(getBlockPos(), contain, tag);
                } else {
                    this.containEntity = null;
                }
            } catch (CommandSyntaxException e) {
                this.contain = Blocks.BEDROCK.defaultBlockState();
                this.containEntity = null;
            }
        }

        protected void saveAdditional(@NotNull CompoundTag nbt) {
            super.saveAdditional(nbt);
            nbt.putString("contain", BlockStateParser.serialize(contain));
            nbt.put("containEntity", isEmpty() ? new CompoundTag() : containEntity.saveWithFullMetadata());
        }

        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        public @NotNull CompoundTag getUpdateTag() {
            CompoundTag nbt = new CompoundTag();
            nbt.putString("contain", BlockStateParser.serialize(contain));
            nbt.put("containEntity", isEmpty() ? new CompoundTag() : containEntity.getUpdateTag());
            return nbt;
        }

        public void markUpdated() {
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
            }
        }

        private boolean isEmpty() {
            return containEntity == null || containEntity instanceof Entity;
        }

        @SuppressWarnings("unchecked")
        public static void tick(Level level, BlockPos blockPos, BlockState blockState, Entity blockEntity) {
            BlockEntity containEntity = blockEntity.getContainEntity();
            BlockState contain = blockEntity.getContain();
            if (containEntity != null && contain.getBlock() instanceof EntityBlock entityBlock) {
                BlockEntityTicker<BlockEntity> ticker = entityBlock.getTicker(level, contain, (BlockEntityType<BlockEntity>) containEntity.getType());
                if (ticker != null) ticker.tick(level, blockPos, contain, containEntity);
            }
        }
    }
}
