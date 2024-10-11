package org.confluence.mod.mixin.integretion.tetra;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.item.curio.combat.MagicQuiver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "se.mickelus.tetra.items.modular.impl.bow.ModularBowItem", remap = false)
public abstract class ModularBowItemMixin {
    @WrapWithCondition(method = "fireArrow", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private boolean canShrink(ItemStack instance, int pDecrement, @Local Player player) {
        if (player.level().isClientSide) return true;
        return MagicQuiver.shouldConsume(player);
    }
}
