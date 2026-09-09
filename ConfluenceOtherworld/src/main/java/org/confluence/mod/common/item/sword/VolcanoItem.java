package org.confluence.mod.common.item.sword;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.mod.common.init.ModEffects;
import org.confluence.mod.common.init.ModTiers;
import org.mesdag.portlib.wrapper.world.entity.ai.attributes.PortAttributeModifier;

public class VolcanoItem extends EffectSwordItem {
    private static final int HELLFIRE_DURATION = 100;

    public VolcanoItem() {
        super(ModTiers.UNBREAKABLE, ModRarity.ORANGE, 25, 1.2F, SwordDefinition.builder()
                        .tooltipImage()
                        .attribute(Attributes.ENTITY_INTERACTION_RANGE, 4.0F, PortAttributeModifier.Operation.ADD_VALUE)
                        .attribute(Attributes.ATTACK_KNOCKBACK, 0.5F, PortAttributeModifier.Operation.ADD_VALUE)
                        .specialSweep(0.8F),
                ModEffects.HELLFIRE, HELLFIRE_DURATION, 0, 1.0F);
    }

    @Override
    protected void onDamage(ItemStack weapon, LivingEntity attacker, LivingEntity victim, DamageSource source) {
        super.onDamage(weapon, attacker, victim, source);
        victim.setRemainingFireTicks(HELLFIRE_DURATION);
    }
}
