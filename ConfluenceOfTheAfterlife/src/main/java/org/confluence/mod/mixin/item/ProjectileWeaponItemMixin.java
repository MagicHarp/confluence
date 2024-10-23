package org.confluence.mod.mixin.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.confluence.mod.common.item.bow.ShortBowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {
    @Inject(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void modifyProjectile(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, LivingEntity target, CallbackInfo ci, float f, float f1, float f2, float f3, int i, ItemStack itemstack, float f4, Projectile projectile) {
        if (projectile instanceof AbstractArrow abstractArrow) {
            ShortBowItem.applyToArrow(weapon, abstractArrow);
        }
    }

//    @WrapWithCondition(method = "useAmmo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;split(I)Lnet/minecraft/world/item/ItemStack;"))
//    private static boolean canAmmoUse(ItemStack instance, int amount, @Local(argsOnly = true, ordinal = 1) ItemStack ammo, @Local(argsOnly = true) LivingEntity shooter) {
//        return !(ammo.getItem() instanceof ArrowItem) || MagicQuiver.shouldConsume(shooter);
//    }
}
