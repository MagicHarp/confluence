package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class EyeOfTheGolem extends BaseCurioItem {
    public static final UUID CRIT_UUID = UUID.fromString("8B15AC80-068B-E9EA-66AC-5880DBC4CC4B");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public EyeOfTheGolem() {
        super(ModRarity.LIME);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.eye_of_the_golem.info"),
            Component.translatable("item.confluence.eye_of_the_golem.info2")
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Eye Of The Golem", ModConfigs.EYE_OF_GOLEM_CRITICAL_CHANCE.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }
}
