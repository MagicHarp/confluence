package org.confluence.mod.block.natural;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class StellarBlossomBlock extends BaseCropBlock{
    public StellarBlossomBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }
    private static final Random RANDOM = new Random();

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.STELLAR_BLOSSOM_SEED.get();
    }

    @Override
    public Set<Block> getCanPlaceBlocks() {
        return Set.of(ModBlocks.CLOUD_BLOCK.get());
    }


    @Override
    public List<ItemStack> getCropDrops() {
        int dropCount = getDropCountBasedOnChance();
        return List.of(new ItemStack(Materials.STAR_PETALS.get(), dropCount));
    }

    private int getDropCountBasedOnChance() {
        int chance = RANDOM.nextInt(100) + 1; // Generate a number between 1 and 100

        if (chance <= 20) {
            return 1; // 20% chance to drop 1 petal
        } else if (chance <= 75) {
            return 2; // 50% chance to drop 2 petals (cumulative chance 75%)
        } else if (chance <= 90) {
            return 3; // 15% chance to drop 3 petals (cumulative chance 90%)
        } else if (chance <= 100) {
            return 4; // 10% chance to drop 4 petals (cumulative chance 100%)
        } else {
            return 5; // 5% chance to drop 5 petals
        }
    }
}

