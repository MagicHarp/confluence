package org.confluence.mod.item.curio.movement;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class Flipper extends BaseCurioItem {
    public static final UUID SWIM_SPEED_UUID = UUID.fromString("6D8F5968-0A9A-9A33-ED76-1935C3F03BB1");
    static final ImmutableMultimap<Attribute, AttributeModifier> SWIM_SPEED = ImmutableMultimap.of(
            ForgeMod.SWIM_SPEED.get(), new AttributeModifier(SWIM_SPEED_UUID, "Flipper", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public Flipper() {
        super(ModRarity.BLUE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return SWIM_SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.flippers.info"),
                Component.translatable("item.confluence.flippers.info2"),
                Component.translatable("item.confluence.flippers.info3")
        };
    }
}
