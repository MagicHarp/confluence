package org.confluence.mod.item.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.ConfluenceEntities;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum SpawnEggs implements EnumRegister<ForgeSpawnEggItem> {
    BLUE_SLIME_SPAWN_EGG("blue_slime_spawn_egg", ConfluenceEntities.BLUE_SLIME, 0x66CCFF, 0x39C5BB);

    private final RegistryObject<ForgeSpawnEggItem> value;

    SpawnEggs(String id, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties props) {
        this.value = ConfluenceItems.ITEMS.register(id, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor, props));
    }

    SpawnEggs(String id, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        this(id, type, backgroundColor, highlightColor, new Item.Properties());
    }

    @Override
    public RegistryObject<ForgeSpawnEggItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering spawn eggs");
    }
}
