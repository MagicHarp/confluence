package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class MoonStone extends BaseCurioItem implements IMagicAttack {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("E11BBE53-8620-4795-296E-B1E512E04EFF");
    public static final UUID DAMAGE_UUID = UUID.fromString("51D0544E-0AA8-CB0C-5FBC-1A4C6B5C99B7");
    public static final UUID ARMOR_UUID = UUID.fromString("35D05688-D0BE-3387-DAD8-82C79B46AB46");
    public static final UUID CRIT_UUID = UUID.fromString("3CCCFFBE-2975-FFF3-FF4C-2AF08AA74CC4");
    public static final UUID MINING_UUID = UUID.fromString("BD0CC75B-8DE2-CDAC-35C7-B8947CB285F2");
    public static final UUID RANGED_UUID = UUID.fromString("6E8B22E4-8888-8C96-12B0-4B4C5CE84593");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public MoonStone() {
        super(ModRarity.PINK);
    }

    @Override
    public double getMagicBonus() {
        return 0.1;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Moon Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Moon Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Moon Stone", 4, AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Moon Stone", 0.02, AttributeModifier.Operation.ADDITION));
            builder.put(ModAttributes.getMiningSpeed(), new AttributeModifier(MINING_UUID, "Moon Stone", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Moon Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
            ATTRIBUTES = builder.build();
        }
        LivingEntity living = slotContext.entity();
        if (living == null) return EMPTY_ATTRIBUTE;
        return living.level().getDayTime() % 24000 > 12000 ? ATTRIBUTES : EMPTY_ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.level().getDayTime() % 24000 < 12000) return;
        ModEffects.healPerSecond(living, 2.0F);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.moon_stone.info")
        };
    }
}
