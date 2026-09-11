package org.confluence.mod.mixin.server.network;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.confluence.lib.util.LibUtils;
import org.confluence.mod.mixed.IServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    /// 允许创造模式同步使用全局扩展堆叠上限的物品栈。
    @Definition(id = "itemstack", local = @Local(type = ItemStack.class))
    @Definition(id = "getCount", method = "Lnet/minecraft/world/item/ItemStack;getCount()I")
    @Expression("itemstack.getCount() <= ?")
    @ModifyExpressionValue(method = "handleSetCreativeModeSlot", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean extendCreativeStackLimit(boolean original, @Local ItemStack itemstack) {
        if (original) return true;
        return itemstack.getCount() <= LibUtils.getMaxStackSize(64);
    }

    @WrapOperation(method = {"handleMoveVehicle", "handleMovePlayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;checkMovementStatistics(DDD)V"))
    private void captureSpeed(ServerPlayer instance, double x, double y, double z, Operation<Void> original) {
        IServerPlayer.of(instance).confluence$getMovementSpeed().set(x, y, z);
        original.call(instance, x, y, z);
    }
}
