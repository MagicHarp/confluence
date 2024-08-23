package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class MoonStone extends BaseCurioItem {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("E11BBE53-8620-4795-296E-B1E512E04EFF");
    public static final UUID DAMAGE_UUID = UUID.fromString("51D0544E-0AA8-CB0C-5FBC-1A4C6B5C99B7");
    public static final UUID ARMOR_UUID = UUID.fromString("35D05688-D0BE-3387-DAD8-82C79B46AB46");
    public static final UUID CRIT_UUID = UUID.fromString("3CCCFFBE-2975-FFF3-FF4C-2AF08AA74CC4");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    public MoonStone() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living == null) return EMPTY_ATTRIBUTE;
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Moon Stone", ModConfigs.MOON_STONE_SPEED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Moon Stone", ModConfigs.MOON_STONE_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Moon Stone", ModConfigs.MOON_STONE_ARMOR.get(), AttributeModifier.Operation.ADDITION),
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Moon Stone", ModConfigs.MOON_STONE_CRITICAL_CHANCE.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return living.level().getDayTime() % 24000 > 12000 ? ATTRIBUTE : EMPTY_ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.level().getDayTime() % 24000 < 12000) return;
        ModEffects.healPerSecond(living, 2.0F);
        MobEffectInstance effect = living.getEffect(MobEffects.DIG_SPEED);
        if (effect == null || effect.getDuration() < 5) {
            living.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, 0, false, false, false));
        }
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.moon_stone.info"),
            Component.translatable("item.confluence.moon_stone.info2"),
            Component.translatable("item.confluence.moon_stone.info3"),
            Component.translatable("item.confluence.moon_stone.info4"),
            Component.translatable("item.confluence.moon_stone.info5"),
            Component.translatable("item.confluence.moon_stone.info6"),
            Component.translatable("item.confluence.moon_stone.info7"),
            Component.translatable("item.confluence.moon_stone.info8"),
            Component.translatable("item.confluence.moon_stone.info9"),
            Component.translatable("item.confluence.moon_stone.info10")
        };
    }
}
