package org.confluence.mod.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(Confluence.MODID, "jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        registration.addItemStackInfo(new ItemStack(ModItems.DEMON_HEART.get()), Component.translatable("item.confluence.demon_heart.info"));
        for (CurioItems curio : CurioItems.values()) {
            BaseCurioItem item = curio.get();
            Component[] information = item.getInformation();
            if (information.length == 0) continue;
            registration.addItemStackInfo(new ItemStack(item), information);
        }
    }
}
