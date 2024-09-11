package org.confluence.mod.item.curio.construction;

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

public class ExtendoGrip extends BaseCurioItem {
    public static final UUID REACH_UUID = UUID.fromString("E4F0BF95-655D-7657-F58E-1C7053FACAFC");
    static final ImmutableMultimap<Attribute, AttributeModifier> REACH = ImmutableMultimap.of(
        ForgeMod.BLOCK_REACH.get(), new AttributeModifier(REACH_UUID, "Extendo Grip", 3, AttributeModifier.Operation.ADDITION)
    );

    public ExtendoGrip() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return REACH;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }
}
