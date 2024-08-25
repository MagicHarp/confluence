package org.confluence.mod.equipment_set;

import com.xiaohunao.equipment_benediction.api.IEquippable;
import com.xiaohunao.equipment_benediction.common.equippable.VanillaEquippable;
import com.xiaohunao.equipment_benediction.common.event.BenedictionRegisterEvent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.armor.Armors;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ModEquipmentSet {
    @SubscribeEvent
    public static void onBenedictionRegister(BenedictionRegisterEvent event) {
        registerSimpleArmorAttributeSet(event,"wood_set",Map.of(
                VanillaEquippable.of("head"), Armors.PLANK_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.PLANK_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.PLANK_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.PLANK_BOOTS.getIngredient()
        ),1);
        registerSimpleArmorAttributeSet(event,"shadewood_set",Map.of(
                VanillaEquippable.of("head"), Armors.SHADOW_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.SHADOW_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.SHADOW_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.SHADOW_BOOTS.getIngredient()
        ),1);
        registerSimpleArmorAttributeSet(event,"ebonwood_set",Map.of(
                VanillaEquippable.of("head"), Armors.EBONY_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.EBONY_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.EBONY_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.EBONY_BOOTS.getIngredient()
        ),1);
        registerSimpleArmorAttributeSet(event,"pearlwood_set",Map.of(
                VanillaEquippable.of("head"), Armors.PEARL_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.PEARL_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.PEARL_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.PEARL_BOOTS.getIngredient()
        ),1);
        registerSimpleArmorAttributeSet(event,"copper_set",Map.of(
                VanillaEquippable.of("head"), Armors.COPPER_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.COPPER_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.COPPER_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.COPPER_BOOTS.getIngredient()
        ),2);
        registerSimpleArmorAttributeSet(event,"tin_set",Map.of(
                VanillaEquippable.of("head"), Armors.COPPER_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.COPPER_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.COPPER_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.COPPER_BOOTS.getIngredient()
        ),2);
        registerSimpleArmorAttributeSet(event,"iron_set",Map.of(
                VanillaEquippable.of("head"), Ingredient.of(Items.IRON_HELMET),
                VanillaEquippable.of("chest"), Ingredient.of(Items.IRON_CHESTPLATE),
                VanillaEquippable.of("legs"), Ingredient.of(Items.IRON_LEGGINGS),
                VanillaEquippable.of("feet"), Ingredient.of(Items.IRON_BOOTS)
        ),2);
        registerSimpleArmorAttributeSet(event,"lead_set",Map.of(
                VanillaEquippable.of("head"), Armors.LEAD_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.LEAD_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.LEAD_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.LEAD_BOOTS.getIngredient()
        ),3);
        registerSimpleArmorAttributeSet(event,"silver_set",Map.of(
                VanillaEquippable.of("head"), Armors.SILVER_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.SILVER_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.SILVER_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.SILVER_BOOTS.getIngredient()
        ),3);
        registerSimpleArmorAttributeSet(event,"tungsten_set",Map.of(
                VanillaEquippable.of("head"), Armors.TUNGSTEN_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.TUNGSTEN_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.TUNGSTEN_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.TUNGSTEN_BOOTS.getIngredient()
        ),3);
        registerSimpleArmorAttributeSet(event,"gold_set",Map.of(
                VanillaEquippable.of("head"), Ingredient.of(Items.GOLDEN_HELMET,Armors.GOLDEN_HELMET.get()),
                VanillaEquippable.of("chest"), Ingredient.of(Items.GOLDEN_CHESTPLATE),
                VanillaEquippable.of("legs"), Ingredient.of(Items.GOLDEN_LEGGINGS),
                VanillaEquippable.of("feet"), Ingredient.of(Items.GOLDEN_BOOTS)
        ),4);
        registerSimpleArmorAttributeSet(event,"platinum_set",Map.of(
                VanillaEquippable.of("head"), Armors.PLATINUM_HELMET.getIngredient(),
                VanillaEquippable.of("chest"), Armors.PLATINUM_CHESTPLATE.getIngredient(),
                VanillaEquippable.of("legs"), Armors.PLATINUM_LEGGINGS.getIngredient(),
                VanillaEquippable.of("feet"), Armors.PLATINUM_BOOTS.getIngredient()
        ),4);
    }

    private static void registerSimpleArmorAttributeSet(BenedictionRegisterEvent event, String key, Map<IEquippable, Ingredient> groupMap,double value) {
        event.registerEquipmentSet(Confluence.asResource(key), set -> {
            set.bonus.addAttributeModifier(Attributes.ARMOR,
                    String.valueOf(UUID.randomUUID()), value,AttributeModifier.Operation.ADDITION);
            groupMap.forEach((equippable, ingredient) -> {
                set.group.addGroup(equippable, ingredient);
            });
            });
    }
}
