package org.confluence.mod.event;

import com.lowdragmc.shimmer.client.light.LightManager;
import com.lowdragmc.shimmer.forge.event.ForgeShimmerLoadConfigEvent;
import com.lowdragmc.shimmer.forge.event.ForgeShimmerReloadEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.client.shimmer.DemonTorchColor;
import org.confluence.mod.client.shimmer.RainbowTorchColor;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ShimmerEvents {
    @SubscribeEvent
    public static void loadConfig(ForgeShimmerLoadConfigEvent event) {
        event.event.addConfiguration(new ResourceLocation(MODID, "shimmer.json"));
    }

    @SubscribeEvent
    public static void reload(ForgeShimmerReloadEvent event) {
        for (Torches torches : Torches.values()) {
            Torches.ColorfulTorchBlock stand = torches.stand.get();
            LightManager.INSTANCE.registerBlockLight(stand, (blockState, blockPos) -> stand.getColor());
            Torches.ColorfulWallTorchBlock wall = torches.wall.get();
            LightManager.INSTANCE.registerBlockLight(wall, (blockState, blockPos) -> wall.getColor());
        }
        LightManager.INSTANCE.registerBlockLight(ModBlocks.MOONSHINE_GRASS.get(), (blockState, blockPos) -> ModBlocks.MOONSHINE_GRASS.get().getColor(blockState));
        LightManager.INSTANCE.registerBlockLight(ModBlocks.DEATHWEED.get(), (blockState, blockPos) -> ModBlocks.DEATHWEED.get().getColor(blockState));
        LightManager.INSTANCE.registerBlockLight(ModBlocks.FLAMEFLOWERS.get(), (blockState, blockPos) -> ModBlocks.FLAMEFLOWERS.get().getColor(blockState));

        LightManager.INSTANCE.registerItemLight(Torches.DEMON_TORCH.item.get(), itemStack -> DemonTorchColor.INSTANCE);
        LightManager.INSTANCE.registerItemLight(Torches.RAINBOW_TORCH.item.get(), itemStack -> RainbowTorchColor.INSTANCE);
    }

    public static void doUpdateTorchColor() {
        RainbowTorchColor.INSTANCE.update();
        DemonTorchColor.INSTANCE.update();
    }
}
