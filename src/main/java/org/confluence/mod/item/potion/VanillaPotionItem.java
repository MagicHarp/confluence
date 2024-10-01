package org.confluence.mod.item.potion;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {
        MobEffect effect;
        if (itemStack.getTag() != null) {
            effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(itemStack.getTag().getString("Potion")));
        } else {
            return;
        }
        if (effect != null) {
            living.addEffect(new MobEffectInstance(effect, duration, amplifier));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag() != null) {
            pTooltipComponents.add(Component.translatable(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(pStack.getTag().getString("Potion"))).getDescriptionId()));
        }
    }

    public static int getColor(ItemStack pStack) {
        if (pStack.getTag() != null){
            return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(pStack.getTag().getString("Potion"))).getColor();
        } else {
            return 0;
        }
    }
}
