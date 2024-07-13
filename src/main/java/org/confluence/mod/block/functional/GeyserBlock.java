package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;

public class GeyserBlock extends AbstractMechanicalBlock implements CustomModel, CustomItemModel { // 热喷泉
    public GeyserBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {

    }
}
