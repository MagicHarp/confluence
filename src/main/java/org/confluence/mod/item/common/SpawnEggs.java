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
    BLUE_SLIME_SPAWN_EGG("blue_slime_spawn_egg", ConfluenceEntities.BLUE_SLIME, 0x66CCFF, 0x466cbe),
    PURPLE_SLIME_SPAWN_EGG("purple_slime_spawn_egg", ConfluenceEntities.PURPLE_SLIME, 0x9b119e, 0xa246be),
    GREEN_SLIME_SPAWN_EGG("green_slime_spawn_egg", ConfluenceEntities.GREEN_SLIME, 0xe3c929, 0xcbb31e),
    RED_SLIME_SPAWN_EGG("red_slime_spawn_egg", ConfluenceEntities.RED_SLIME, 0xc91717, 0xa51e1e),
    YELLOW_SLIME_SPAWN_EGG("yellow_slime_spawn_egg", ConfluenceEntities.YELLOW_SLIME, 0xcaee70, 0x8dee70),
   BLACK_SLIME_SPAWN_EGG("black_slime_spawn_egg", ConfluenceEntities. BLACK_SLIME, 0x7e7e7e, 0x373535),
    PINK_SLIME_SPAWN_EGG("pink_slime_spawn_egg", ConfluenceEntities.PINK_SLIME, 0xff87b3, 0xdd427b),
    DESERT_SLIME_SPAWN_EGG("desert_slime_spawn_egg", ConfluenceEntities.DESERT_SLIME, 0xdcc59a, 0xc7ab5e),
    ICE_SLIME_SPAWN_EGG("ice_slime_spawn_egg", ConfluenceEntities.ICE_SLIME, 0xb3f0ea, 0x7fdedf),
    LAVA_SLIME_SPAWN_EGG("lava_slime_spawn_egg", ConfluenceEntities.LAVA_SLIME, 0xffb150, 0xc45737),
    CRIMSON_SLIME_SPAWN_EGG("crimson_slime_spawn_egg", ConfluenceEntities.CRIMSON_SLIME, 0x8b4949, 0x7d1d1d),
    TROPIC_SLIME_SPAWN_EGG("tropic_slime_spawn_egg", ConfluenceEntities.TROPIC_SLIME, 0x3f69bb, 0x2b6b5b),
    LUMINOUS_SLIME_SPAWN_EGG("evil_slime_spawn_egg", ConfluenceEntities.LUMINOUS_SLIME, 0xff00ff, 0xedfffa),

    ;

    ;
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
