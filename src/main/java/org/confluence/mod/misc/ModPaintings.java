package org.confluence.mod.misc;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Confluence.MODID);
    public static final RegistryObject<PaintingVariant> AMANITA = PAINTING_VARIANTS.register("amanita", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> MAGIC_HARP = PAINTING_VARIANTS.register("magic_harp", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> WESTERNAT = PAINTING_VARIANTS.register("westernat", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> COOOBRID = PAINTING_VARIANTS.register("cooobrid", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> NAKINOSI = PAINTING_VARIANTS.register("nakinosi", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> MAKER = PAINTING_VARIANTS.register("maker", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> MUSTARD_OASIS = PAINTING_VARIANTS.register("mustard_oasis", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> A_PIGEON_DELIGHT = PAINTING_VARIANTS.register("a_pigeon_delight", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> SHEEP_MINK = PAINTING_VARIANTS.register("sheep_mink", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> VOILA = PAINTING_VARIANTS.register("voila", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> XUANYU_1725 = PAINTING_VARIANTS.register("xuanyu_1725", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> SHADOW_END = PAINTING_VARIANTS.register("shadow_end", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> HUNAO = PAINTING_VARIANTS.register("hunao", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> KL_JIANA = PAINTING_VARIANTS.register("kl_jiana", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> SIHUAI_2412 = PAINTING_VARIANTS.register("sihuai_2412", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> OLD_SHEEP = PAINTING_VARIANTS.register("old_sheep", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> SLIME_DRAGON = PAINTING_VARIANTS.register("slime_dragon", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> KHAKI_COFFEE_BEANS = PAINTING_VARIANTS.register("khaki_coffee_beans", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> UQTQU_DAY = PAINTING_VARIANTS.register("uqtqu_day", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> EMERALD_SHENYI = PAINTING_VARIANTS.register("emerald_shenyi", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> BLACK_CAT = PAINTING_VARIANTS.register("black_cat", () -> new PaintingVariant(32, 32));

    public static void register(IEventBus bus) {
        PAINTING_VARIANTS.register(bus);
    }
}
