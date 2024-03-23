package org.confluence.mod.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ConfluenceBlocks;
import org.jetbrains.annotations.NotNull;

public class ActuatorsBlockEntity extends BlockEntity {
    private BlockState contain;

    public ActuatorsBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ConfluenceBlocks.ACTUATORS_ENTITY.get(), blockPos, blockState);
        this.contain = Blocks.BEDROCK.defaultBlockState();
    }

    public BlockState getContain() {
        return contain;
    }

    public void setContain(BlockState contain) {
        this.contain = contain;
    }

    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        try {
            this.contain = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), nbt.getString("contain"), true).blockState();
        } catch (CommandSyntaxException e) {
            this.contain = Blocks.BEDROCK.defaultBlockState();
        }
    }

    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putString("contain", BlockStateParser.serialize(contain));
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("contain", BlockStateParser.serialize(contain));
        return nbt;
    }

    public void markUpdated() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}
