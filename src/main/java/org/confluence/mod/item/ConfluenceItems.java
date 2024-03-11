package org.confluence.mod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.common.BaseItem;
import org.confluence.mod.item.common.HealingPotion;
import org.confluence.mod.item.magic.MagicMirror;
import org.confluence.mod.item.magic.ManaPotion;


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
    //  Potion
    public static final RegistryObject<HealingPotion> LESSER_HEALING_POTION = ITEMS.register("lesser_healing_potion", () -> new HealingPotion(50, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<HealingPotion> HEALING_POTION = ITEMS.register("healing_potion", () -> new HealingPotion(100, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<HealingPotion> GREATER_HEALING_POTION = ITEMS.register("greater_healing_potion", () -> new HealingPotion(200, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<HealingPotion> SUPER_HEALING_POTION = ITEMS.register("super_healing_potion", () -> new HealingPotion(300, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<ManaPotion> LESSER_MANA_POTION = ITEMS.register("lesser_mana_potion", () -> new ManaPotion(50, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<ManaPotion> MANA_POTION = ITEMS.register("mana_potion", () -> new ManaPotion(100, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ManaPotion> GREATER_MANA_POTION = ITEMS.register("greater_mana_potion", () -> new ManaPotion(200, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<ManaPotion> SUPER_MANA_POTION = ITEMS.register("super_mana_potion", () -> new ManaPotion(300, new Item.Properties().rarity(Rarity.EPIC)));
    //  Throwable
    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken", BaseItem::new);


    public static void register(IEventBus bus) {
        Icons.init();
        Materials.init();
        Swords.init();
        Axes.init();
        Pickaxes.init();
        Hammers.init();
        HammerAxes.init();
        SlimeBalls.init();
        ITEMS.register(bus);
    }
}
