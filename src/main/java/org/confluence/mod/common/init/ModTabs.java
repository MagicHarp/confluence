package org.confluence.mod.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.block.ModOreBlocks;

import java.util.function.Supplier;

public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Confluence.MODID);

    public static final Supplier<CreativeModeTab> NATURAL_BLOCKS = TABS.register("building_blocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.AIR))
                    .title(Component.translatable("creativetab.confluence.natural_blocks"))
                    .displayItems((parameters, output) -> {
                        ModOreBlocks.BLOCKS.getEntries().forEach(holder -> {
                            output.accept(holder.get().asItem());
                        });
                    })
                    .build()
    );
}