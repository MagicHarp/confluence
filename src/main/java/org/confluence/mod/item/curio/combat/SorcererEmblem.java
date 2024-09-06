package org.confluence.mod.item.curio.combat;

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
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class SorcererEmblem extends BaseCurioItem {
    public static final UUID MAGIC_UUID = UUID.fromString("CF1AF916-F1C7-7B38-E948-DE75F1528711");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public SorcererEmblem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                    ModAttributes.getMagicDamage(), new AttributeModifier(MAGIC_UUID, "Sorcerer Emblem", ModConfigs.SORCERER_EMBLEM_MAGIC.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {}

    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.sorcerer_emblem.info"),
                Component.translatable("item.confluence.sorcerer_emblem.info2")
        };
    }
}
