package org.confluence.mod.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class ActuatorsBlockEntity extends BlockEntity {
    private BlockState contain;
    private BlockEntity containEntity;

    public ActuatorsBlockEntity(BlockPos blockPos, BlockState blockState) {
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
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    private boolean isEmpty() {
        return containEntity == null || containEntity instanceof ActuatorsBlockEntity;
    }

    @SuppressWarnings("unchecked")
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ActuatorsBlockEntity blockEntity) {
        BlockEntity containEntity = blockEntity.getContainEntity();
        BlockState contain = blockEntity.getContain();
        if (containEntity != null && contain.getBlock() instanceof EntityBlock entityBlock) {
            BlockEntityTicker<BlockEntity> ticker = entityBlock.getTicker(level, contain, (BlockEntityType<BlockEntity>) containEntity.getType());
            if (ticker != null) ticker.tick(level, blockPos, contain, containEntity);
        }
    }
}
