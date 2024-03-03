package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

import static org.confluence.mod.item.ConfluenceItems.Materials;

public class ConfluenceTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);

    public static final RegistryObject<CreativeModeTab> COMMON = TABS.register("common",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Materials.RAW_TIN.get()))
            .title(Component.translatable("creativetab.common"))
            .displayItems((parameters, output) -> {
                output.accept(Materials.RAW_TIN.get());
                output.accept(Materials.TIN_INGOT.get());
            })
            .build());
}
