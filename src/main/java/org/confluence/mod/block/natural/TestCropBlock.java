package org.confluence.mod.block.natural;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class TestCropBlock extends BaseCropBlock{
    public TestCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.TEST_SEED.get();
    }

    @Override
    public Set<Block> getCanPlaceBlocks() {
        return Set.of(Blocks.FARMLAND);
    }

    @Override
    public List<ItemStack> getCropDrops() {
        return List.of(new ItemStack(Materials.GEL.get(), 16));
    }
}
