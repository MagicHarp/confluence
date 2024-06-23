package org.confluence.mod.item.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum SpawnEggs implements EnumRegister<ForgeSpawnEggItem> {
    BLUE_SLIME_SPAWN_EGG("blue_slime_spawn_egg", ModEntities.BLUE_SLIME, 0x73bcf4, 0x466CBE),
    PURPLE_SLIME_SPAWN_EGG("purple_slime_spawn_egg", ModEntities.PURPLE_SLIME, 0xf334f8, 0xA246BE),
    GREEN_SLIME_SPAWN_EGG("green_slime_spawn_egg", ModEntities.GREEN_SLIME, 0xa2f89f, 0x3de838),
    RED_SLIME_SPAWN_EGG("red_slime_spawn_egg", ModEntities.RED_SLIME, 0xf83434, 0xA51E1E),
    YELLOW_SLIME_SPAWN_EGG("yellow_slime_spawn_egg", ModEntities.YELLOW_SLIME, 0xf8e234, 0xd19519),
    BLACK_SLIME_SPAWN_EGG("black_slime_spawn_egg", ModEntities.BLACK_SLIME, 0x7E7E7E, 0x373535),
    PINK_SLIME_SPAWN_EGG("pink_slime_spawn_egg", ModEntities.PINK_SLIME, 0xFF87B3, 0xf89fe3),
    DESERT_SLIME_SPAWN_EGG("desert_slime_spawn_egg", ModEntities.DESERT_SLIME, 0xDCC59a, 0xC7AB5E),
    ICE_SLIME_SPAWN_EGG("ice_slime_spawn_egg", ModEntities.ICE_SLIME, 0xB3F0EA, 0x7FDEDF),
    LAVA_SLIME_SPAWN_EGG("lava_slime_spawn_egg", ModEntities.LAVA_SLIME, 0xFFB150, 0xC45737),
    CRIMSON_SLIME_SPAWN_EGG("crimson_slime_spawn_egg", ModEntities.CRIMSON_SLIME, 0x8B4949, 0x7D1D1D),
    TROPIC_SLIME_SPAWN_EGG("tropic_slime_spawn_egg", ModEntities.TROPIC_SLIME, 0x73bcf4, 0x7374f4),
    LUMINOUS_SLIME_SPAWN_EGG("evil_slime_spawn_egg", ModEntities.LUMINOUS_SLIME, 0xFF00FF, 0xEDFFFA);

    private final RegistryObject<ForgeSpawnEggItem> value;

    SpawnEggs(String id, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties props) {
        this.value = ModItems.ITEMS.register(id, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor, props));
    }

    SpawnEggs(String id, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        this(id, type, backgroundColor, highlightColor, new Item.Properties());
    }

    @Override
    public RegistryObject<ForgeSpawnEggItem> getValue() {
        return value;
    }

    public static void init() {}
}
