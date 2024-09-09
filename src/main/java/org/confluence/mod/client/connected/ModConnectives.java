package org.confluence.mod.client.connected;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.connected.behaviour.ConnectedTextureBehaviour;
import org.confluence.mod.client.connected.behaviour.EncasedCTBehaviour;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class ModConnectives {
    public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();
    public static final CasingConnectivity CASING_CONNECTIVITY = new CasingConnectivity();

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(StitchedSprite::onTextureStitchPost);
        MODEL_SWAPPER.registerListeners(modEventBus);

        registerCTBehaviour(ModBlocks.ANDESITE_CASING.get(), () -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING));
        registerCasingConnectivity(ModBlocks.ANDESITE_CASING.get(), (block, cc) -> cc.makeCasing(block, AllSpriteShifts.ANDESITE_CASING));
    }

    private static <T extends Block> void registerCasingConnectivity(T entry, BiConsumer<T, CasingConnectivity> consumer) {
        consumer.accept(entry, CASING_CONNECTIVITY);
    }

    private static void registerCTBehaviour(Block entry, Supplier<ConnectedTextureBehaviour> behaviorSupplier) {
        ConnectedTextureBehaviour behavior = behaviorSupplier.get();
        MODEL_SWAPPER.getCustomBlockModels().register(ForgeRegistries.BLOCKS.getKey(entry), model -> new CTModel(model, behavior));
    }
}
