package org.confluence.mod.painting;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant>PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Confluence.MODID);
    public static final RegistryObject<PaintingVariant> AMANITA = PAINTING_VARIANTS.register("amanita",()->new PaintingVariant(32,32));
    public static final RegistryObject<PaintingVariant> MAGIC_HARP = PAINTING_VARIANTS.register("magic_harp",()->new PaintingVariant(32,32));
    public static final RegistryObject<PaintingVariant> WESTERNAT = PAINTING_VARIANTS.register("westernat",()->new PaintingVariant(32,32));
    public static final RegistryObject<PaintingVariant> COOOBRID = PAINTING_VARIANTS.register("cooobrid",()->new PaintingVariant(32,32));
    public static void register(IEventBus bus){
         PAINTING_VARIANTS.register(bus);
    }

}
