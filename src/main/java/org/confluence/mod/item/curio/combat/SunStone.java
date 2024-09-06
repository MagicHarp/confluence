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
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SunStone extends BaseCurioItem {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("6B2CFC65-3C84-7C1F-69D8-7B37556578E0");
    public static final UUID DAMAGE_UUID = UUID.fromString("56A08AD3-ADA1-F838-E09C-28B08935F5C2");
    public static final UUID ARMOR_UUID = UUID.fromString("7E929677-A019-1C19-1A2C-36A07268A66B");
    public static final UUID CRIT_UUID = UUID.fromString("840A63CB-F274-75C1-09B1-BC8092B076F4");
    public static final UUID MINING_UUID = UUID.fromString("B599894C-1157-3875-C28C-E4F0681F04E9");
    public static final UUID RANGED_UUID = UUID.fromString("171921EE-AB4F-E630-D69E-BB31E17A22C8");
    public static final UUID MAGIC_UUID = UUID.fromString("B9A4443F-E08B-19CD-0EBD-679F5267CB14");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public SunStone() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.<Attribute, AttributeModifier>builder()
                    .put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Sun Stone", ModConfigs.SUN_STONE_SPEED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Sun Stone", ModConfigs.SUN_STONE_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Sun Stone", ModConfigs.SUN_STONE_ARMOR.get(), AttributeModifier.Operation.ADDITION))
                    .put(ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Sun Stone", ModConfigs.SUN_STONE_CRITICAL_CHANCE.get(), AttributeModifier.Operation.ADDITION))
                    .put(ModAttributes.getMiningSpeed(), new AttributeModifier(MINING_UUID, "Sun Stone", ModConfigs.SUN_STONE_MINING.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ModAttributes.getRangedDamage(), new AttributeModifier(RANGED_UUID, "Sun Stone", ModConfigs.SUN_STONE_RANGED.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .put(ModAttributes.getMagicDamage(), new AttributeModifier(MAGIC_UUID, "Sun Stone", ModConfigs.SUN_STONE_MAGIC.get(), AttributeModifier.Operation.MULTIPLY_TOTAL))
                    .build();
        }
        LivingEntity living = slotContext.entity();
        if (living == null) return EMPTY_ATTRIBUTE;
        return living.level().getDayTime() % 24000 < 12000 ? ATTRIBUTES : EMPTY_ATTRIBUTE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        if (living.level().getDayTime() % 24000 > 12000) return;
        ModEffects.healPerSecond(living, 2.0F);
    }

    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.sun_stone.info")
        };
    }
}
