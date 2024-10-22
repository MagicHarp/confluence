package org.confluence.mod.common.init.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomModelData;
import net.neoforged.neoforge.registries.DeferredItem;
import org.confluence.mod.common.init.ModItems;


public class IconItem extends Item{

    public static DeferredItem<Item> ITEM_ICON = register("item_icon");
    public static DeferredItem<Item> MATERIAL_ICON = register("material_icon");
    public static DeferredItem<Item> BLOCKS_ICON = register("blocks_icon");
    public static DeferredItem<Item> MAGIC_ICON = register("magic_icon");
    public static DeferredItem<Item> MELEE_ICON = register("melee_icon");
    public static DeferredItem<Item> REMOTE_ICON = register("remote_icon");
    public static DeferredItem<Item> ARMOR_ICON = register("armor_icon");
    public static DeferredItem<Item> NATURE_ICON = register("nature_icon");
    public static DeferredItem<Item> POTION_ICON = register("potion_icon");
    public static DeferredItem<Item> PRECIOUS_ICON = register("precious_icon");
    public static DeferredItem<Item> SUMMON_ICON = register("summon_icon");
    public static DeferredItem<Item> DEVELOPER_ICON = register("developer_icon");
    public static DeferredItem<Item> ACCESSORIES_ICON = register("accessories_icon");
    public static DeferredItem<Item> TOOLS_ICON = register("tools_icon");
    public static DeferredItem<Item> MECHANICAL_ICON = register("mechanical_icon");
    public IconItem() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }
    public static DeferredItem<Item> register(String name){
        return ModItems.ITEMS.register(name, IconItem::new);
    }
    public static void init(){}
}