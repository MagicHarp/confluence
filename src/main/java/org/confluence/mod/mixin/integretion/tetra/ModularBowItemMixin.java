package org.confluence.mod.mixin.integretion.tetra;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.combat.MoltenQuiver;
import org.confluence.mod.misc.ModAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Function;

@Pseudo
@Mixin(targets = "se.mickelus.tetra.items.modular.impl.bow.ModularBowItem", remap = false)
public abstract class ModularBowItemMixin {
    @SuppressWarnings("all")
    @Inject(method = "fireProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void apply(ItemStack itemStack, Level world, ArrowItem ammoItem, ItemStack ammoStack, ImmutableList<Function<AbstractArrow, AbstractArrow>> projectileRemappers, Player player, float basePitch, float yaw, float projectileVelocity, float accuracy, int drawProgress, double strength, int powerLevel, int punchLevel, int flameLevel, int piercingLevel, boolean hasSuspend, boolean infiniteAmmo, CallbackInfo ci, AbstractArrow abstractarrow) {
        ModAttributes.applyToArrow(player, abstractarrow);
        MoltenQuiver.applyToArrow(player, abstractarrow);
    }
}
