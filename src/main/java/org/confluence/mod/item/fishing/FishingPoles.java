package org.confluence.mod.item.fishing;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum FishingPoles implements EnumRegister<Item> {
    WOOD_FISHING_POLE("wood_fishing_pole", WoodFishingPole::new),
    REINFORCED_FISHING_POLE("reinforced_fishing_pole", ReinforcedFishingPole::new),
    FISHER_OF_SOULS("fisher_of_souls", FisherOfSouls::new),
    FLESHCATCHER("fleshcatcher", Fleshcatcher::new),
    SCARAB_FISHING_ROD("scarab_fishing_rod", ScarabFishingRod::new),
    CHUM_CASTER("chum_caster", ChumCaster::new),
    FIBERGLASS_FISHING_POLE("fiberglass_fishing_pole", FiberglassFishingPole::new),
    MECHANICS_ROD("mechanics_rod", MechanicsRod::new),
    SITTING_DUCKS_FISHING_POLE("sitting_ducks_fishing_pole", SittingDucksFishingPole::new),
    HOTLINE_FISHING_HOOK("hotline_fishing_hook", HotlineFishingHook::new),
    GOLDEN_FISHING_ROD("golden_fishing_rod", GoldenFishingRod::new);

    private final RegistryObject<Item> value;

    FishingPoles(String id, Supplier<Item> supplier) {
        this.value = ModItems.ITEMS.register(id, supplier);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {}

    @OnlyIn(Dist.CLIENT)
    public static void registerCast() {
        ResourceLocation cast = new ResourceLocation("cast");
        ClampedItemPropertyFunction function = (itemStack, level, living, speed) -> {
            if (living == null) {
                return 0.0F;
            } else {
                boolean flag = living.getMainHandItem() == itemStack;
                boolean flag1 = living.getOffhandItem() == itemStack;
                if (living.getMainHandItem().getItem() instanceof FishingRodItem) flag1 = false;
                return (flag || flag1) && living instanceof Player && ((Player) living).fishing != null ? 1.0F : 0.0F;
            }
        };
        for (FishingPoles fishingPoles : FishingPoles.values()) {
            ItemProperties.register(fishingPoles.get(), cast, function);
        }
    }
}
