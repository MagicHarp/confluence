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

    public static final RegistryObject<CreativeModeTab> BUILDING_BLOCKS = TABS.register("building_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.BLOCKS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.building_blocks"))
            .displayItems((parameters, output) -> {

            })
            .build());
    public static final RegistryObject<CreativeModeTab> NATURAL_BLOCKS = TABS.register("natural_blocks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ConfluenceItems.BLOCKS_ICON.get()))
            .title(Component.translatable("creativetab.confluence.natural_blocks"))
            .displayItems((parameters, output) -> {

            })
            .build());
    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Materials.RAW_TIN.get()))
            .title(Component.translatable("creativetab.confluence.materials"))
            .displayItems((parameters, output) -> {
                for (Materials materials : Materials.values()) {
                    output.accept(materials.get());
                }
            })
            .build());

}
