package org.confluence.mod.integration.jade;

import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.block.reveal.IRevealed;
import snownee.jade.api.*;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(MechanicalComponentProvider.INSTANCE, AbstractMechanicalBlock.Entity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MechanicalComponentProvider.INSTANCE, AbstractMechanicalBlock.class);
        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (accessor instanceof BlockAccessor blockAccessor && blockAccessor.getBlock() instanceof IRevealed) {
                BlockState blockState = blockAccessor.getBlockState();
                if (!RevelationRegistry.isVisibleTo(blockState, accessor.getPlayer())) {
                    return registration.blockAccessor().from(blockAccessor).blockState(RevelationRegistry.getCloak(blockState)).build();
                }
            }
            return accessor;
        });
    }
}
