package org.confluence.mod.mixin.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import org.confluence.mod.item.curio.fishing.ILavaproofFishingHook;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.IFishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private Entity replaceHook(Entity entity) {
        if (entity instanceof FishingHook fishingHook && CuriosUtils.hasCurio(fishingHook.getPlayerOwner(), ILavaproofFishingHook.class)) {
            ((IFishingHook) fishingHook).c$setIsLavaHook();
        }
        return entity;
    }
}
