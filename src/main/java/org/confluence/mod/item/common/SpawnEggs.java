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
    BLUE_SLIME_SPAWN_EGG("blue_slime_spawn_egg", ConfluenceEntities.BLUE_SLIME, 0x66CCFF, 0x466CBE),
    PURPLE_SLIME_SPAWN_EGG("purple_slime_spawn_egg", ConfluenceEntities.PURPLE_SLIME, 0x9B119E, 0xA246BE),
    GREEN_SLIME_SPAWN_EGG("green_slime_spawn_egg", ConfluenceEntities.GREEN_SLIME, 0xE3C929, 0xCBB31E),
    RED_SLIME_SPAWN_EGG("red_slime_spawn_egg", ConfluenceEntities.RED_SLIME, 0xC91717, 0xA51E1E),
    YELLOW_SLIME_SPAWN_EGG("yellow_slime_spawn_egg", ConfluenceEntities.YELLOW_SLIME, 0xCAEE70, 0x8DEE70),
    BLACK_SLIME_SPAWN_EGG("black_slime_spawn_egg", ConfluenceEntities.BLACK_SLIME, 0x7E7E7E, 0x373535),
    PINK_SLIME_SPAWN_EGG("pink_slime_spawn_egg", ConfluenceEntities.PINK_SLIME, 0xFF87B3, 0xDD427B),
    DESERT_SLIME_SPAWN_EGG("desert_slime_spawn_egg", ConfluenceEntities.DESERT_SLIME, 0xDCC59a, 0xC7AB5E),
    ICE_SLIME_SPAWN_EGG("ice_slime_spawn_egg", ConfluenceEntities.ICE_SLIME, 0xB3F0EA, 0x7FDEDF),
    LAVA_SLIME_SPAWN_EGG("lava_slime_spawn_egg", ConfluenceEntities.LAVA_SLIME, 0xFFB150, 0xC45737),
    CRIMSON_SLIME_SPAWN_EGG("crimson_slime_spawn_egg", ConfluenceEntities.CRIMSON_SLIME, 0x8B4949, 0x7D1D1D),
    TROPIC_SLIME_SPAWN_EGG("tropic_slime_spawn_egg", ConfluenceEntities.TROPIC_SLIME, 0x3F69BB, 0x2B6B5B),
    LUMINOUS_SLIME_SPAWN_EGG("evil_slime_spawn_egg", ConfluenceEntities.LUMINOUS_SLIME, 0xFF00FF, 0xEDFFFA);

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
