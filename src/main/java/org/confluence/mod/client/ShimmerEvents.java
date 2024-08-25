package org.confluence.mod.client;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import com.lowdragmc.shimmer.forge.event.ForgeShimmerLoadConfigEvent;
import com.lowdragmc.shimmer.forge.event.ForgeShimmerReloadEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.Torches;
import org.confluence.mod.block.natural.BaseHerbBlock;
import org.confluence.mod.block.natural.herbs.DeathWeed;
import org.confluence.mod.block.natural.herbs.MoonshineGrass;
import org.confluence.mod.client.color.ColorPointLightData;
import org.confluence.mod.client.shimmer.DemonTorchColor;
import org.confluence.mod.client.shimmer.RainbowTorchColor;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ShimmerEvents {
    private static final ColorPointLight.Template FLAMEFLOWERS_LIGHT = new ColorPointLight.Template(4, 1, 0.86f, 0, 1);
    private static final ColorPointLight.Template DEATHWEED_LIGHT = new ColorPointLight.Template(BaseHerbBlock.BRIGHTNESS, 1, 0, 1, 1);
    private static final ColorPointLight.Template MOONSHINE_GRASS_LIGHT = new ColorPointLight.Template(BaseHerbBlock.BRIGHTNESS, 0, 0, 1, 1);
    private static final ColorPointLight.Template JUNGLE_SPORE_LIGHT = new ColorPointLight.Template(4, 0.7f, 0.8f, 0.16f, 1);
    public static final ColorPointLight.Template MUSHROOM_GRASS_BLOCK_LIGHT = new ColorPointLight.Template(5, 0, 0, 1, 1);

    @SubscribeEvent
    public static void loadConfig(ForgeShimmerLoadConfigEvent event) {
        event.event.addConfiguration(new ResourceLocation(MODID, "shimmer.json"));
    }

    @SubscribeEvent
    public static void reload(ForgeShimmerReloadEvent event) {
        for (Torches torches : Torches.values()) {
            if (torches.stand.get() instanceof Torches.UpdatingColorfulTorchBlock) {
                continue; // 跳过动态光源
            }
            Torches.ColorfulTorchBlock stand = torches.stand.get();
            LightManager.INSTANCE.registerBlockLight(stand, (blockState, blockPos) -> {
                ColorPointLightData data = stand.getColor();
                return new ColorPointLight.Template(data.radius(), data.r(), data.g(), data.b(), data.a());
            });
            Torches.ColorfulWallTorchBlock wall = torches.wall.get();
            LightManager.INSTANCE.registerBlockLight(wall, (blockState, blockPos) -> {
                ColorPointLightData data = wall.getColor();
                return new ColorPointLight.Template(data.radius(), data.r(), data.g(), data.b(), data.a());
            });
        }
        LightManager.INSTANCE.registerBlockLight(Torches.DEMON_TORCH.stand.get(), (blockState, pos) -> DemonTorchColor.INSTANCE);
        LightManager.INSTANCE.registerBlockLight(Torches.DEMON_TORCH.wall.get(), (blockState, pos) -> DemonTorchColor.INSTANCE);
        LightManager.INSTANCE.registerBlockLight(Torches.RAINBOW_TORCH.stand.get(), (blockState, pos) -> RainbowTorchColor.INSTANCE);
        LightManager.INSTANCE.registerBlockLight(Torches.RAINBOW_TORCH.wall.get(), (blockState, pos) -> RainbowTorchColor.INSTANCE);
        LightManager.INSTANCE.registerItemLight(Torches.DEMON_TORCH.item.get(), itemStack -> DemonTorchColor.INSTANCE);
        LightManager.INSTANCE.registerItemLight(Torches.RAINBOW_TORCH.item.get(), itemStack -> RainbowTorchColor.INSTANCE);

        LightManager.INSTANCE.registerBlockLight(ModBlocks.MOONSHINE_GRASS.get(), (blockState, blockPos) -> {
            MOONSHINE_GRASS_LIGHT.radius = ModBlocks.MOONSHINE_GRASS.get().getAge(blockState) == BaseHerbBlock.MAX_AGE ? blockState.getValue(MoonshineGrass.PROP_LIGHT) : 0;
            return MOONSHINE_GRASS_LIGHT;
        });
        LightManager.INSTANCE.registerBlockLight(ModBlocks.DEATHWEED.get(), (blockState, blockPos) -> {
            DEATHWEED_LIGHT.radius = ModBlocks.DEATHWEED.get().getAge(blockState) == BaseHerbBlock.MAX_AGE ? blockState.getValue(DeathWeed.PROP_LIGHT) : 0;
            return DEATHWEED_LIGHT;
        });
        LightManager.INSTANCE.registerBlockLight(ModBlocks.FLAMEFLOWERS.get(), (blockState, blockPos) -> {
            FLAMEFLOWERS_LIGHT.radius = ModBlocks.FLAMEFLOWERS.get().getAge(blockState) == BaseHerbBlock.MAX_AGE ? 4 : 0;
            return FLAMEFLOWERS_LIGHT;
        });
        LightManager.INSTANCE.registerBlockLight(ModBlocks.JUNGLE_SPORE.get(), (blockState, blockPos) -> JUNGLE_SPORE_LIGHT);
        LightManager.INSTANCE.registerBlockLight(ModBlocks.MUSHROOM_GRASS_BLOCK.get(), (blockState, blockPos) -> MUSHROOM_GRASS_BLOCK_LIGHT);
    }

    public static void doUpdateTorchColor() {
        RainbowTorchColor.INSTANCE.update();
        DemonTorchColor.INSTANCE.update();
    }
}
