package org.confluence.mod.common.item.potion;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.terra_curio.common.component.ModRarity;
import org.confluence.mod.terra_curio.common.init.ModDataComponentTypes;

import java.util.List;

public class EffectPotionItem extends AbstractPotionItem {
    public final Holder<MobEffect> mobEffect;
    public final int duration;
    public final int amplifier;

    public EffectPotionItem(Properties properties, Holder<MobEffect> mobEffect, int duration, int amplifier) {
        super(properties);
        this.mobEffect = mobEffect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public EffectPotionItem(Rarity rarity, Holder<MobEffect> mobEffect, int duration) {
        this(new Properties().rarity(rarity), mobEffect, duration, 0);
    }

    public EffectPotionItem(Rarity rarity, Holder<MobEffect> mobEffect, int duration, int amplifier) {
        this(new Properties().rarity(rarity), mobEffect, duration, amplifier);
    }

    public EffectPotionItem(Holder<MobEffect> mobEffect, int duration) {
        this(new Properties().component(ModDataComponentTypes.MOD_RARITY, ModRarity.BLUE), mobEffect, duration, 0);
    }

    public EffectPotionItem(Holder<MobEffect> mobEffect, int duration, int amplifier) {
        this(new Properties().component(ModDataComponentTypes.MOD_RARITY, ModRarity.BLUE), mobEffect, duration, amplifier);
    }

    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {
        living.addEffect(new MobEffectInstance(mobEffect, duration, amplifier));
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("info.confluence.potion_mana", mobEffect.value().getColor()));
    }
}
