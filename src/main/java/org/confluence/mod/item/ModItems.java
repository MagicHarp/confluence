package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.curio.CurioItems;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        IconItem.Icons.init();
        CurioItems.init();
        ITEMS.register(bus);
    }
}
