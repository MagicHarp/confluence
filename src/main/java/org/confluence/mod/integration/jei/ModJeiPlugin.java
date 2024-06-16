package org.confluence.mod.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.Confluence;
import org.confluence.mod.fluid.ShimmerItemTransmutationEvent;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(Confluence.MODID, "jei_plugin");
    public static final Component SHIMMER_TRANSMUTATION_TITLE = Component.translatable("title.confluence.shimmer_transmutation");
    public static final IDrawable BACKGROUND = new IDrawable() {
        @Override
        public int getWidth() {
            return 128;
        }

        @Override
        public int getHeight() {
            return 128;
        }

        @Override
        public void draw(@NotNull GuiGraphics guiGraphics, int xOffset, int yOffset) {}
    };

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        registration.addRecipeCategories(new ShimmerItemTransmutationCategory(jeiHelpers));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ShimmerItemTransmutationCategory.TYPE, ShimmerItemTransmutationEvent.ITEM_TRANSMUTATION);
        for (CurioItems curio : CurioItems.values()) {
            BaseCurioItem item = curio.get();
            registration.addItemStackInfo(new ItemStack(item), item.getInformation());
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()), ShimmerItemTransmutationCategory.TYPE);
    }
}
