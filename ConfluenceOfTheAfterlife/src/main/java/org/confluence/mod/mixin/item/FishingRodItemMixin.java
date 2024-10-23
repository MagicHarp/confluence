package org.confluence.mod.mixin.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import org.confluence.mod.entity.fishing.CurioFishingHook;
import org.confluence.mod.item.curio.fishing.FishingBobber;
import org.confluence.mod.item.curio.fishing.ILavaproofFishingHook;
import org.confluence.mod.mixin.accessor.FishingHookAccessor;
import org.confluence.mod.mixinauxiliary.IFishingHook;
import org.confluence.mod.util.CuriosUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private Entity replaceHook(Entity entity) {
        if (!(entity instanceof FishingHook fishingHook)) return entity;
        Optional<FishingBobber> curio = CuriosUtils.findCurio(fishingHook.getPlayerOwner(), FishingBobber.class);
        if (curio.isPresent()) {
            fishingHook = new CurioFishingHook(
                fishingHook.getPlayerOwner(),
                fishingHook.level(),
                ((FishingHookAccessor) fishingHook).getLuck(),
                ((FishingHookAccessor) fishingHook).getLureSpeed(),
                curio.get().variant
            );
        }
        if (CuriosUtils.hasCurio(fishingHook.getPlayerOwner(), ILavaproofFishingHook.class)) {
            ((IFishingHook) fishingHook).confluence$setIsLavaHook();
        }
        return fishingHook;
    }
}
