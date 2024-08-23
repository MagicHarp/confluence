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
import org.confluence.mod.entity.ModAttributes;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class PutridScent extends BaseCurioItem implements IAggroAttach {
    public static final UUID DAMAGE_UUID = UUID.fromString("70F6E4B4-64AC-4B2A-AAD6-8C35AFB9507D");
    public static final UUID CRIT_UUID = UUID.fromString("7C35023D-57DA-189C-6CD1-BA987AFF6142");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public PutridScent() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Putrid Scent", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL),
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Recon Scope", 0.05, AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }

    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("item.confluence.putrid_scent.tooltip2"));
    }

    @Override
    public int getAggro() {
        return -400;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.putrid_scent.info"),
            Component.translatable("item.confluence.putrid_scent.info2"),
            Component.translatable("item.confluence.putrid_scent.info3")
        };
    }
}
