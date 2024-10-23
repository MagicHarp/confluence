package org.confluence.mod.common.item.potion;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.init.ModEffects;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//todo ???
public class VanillaPotionItem extends AbstractPotionItem {

    int duration;
    int amplifier;

    public VanillaPotionItem() {
        super(new Properties());
        duration = 2400;
        amplifier = 0;
    }

    public VanillaPotionItem(int duration, int amplifier) {
        super(new Properties());
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void setAmplifier(int amplifier){
        this.amplifier = amplifier;
    }

    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {

    }

    /*
    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {
        MobEffect effect;
        if (!itemStack.getTags().toList().isEmpty()) {
            effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(itemStack.getTag().getString("Potion")));
        } else {
            return;
        }
        if (effect != null) {
            living.addEffect(new MobEffectInstance(effect, duration, amplifier));this.app
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack,TooltipContext pContext , List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(ModEffects.)

        if (pStack.getTag() != null) {
            ModUtils.addPotionTooltip(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(pStack.getTag().getString("Potion")))
                    , pTooltipComponents, amplifier, duration);
        } else {
            ModUtils.addPotionTooltip(null, pTooltipComponents, 0, 0);
        }
    }

    public static int getColor(ItemStack pStack) {
        if (pStack.getTag() != null){
            return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(pStack.getTag().getString("Potion"))).getColor();
        } else {
            return 0;
        }
    }
    */
}
