package org.confluence.mod.item.hammer;

import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTiers;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Hammers implements EnumRegister<HammerItem> {
    WOODEN_HAMMER("wooden_hammer", () -> new HammerItem(Tiers.WOOD, 1, 1)),
    COPPER_HAMMER("copper_hammer", () -> new HammerItem(ModTiers.COPPER, 2, 1)),
    TIN_HAMMER("tin_hammer", () -> new HammerItem(ModTiers.TIN, 2, 1)),
    LEAD_HAMMER("lead_hammer", () -> new HammerItem(ModTiers.LEAD, 3, 1)),
    SILVER_HAMMER("silver_hammer", () -> new HammerItem(ModTiers.SILVER, 3, 1)),
    TUNGSTEN_HAMMER("tungsten_hammer", () -> new HammerItem(ModTiers.TUNGSTEN, 4, 1)),
    GOLDEN_HAMMER("golden_hammer", () -> new HammerItem(ModTiers.GOLD, 5, 1)),
    PLATINUM_HAMMER("platinum_hammer", () -> new HammerItem(ModTiers.PLATINUM, 5, 1)),
    EBONY_HAMMER("ebony_hammer", () -> new BigHammerItem(ModTiers.EBONY, 6, 1)),
    TR_CRIMSON_HAMMER("tr_crimson_hammer", () -> new BigHammerItem(ModTiers.TR_CRIMSON, 6, 1)),
    PWNHAMMER("pwnhammer", () -> new HammerItem(ModTiers.HALLOWED, 7, 1));

    private final RegistryObject<HammerItem> value;

    Hammers(String id, Supplier<HammerItem> hammer) {
        this.value = ModItems.ITEMS.register(id, hammer);
    }

    @Override
    public RegistryObject<HammerItem> getValue() {
        return value;
    }

    public static void init() {}
}
