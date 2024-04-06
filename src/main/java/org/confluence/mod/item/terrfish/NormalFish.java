package org.confluence.mod.item.terrfish;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.bullet.SparkBulletEntity;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.BaseItem;
import org.confluence.mod.item.magic.StaffItem;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum NormalFish implements EnumRegister<Item> {
    SEA_BASS("sea_bass", BaseItem::new),
    ATLANTIC_COD("atlantic_cod", BaseItem::new),
    ARMORED_CAVE_FISH("armored_cave_fish", BaseItem::new),
    CHAOS_FISH("chaos_fish", BaseItem::new),
    SCARLET_TIGER_FISH("scarlet_tiger_fish", BaseItem::new),
    DAMSEL_FISH("damsel_fish", BaseItem::new),
    PISCES_FIN_COD("pisces_fin_cod", BaseItem::new),
    EBONY_KOI("ebony_koi", BaseItem::new),
    FLASHFIN_KOI("flashfin_koi", BaseItem::new),


    ;

    private final RegistryObject<Item> value;

    NormalFish(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("NormalFish");
    }
}
