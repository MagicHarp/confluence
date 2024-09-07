package org.confluence.mod.item.curio.miscellaneous;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class TreasureMagnet extends BaseCurioItem {
    public static final UUID PICKUP_UUID = UUID.fromString("800428A3-184D-C143-D31A-B9FAF862BA86");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.treasure_magnet.info")
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                    ModAttributes.getPickupRange(), new AttributeModifier(PICKUP_UUID, "Treasure Magnet", ModConfigs.TREASURE_MAGNET_PICKUP.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }
}
