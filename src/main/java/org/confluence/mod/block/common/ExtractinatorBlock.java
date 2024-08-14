package org.confluence.mod.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.confluence.mod.misc.ModLootTables;
import org.confluence.mod.misc.ModTags;
import org.jetbrains.annotations.NotNull;

public class ExtractinatorBlock extends Block {
    public ExtractinatorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack item = pPlayer.getItemInHand(pHand);

        if (pLevel instanceof ServerLevel level) {
            LootParams lootparams = new LootParams.Builder(level)
                    .withParameter(LootContextParams.ORIGIN, pPlayer.position())
                    .withParameter(LootContextParams.THIS_ENTITY, pPlayer)
                    .create(LootContextParamSets.GIFT);
            LootTable loottable;
            if (item.is(ModTags.Items.DESERT_FOSSIL)) {
                loottable = level.getServer().getLootData().getLootTable(ModLootTables.E_DF);
            } else if (item.is(ModTags.Items.GRAVEL)) {
                loottable = level.getServer().getLootData().getLootTable(ModLootTables.E_G);
            } else if (item.is(ModTags.Items.JUNK)) {
                loottable = level.getServer().getLootData().getLootTable(ModLootTables.E_J);
            } else if (item.is(ModTags.Items.SLUSH)) {
                loottable = level.getServer().getLootData().getLootTable(ModLootTables.E_S);
            } else {
                return InteractionResult.PASS;
            }

            SimpleContainer inventory = new SimpleContainer(10);
            int p = 0;
            for (ItemStack loot : loottable.getRandomItems(lootparams)) {
                inventory.setItem(p, loot);
                p++;
                Containers.dropContents(level, pPos.below(-1), inventory);
            }

            pPlayer.getItemInHand(pHand).setCount(item.getCount() - 1);
        }
        return InteractionResult.SUCCESS;
    }
}
