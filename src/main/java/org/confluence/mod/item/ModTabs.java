package org.confluence.mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.curio.CurioItems;

@SuppressWarnings("unused")
public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);

    public static final RegistryObject<CreativeModeTab> JEWELRY = TABS.register("accessories",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(IconItem.Icons.ACCESSORIES_ICON.get()))
            .title(Component.translatable("creativetab.confluence.accessories"))
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.WORKSHOP.get());
                output.accept(ModItems.DEMON_HEART.get());
                for (CurioItems curioItems : CurioItems.values()) output.accept(curioItems.get());
            })
            .build());
}