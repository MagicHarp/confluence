package org.confluence.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.NeutralMob;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.helper.HunterHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Minecraft.class)
public abstract class MinectaftMixin {

    @Inject(method = "shouldEntityAppearGlowing",at = @At(value ="HEAD"),cancellable = true)
    public void changeGlowOutline(Entity pentity, CallbackInfoReturnable<Boolean> cir){
        if(Minecraft.getInstance().player !=null &&
                Minecraft.getInstance().player.hasEffect(ModEffects.HUNTER.get())
        ){
            if(KeyBindings.TAB.get().isDown()){
                cir.setReturnValue(true);
                return;
            }
            HunterHelper helper = HunterHelper.getSingleton();
            //中立生物
            if(pentity instanceof NeutralMob && helper.alwaysShowNeutral){
                cir.setReturnValue(true);
                return;
            }
            AtomicBoolean flag = new AtomicBoolean(false);
            try{
                helper.colorMap.forEach((k,v)->{
                    if(k.isAssignableFrom(pentity.getClass())) {
                        cir.setReturnValue(v.alwaysShow());
                        flag.set(true);
                        throw new RuntimeException("break");
                    }
                });
            }catch (RuntimeException e){
                if(flag.get()) return;
            }
            cir.setReturnValue(false);
        }
    }

}
