package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Enemy;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.helper.GlowingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinectaftMixin {

    @Inject(method = "shouldEntityAppearGlowing", at = @At(value = "HEAD"), cancellable = true)
    public void changeGlowOutline(Entity pentity, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            GlowingHelper helper = GlowingHelper.getHunterHelper();
            //狩猎药水
            if (Minecraft.getInstance().player.hasEffect(ModEffects.HUNTER.get())) {

                //自定义类别 中立生物不计入其中
                for (var n : helper.hunterCatalog)
                    if (n.isAssignableFrom(pentity.getClass())) {
                        if (KeyBindings.TAB.get().isDown()) {
                            cir.setReturnValue(true);
                            return;
                        }
                        cir.setReturnValue(helper.colorMap.get(n).alwaysShow());
                        return;
                    }
                //敌人
                if (pentity instanceof Enemy) {
                    cir.setReturnValue(true);
                    return;
                }
                //中立生物
                if (pentity instanceof NeutralMob) {
                    if (KeyBindings.TAB.get().isDown()) {
                        cir.setReturnValue(true);
                        return;
                    }
                    if (helper.alwaysShowNeutral) {
                        cir.setReturnValue(true);
                        return;
                    }
                }



            }

            //危险感知药水
            if (Minecraft.getInstance().player.hasEffect(ModEffects.DANGER_SENSE.get())) {
                for (var n : helper.dangerCatalog) {
                    if (n.isAssignableFrom(pentity.getClass())) {
                        if (KeyBindings.TAB.get().isDown()) {
                            cir.setReturnValue(true);
                            return;
                        }
                        cir.setReturnValue(helper.colorMap.get(n).alwaysShow());
                        return;
                    }
                }
            }

            cir.setReturnValue(false);
        }
    }

}
