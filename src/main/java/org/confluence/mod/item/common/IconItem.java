package org.confluence.mod.item.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

public class IconItem extends Item implements CustomModel {
    public IconItem() {
        super(new Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(1));
    }

    public enum Icons implements EnumRegister<IconItem> {
        ACCESSORIES_ICON("accessories_icon");

        private final RegistryObject<IconItem> value;

        Icons(String id) {
            this.value = ModItems.ITEMS.register(id, IconItem::new);
        }

        @Override
        public RegistryObject<IconItem> getValue() {
            return value;
        }

        public static void init() {
            Confluence.LOGGER.info("Registering icon items");
        }
    }
}
