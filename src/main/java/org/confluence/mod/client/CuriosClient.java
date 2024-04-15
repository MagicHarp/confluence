package org.confluence.mod.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.confluence.mod.client.model.curio.*;
import org.confluence.mod.client.renderer.curio.*;
import org.confluence.mod.item.curio.CurioItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CuriosClient {
    public static void registerRenderers() {
        CuriosRendererRegistry.register(CurioItems.SPECTRE_GOGGLES.get(), SpectreGogglesRenderer::new);
        CuriosRendererRegistry.register(CurioItems.TERRASPARK_BOOTS.get(), TerrasparkBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.DUNERIDER_BOOTS.get(), DuneriderBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.MAGMA_SKULL.get(), MagmaSkullRenderer::new);
        CuriosRendererRegistry.register(CurioItems.FLURRY_BOOTS.get(), FlurryBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.HERMES_BOOTS.get(), HermesBootsRenderer::new);
        CuriosRendererRegistry.register(CurioItems.OBSIDIAN_SKULL.get(), ObsidianSkullRenderer::new);
    }

    public static void registerLayers(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> layerDefinition) {
        layerDefinition.accept(SpectreGogglesModel.LAYER_LOCATION, SpectreGogglesModel::createBodyLayer);
        layerDefinition.accept(TerrasparkBootsModel.LAYER_LOCATION, TerrasparkBootsModel::createBodyLayer);
        layerDefinition.accept(DuneriderBootsModel.LAYER_LOCATION, DuneriderBootsModel::createBodyLayer);
        layerDefinition.accept(MagmaSkullModel.LAYER_LOCATION, MagmaSkullModel::createBodyLayer);
        layerDefinition.accept(FlurryBootsModel.LAYER_LOCATION, FlurryBootsModel::createBodyLayer);
        layerDefinition.accept(HermesBootsModel.LAYER_LOCATION, HermesBootsModel::createBodyLayer);
        layerDefinition.accept(ObsidianSkullModel.LAYER_LOCATION, ObsidianSkullModel::createBodyLayer);
    }
}
