package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import org.confluence.mod.block.functional.StateProperties;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SwordInStoneBlock extends Block {
    public static final EnumProperty<StateProperties.SwordType> swordType = EnumProperty.create("sword_type", StateProperties.SwordType.class);

    public SwordInStoneBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak());
        this.registerDefaultState(this.getStateDefinition().any().setValue(swordType, StateProperties.SwordType.Null));
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        StateProperties.SwordType swordTypes = state.getValue(swordType);
        if (!level.isClientSide && swordTypes == StateProperties.SwordType.EnchantedSword && !player.isCreative()) {
            //TODO: 掉落附魔剑
        } else if (!level.isClientSide && swordTypes == StateProperties.SwordType.Terragrim && !player.isCreative()) {
            //TODO: 掉落泰拉魔刃
        } else if (!level.isClientSide && swordTypes == StateProperties.SwordType.RottenSword && !player.isCreative()) {
            popResource(level, pos, new ItemStack(Items.AIR));
        } else {
            popResource(level, pos, new ItemStack(Items.AIR));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(swordType);
    }
}
