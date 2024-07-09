package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
