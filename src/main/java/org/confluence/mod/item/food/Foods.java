package org.confluence.mod.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Foods implements EnumRegister<Item> {

    EMM("emm", (new FoodProperties.Builder()).nutrition(2).saturationMod(2.5F)
            .effect(new MobEffectInstance(ModEffects.EXQUISITELY_STUFFED.get(), 6000, 1), 0.3F).build());

    private final RegistryObject<Item> value;

    private static FoodProperties.Builder stew(int p_150384_) {
        return (new FoodProperties.Builder()).nutrition(p_150384_).saturationMod(0.6F);
    }
    Foods(String id, FoodProperties build) {
        RegistryObject<Item> FoodProperties = null;
        this.value = FoodProperties;
    }
    Foods(String id, Supplier<Item> item) {
        this.value = ModItems.ITEMS.register(id, item);
    }

    @Override
    public RegistryObject<Item> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering foods");
    }
}
