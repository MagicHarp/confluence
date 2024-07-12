package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.datagen.limit.CustomName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class BaseChestBlock extends ChestBlock implements CustomModel, CustomItemModel, CustomName {
    public BaseChestBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.CHEST), ModBlocks.BASE_CHEST_BLOCK_ENTITY::get);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new Entity(pPos, pState);
    }

    @Override
    public void setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull LivingEntity pPlacer, @NotNull ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CompoundTag tag = pStack.getTag();
        if (tag == null) return;
        if (pLevel.getBlockEntity(pPos) instanceof Entity entity) {
            entity.variant = Variant.byId(tag.getInt("VariantId"));
            entity.locked = tag.getBoolean("Locked");
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.getBlockEntity(pPos) instanceof Entity entity && entity.locked){
            return InteractionResult.PASS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        ChestType chesttype = ChestType.SINGLE;
        Direction direction = pContext.getHorizontalDirection().getOpposite(); // 箱子朝向
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = pContext.isSecondaryUseActive();
        Direction direction1 = pContext.getClickedFace();
        if (direction1.getAxis().isHorizontal() && flag) {
            Direction direction2 = candidatePartnerFacing(pContext, direction1.getOpposite());
            if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
                direction = direction2;
                chesttype = direction2.getCounterClockWise() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
            }
        }

        ItemStack itemStack = pContext.getItemInHand();
            boolean locked =itemStack.getTag() != null&& itemStack.getTag().getBoolean("Locked");
            if (chesttype == ChestType.SINGLE && !flag) {
                if (direction == candidatePartnerFacing(pContext, direction.getClockWise())) {
                    chesttype = ChestType.LEFT;
                } else if (direction == candidatePartnerFacing(pContext, direction.getCounterClockWise())) {
                    chesttype = ChestType.RIGHT;
                }
            }
        return defaultBlockState().setValue(FACING, direction).setValue(TYPE, chesttype).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @javax.annotation.Nullable
    private Direction candidatePartnerFacing(BlockPlaceContext pContext, Direction pDirection) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pDirection));
        return blockstate.is(this) && blockstate.getValue(TYPE) == ChestType.SINGLE ? blockstate.getValue(FACING) : null;
    }

    public static ItemStack setData(ItemStack itemStack, Variant variant, boolean locked) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("VariantId", variant.id);
        tag.putBoolean("Locked", locked);
        String info = (locked ? "locked" : "unlocked") + "_" + variant.name;
        itemStack.setHoverName(Component.translatable("block.confluence.base_chest_block." + info));
        return itemStack;
    }

    @Override
    public @Nullable String getGenName() {
        return null;
    }

    public static class Entity extends ChestBlockEntity {
        public Variant variant;
        boolean locked;

        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.BASE_CHEST_BLOCK_ENTITY.get(), pPos, pBlockState);
        }

        public void unlock() {
            this.locked = false;
            markUpdated();
        }

        public boolean isLocked() {
            return locked;
        }

        @Override
        public boolean canOpen(@NotNull Player pPlayer) {
            return !locked && super.canOpen(pPlayer);
        }

        @Override
        public void load(@NotNull CompoundTag pTag) {
            super.load(pTag);
            this.variant = Variant.byId(pTag.getInt("VariantId"));
            this.locked = pTag.getBoolean("Locked");
        }

        @Override
        protected void saveAdditional(@NotNull CompoundTag pTag) {
            super.saveAdditional(pTag);
            pTag.putInt("VariantId", variant.id);
            pTag.putBoolean("Locked", locked);
        }

        @Override
        public @NotNull CompoundTag getUpdateTag() {
            CompoundTag updateTag = super.getUpdateTag();
            updateTag.putBoolean("Locked", locked);
            return updateTag;
        }

        @Override
        public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        public void markUpdated() {
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
            }
        }
    }

    public enum Variant implements StringRepresentable {
        GOLDEN(0, "golden");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        private final int id;
        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public static Variant byId(int pId) {
            return BY_ID.apply(pId);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
