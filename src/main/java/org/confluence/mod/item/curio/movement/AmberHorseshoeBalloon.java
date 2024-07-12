package org.confluence.mod.item.curio.movement;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.combat.HoneyComb;
import org.confluence.mod.item.curio.combat.IHoneycomb;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AmberHorseshoeBalloon extends HoneyComb implements IFallResistance, IJumpBoost {
    public AmberHorseshoeBalloon() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getFallResistance() {
        return ModConfigs.AMBER_HORSESHOE_BALLOON_FALL_RESISTANCE.get();
    }

    @Override
    public double getBoost() {
        return ModConfigs.AMBER_HORSESHOE_BALLOON_JUMP_BOOST.get();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return LuckyHorseshoe.getOrCreateAttributes();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IHoneycomb.TOOLTIP);
        list.add(Component.translatable("item.confluence.horseshoe_balloon.tooltip"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.amber_horseshoe_balloon.info"),
            Component.translatable("item.confluence.amber_horseshoe_balloon.info2"),
            Component.translatable("item.confluence.amber_horseshoe_balloon.info3")
        };
    }
}
