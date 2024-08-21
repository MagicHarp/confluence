package org.confluence.mod.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.client.model.curio.*;
import org.confluence.mod.client.renderer.curio.*;
import org.confluence.mod.item.curio.CurioItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class CuriosClient {
    public static void registerRenderers() {
        CuriosRendererRegistry.register(CurioItems.SPECTRE_GOGGLES.get(), SpectreGogglesRenderer::new);
        CuriosRendererRegistry.register(CurioItems.WORM_SCARF.get(), WormScarfRenderer::new);
        CuriosRendererRegistry.register(CurioItems.TERRASPARK_BOOTS.get(), TerrasparkBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.DUNERIDER_BOOTS.get(), DuneriderBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.MAGMA_SKULL.get(), MagmaSkullRenderer::new);
        CuriosRendererRegistry.register(CurioItems.FLURRY_BOOTS.get(), FlurryBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.HERMES_BOOTS.get(), HermesBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.OBSIDIAN_SKULL.get(), ObsidianSkullRenderer::new);
        CuriosRendererRegistry.register(CurioItems.SHIELD_OF_CTHULHU.get(), ShieldOfCthulhuRenderer::new);
        CuriosRendererRegistry.register(CurioItems.STEP_STOOL.get(), StepStoolRenderer::new);

        // Gecko x Curio
        CuriosRendererRegistry.register(CurioItems.FLEDGLING_WINGS.get(), FledglingWingsRenderer::new);
    }

    public static void registerLayers(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> layerDefinition) {
        layerDefinition.accept(SpectreGogglesModel.LAYER_LOCATION, SpectreGogglesModel::createBodyLayer);
        layerDefinition.accept(WormScarfModel.LAYER_LOCATION, WormScarfModel::createBodyLayer);
        layerDefinition.accept(TerrasparkBootsModel.LAYER_LOCATION, TerrasparkBootsModel::createBodyLayer);
        layerDefinition.accept(DuneriderBootsModel.LAYER_LOCATION, DuneriderBootsModel::createBodyLayer);
        layerDefinition.accept(MagmaSkullModel.LAYER_LOCATION, MagmaSkullModel::createBodyLayer);
        layerDefinition.accept(FlurryBootsModel.LAYER_LOCATION, FlurryBootsModel::createBodyLayer);
        layerDefinition.accept(HermesBootsModel.LAYER_LOCATION, HermesBootsModel::createBodyLayer);
        layerDefinition.accept(ObsidianSkullModel.LAYER_LOCATION, ObsidianSkullModel::createBodyLayer);
        layerDefinition.accept(ShieldOfCthulhuModel.LAYER_LOCATION, ShieldOfCthulhuModel::createBodyLayer);
        layerDefinition.accept(StepStoolModel.LAYER_LOCATION, StepStoolModel::createBodyLayer);
    }
}
