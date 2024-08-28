package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class Aglet extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("2B6DC797-A802-DF05-8231-BC8FCA9D770A");
    private static ImmutableMultimap<Attribute, AttributeModifier> SPEED;

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (SPEED == null) {
            SPEED = ImmutableMultimap.of(
                Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Aglet", ModConfigs.AGLET_MOVEMENT.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.aglet.info")
        };
    }
}
