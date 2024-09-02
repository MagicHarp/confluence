package org.confluence.mod.block.natural;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class CloudWeaverBlock extends BaseCropBlock{
    public CloudWeaverBlock(Properties pProperties) {
        super(pProperties);
    }
    private static final Random RANDOM = new Random();

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.CLOUDWEAVER_SEED.get();
    }

    @Override
    public Set<Block> getCanPlaceBlocks() {
        return Set.of(ModBlocks.CLOUD_BLOCK.get());
    }


    @Override
    public List<ItemStack> getCropDrops() {
        int dropCount = getDropCountBasedOnChance();
        return List.of(new ItemStack(Materials.WEAVING_CLOUD_COTTON.get(), dropCount));
    }

    private int getDropCountBasedOnChance() {
        int chance = RANDOM.nextInt(100) + 1; // Generate a number between 1 and 100

        if (chance <= 50) {
            return 2; // 50% chance to drop 2 petals
        } else {
            return 3; // 50% chance to drop 3 petals
        }
    }
}

