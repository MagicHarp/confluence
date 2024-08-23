package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class DestroyerEmblem extends BaseCurioItem implements IMagicAttack, IProjectileAttack {
    public static final UUID DAMAGE_UUID = UUID.fromString("35E7BAD6-5998-D35B-2974-4FA8065D29F7");
    public static final UUID CRIT_UUID = UUID.fromString("4BBCB7D4-9884-124C-041C-72CA6959D7E8");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public DestroyerEmblem() {
        super(ModRarity.LIME);
    }

    @Override
    public double getMagicBonus() {
        return ModConfigs.DESTROYER_EMBLEM_MAGIC_BONUS.get();
    }

    @Override
    public float getProjectileBonus() {
        return ModConfigs.DESTROYER_EMBLEM_PROJECTILE_BONUS.get().floatValue();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Destroyer Emblem", ModConfigs.DESTROYER_EMBLEM_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_TOTAL),
                ModAttributes.getCriticalChance(), new AttributeModifier(CRIT_UUID, "Destroyer Emblem", ModConfigs.DESTROYER_EMBLEM_CRITICAL_CHANCE.get(), AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.destroyer_emblem.info"),
            Component.translatable("item.confluence.destroyer_emblem.info2")
        };
    }
}
