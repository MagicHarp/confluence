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
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AvengerEmblem extends BaseCurioItem implements IMagicAttack {
    public static final UUID DAMAGE_UUID = UUID.fromString("3D20DB42-C40E-23BF-6CE4-FBDD7CC14222");
    public static final UUID RANGED_UUID = UUID.fromString("2E3FD383-40AC-A0DB-E778-0657BE11F199");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public AvengerEmblem() {
        super(ModRarity.PINK);
    }

    @Override
    public double getMagicBonus() {
        return ModConfigs.AVENGER_EMBLEM_MAGIC_BONUS.get();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Avenger Emblem", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL),
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Avenger Emblem", 0.12, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.avenger_emblem.info"),
            Component.translatable("item.confluence.avenger_emblem.info2")
        };
    }
}
