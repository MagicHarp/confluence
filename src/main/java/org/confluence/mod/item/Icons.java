package org.confluence.mod.item;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.util.EnumRegister;

public enum Icons implements EnumRegister<IconItem> {
    ITEM_ICON("item_icon"),
    BLOCKS_ICON("blocks_icon"),
    MAGIC_ICON("magic_icon"),
    MELEE_ICON("melee_icon"),
    REMOTE_ICON("remote_icon"),
    ARMOR_ICON("armor_icon"),
    CREATIVE_ICON("creative_icon"),
    DEVELOPER_ICON("developer_icon"),
    ENEMY_ICON("enemy_icon"),
    FOOD_ICON("food_icon"),
    NATURE_ICON("nature_icon"),
    POTION_ICON("potion_icon"),
    PRECIOUS_ICON("precious_icon"),
    SPOOKY_WOOD("spooky_wood"),
    SUMMON_ICON("summon_icon"),
    TOOLS_ICON("tools_icon");

    private final RegistryObject<IconItem> value;

    Icons(String id) {
        this.value = ConfluenceItems.ITEMS.register(id, IconItem::new);
    }

    @Override
    public RegistryObject<IconItem> getValue() {
        return value;
    }

    static void init() {
        Confluence.LOGGER.info("Registering icon items");
    }
}
