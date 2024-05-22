package org.confluence.mod.event;

import com.lowdragmc.shimmer.client.light.LightManager;
import com.lowdragmc.shimmer.forge.event.ForgeShimmerReloadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.block.common.ColorfulTorchBlock;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.client.shimmer.DemonTorchColor;
import org.confluence.mod.client.shimmer.RainbowTorchColor;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ShimmerEvents {
    @SubscribeEvent
    public static void shimmerReload(ForgeShimmerReloadEvent event) {
        for (Torches torches : Torches.values()) {
            ColorfulTorchBlock block = torches.get();
            LightManager.INSTANCE.registerBlockLight(block, (blockState, blockPos) -> block.getColor());
        }
    }

    public static void doUpdateTorchColor() {
        RainbowTorchColor.INSTANCE.update();
        DemonTorchColor.INSTANCE.update();
    }
}
