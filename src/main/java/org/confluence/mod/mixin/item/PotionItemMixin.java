package org.confluence.mod.mixin.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.block.crafting.AlchemyTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(PotionItem.class)
public class PotionItemMixin {
    @Inject(method = "appendHoverText", at = @At("RETURN"))
    private void append(ItemStack pStack, Level pLevel, List<Component> pTooltip, TooltipFlag pFlag, CallbackInfo ci){
        if (pStack.getItem() instanceof PotionItem){
            String potionId = pStack.getOrCreateTag().getString("Potion");
            Potion effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(potionId));
            List<Integer> colors = new ArrayList<>();
            if (effect != null) {
                for (MobEffectInstance effectInstance : effect.getEffects()) {
                    colors.add(effectInstance.getEffect().getColor());
                }
            }
            pTooltip.add(Component.translatable("info.confluence.potion_mana", AlchemyTableBlock.calculateAverage(colors)));
        }
    }
}
