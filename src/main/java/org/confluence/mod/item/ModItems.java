package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.common.CellPhone;
import org.confluence.mod.item.common.DemonHeart;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.common.MagicMirror;
import org.confluence.mod.item.curio.CurioItems;


@SuppressWarnings("unused")
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties()));
    public static final RegistryObject<DemonHeart> DEMON_HEART = ITEMS.register("demon_heart", DemonHeart::new);
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror", MagicMirror::new);
    public static final RegistryObject<CellPhone> CELL_PHONE = ITEMS.register("cell_phone", CellPhone::new);

    public static void register(IEventBus bus) {
        IconItem.Icons.init();
        CurioItems.initialize();
        ITEMS.register(bus);
    }
}
