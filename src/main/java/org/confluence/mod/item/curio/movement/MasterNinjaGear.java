package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class MasterNinjaGear extends BaseCurioItem implements IWallClimb, ITabi {
    public static final UUID DODGE_UUID = UUID.fromString("BBBE0822-5ABC-6C16-80E8-75C20CADCE5E");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public MasterNinjaGear() {
        super(ModRarity.YELLOW);
    }

    @Override
    public boolean fullyWallClimb() {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(IWallClimb.WALL_CLIMB);
        list.add(ITabi.TOOLTIP);
        list.add(Component.translatable("curios.tooltip.hurt_evasion"));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                ModAttributes.getDodgeChance(), new AttributeModifier(DODGE_UUID, "Master Ninja Gear", 0.1, AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTE;
    }
}
