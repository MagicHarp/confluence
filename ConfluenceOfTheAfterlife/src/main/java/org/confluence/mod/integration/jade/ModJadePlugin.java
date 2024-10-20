package org.confluence.mod.integration.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
//        registration.registerBlockDataProvider(MechanicalComponentProvider.INSTANCE, AbstractMechanicalBlock.Entity.class);
//        registration.registerBlockDataProvider(MechanicalComponentProvider.INSTANCE, DeathChestBlock.Entity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
//        registration.registerBlockComponent(MechanicalComponentProvider.INSTANCE, AbstractMechanicalBlock.class);
//        registration.registerBlockComponent(MechanicalComponentProvider.INSTANCE, DeathChestBlock.class);
//        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
//            if (accessor instanceof BlockAccessor blockAccessor) {
//                Player player = accessor.getPlayer();
//                if (!player.isCreative() && blockAccessor.getBlockEntity() instanceof DeathChestBlock.Entity entity) { // 隐藏死人箱
//                    CompoundTag serverData = blockAccessor.getServerData();
//                    serverData.putString("givenName", "{\"translate\":\"block.confluence.base_chest_block." + entity.variant.getSerializedName() + "\"}");
//                    return registration.blockAccessor().from(blockAccessor).serverData(serverData).build();
//                }
//                BlockState blockState = blockAccessor.getBlockState();
//                if (blockAccessor.getBlock() instanceof IRevealed && !RevelationRegistry.isVisibleTo(blockState, player)) { // 隐藏Clocked方块
//                    return registration.blockAccessor().from(blockAccessor).blockState(RevelationRegistry.getCloak(blockState)).build();
//                }
//            }
//            return accessor;
//        });
    }
}
