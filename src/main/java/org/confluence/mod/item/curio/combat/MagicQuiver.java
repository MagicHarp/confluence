package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class MagicQuiver extends BaseCurioItem implements IMagicQuiver {
    public static final UUID RANGED_DAMAGE_UUID = UUID.fromString("E8580731-52F5-7486-A267-E4440FE2831A");
    public static final UUID RANGED_VELOCITY_UUID = UUID.fromString("7F481225-F353-FC3E-FBBF-75870C1E20FB");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public MagicQuiver() {
        super(ModRarity.LIGHT_RED);
    }

    public MagicQuiver(Rarity rarity) {
        super(rarity);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_DAMAGE_UUID, "Magic Quiver", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
                ModAttributes.getRangedVelocity(), new AttributeModifier(RANGED_VELOCITY_UUID, "Magic Quiver", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magic_quiver.tooltip2"));
    }

    public static boolean shouldConsume(LivingEntity living) {
        return living.getRandom().nextFloat() >= 0.2 || CuriosUtils.noSameCurio(living, IMagicQuiver.class);
    }
}
