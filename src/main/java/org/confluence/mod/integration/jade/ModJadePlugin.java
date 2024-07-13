package org.confluence.mod.integration.jade;

import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.functional.DeathChestBlock;
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
            if (accessor instanceof BlockAccessor blockAccessor) {
                Player player = accessor.getPlayer();
                if (!player.isCreative() && blockAccessor.getBlockEntity() instanceof DeathChestBlock.Entity entity) { // 隐藏死人箱
                    CompoundTag serverData = blockAccessor.getServerData();
                    serverData.putString("givenName", "{\"translate\":\"block.confluence.base_chest_block." + entity.variant.getSerializedName() + "\"}");
                    return registration.blockAccessor().from(blockAccessor).blockEntity(entity).serverData(serverData).build();
                }
                BlockState blockState = blockAccessor.getBlockState();
                if (blockAccessor.getBlock() instanceof IRevealed && !RevelationRegistry.isVisibleTo(blockState, player)) { // 隐藏Clocked方块
                    return registration.blockAccessor().from(blockAccessor).blockState(RevelationRegistry.getCloak(blockState)).build();
                }
            }
            return accessor;
        });
    }
}
