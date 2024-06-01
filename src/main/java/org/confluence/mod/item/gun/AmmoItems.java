package org.confluence.mod.item.gun;

import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.entity.projectile.BaseAmmoEntity;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import static org.confluence.mod.entity.projectile.BaseAmmoEntity.Variant.*;
import static org.confluence.mod.misc.ModRarity.*;

public enum AmmoItems implements EnumRegister<BaseAmmoItem> {
    MUSKET_BULLET("musket_ball", WHITE, MUSKET),
    METEOR_BULLET("meteor_bullet", BLUE, METEOR),
    SILVER_BULLET("silver_bullet", WHITE, SILVER),
    CRYSTAL_BILLET("crystal_bullet", ORANGE, CRYSTAL),
    CURSED_BULLET("cursed_bullet", ORANGE, CURSED),
    CHLOROPHYTE_BULLET("chlorophyte_bullet", LIME, CHLOROPHYTE),
    HIGH_VELOCITY_BULLET("high_velocity_bullet", ORANGE, HIGH_VELOCITY),
    ICHOR_BULLET("ichor_bullet", ORANGE, ICHOR),
    VENOM_BULLET("venom_bullet", ORANGE, VENOM),
    PARTY_BULLET("party_bullet", ORANGE, PARTY),
    NANO_BULLET("nano_bullet", ORANGE, NANO),
    EXPLODING_BULLET("exploding_bullet", ORANGE, EXPLODING),
    GOLDEN_BULLET("golden_bullet", ORANGE, GOLDEN),
    ENDLESS_MUSKET_POUCH("endless_musket_pouch", GREEN, MUSKET),
    LUMINITE_BULLET("luminite_bullet", CYAN, LUMINITE),
    TUNGSTEN_BULLET("tungsten_bullet", WHITE, TUNGSTEN);

    private final RegistryObject<BaseAmmoItem> value;

    AmmoItems(String id, Rarity rarity, BaseAmmoEntity.Variant variant) {
        this.value = ModItems.ITEMS.register(id, () -> new BaseAmmoItem(rarity, variant));
    }

    @Override
    public RegistryObject<BaseAmmoItem> getValue() {
        return value;
    }

    public static void init() {}
}
