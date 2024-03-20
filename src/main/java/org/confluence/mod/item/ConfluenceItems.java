package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.common.*;
import org.confluence.mod.item.hammer.HammerAxes;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.magic.MagicMirror;
import org.confluence.mod.item.magic.ManaWeapons;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.sword.Swords;


@SuppressWarnings("unused")
public class ConfluenceItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Confluence.MODID);

    //  food
    public static final RegistryObject<Item> APPLE_JUICE = ITEMS.register("apple_juice", BaseItem::new);
    public static final RegistryObject<Item> BLACKCURRANT = ITEMS.register("blackcurrant", BaseItem::new);
    public static final RegistryObject<Item> BLOOD_ORANGE = ITEMS.register("blood_orange", BaseItem::new);
    public static final RegistryObject<Item> BLOODY_MOSCATO = ITEMS.register("bloody_moscato", BaseItem::new);
    public static final RegistryObject<Item> ELDERBERRY = ITEMS.register("elderberry", BaseItem::new);
    //  tool
    public static final RegistryObject<MagicMirror> ICE_MIRROR = ITEMS.register("ice_mirror", MagicMirror::new);
    public static final RegistryObject<MagicMirror> MAGIC_MIRROR = ITEMS.register("magic_mirror", MagicMirror::new);
    public static final RegistryObject<Item> ROPE = ITEMS.register("rope", BaseItem::new);
    public static final RegistryObject<Item> ROPE_COIL = ITEMS.register("rope_coil", BaseItem::new);
    //  Throwable
    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken", BaseItem::new);

    public static void register(IEventBus bus) {
        ConfluenceTiers.register();
        Icons.init();
        Materials.init();
        Swords.init();
        Axes.init();
        Pickaxes.init();
        Hammers.init();
        HammerAxes.init();
        SlimeBalls.init();
        ManaWeapons.init();
        TerraPotions.init();
        SpawnEggs.init();
        Armors.init();
        ITEMS.register(bus);
    }
}
