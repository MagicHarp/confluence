package org.confluence.mod.menu;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Confluence.MODID);

    public static final RegistryObject<MenuType<SkyMillMenu>> SKY_MILL = TYPES.register("sky_mill", () -> new MenuType<>(SkyMillMenu::new, FeatureFlags.VANILLA_SET));
    public static final RegistryObject<MenuType<WorkshopMenu>> WORKSHOP = TYPES.register("workshop", () -> new MenuType<>(WorkshopMenu::new, FeatureFlags.VANILLA_SET));
}
