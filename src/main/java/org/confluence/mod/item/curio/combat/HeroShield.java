package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class HeroShield extends PaladinsShield implements IAggroAttach {
    public static final UUID ARMOR_UUID = UUID.fromString("164D0FAC-7E8E-1629-33E1-FEC6BEFFDF5B");
    public static final UUID RESISTANCE_UUID = UUID.fromString("DF9BB853-E184-8546-7602-CF4DA5D47BBC");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Hero Shield", 10, AttributeModifier.Operation.ADDITION),
        Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(RESISTANCE_UUID, "Hero Shield", 1.0, AttributeModifier.Operation.ADDITION)
    );

    public HeroShield() {
        super(ModRarity.PINK);
    }

    public int getAggro() {
        return 400;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }

    @Override
    public String getName() {
        return "Hero Shield";
    }
}
