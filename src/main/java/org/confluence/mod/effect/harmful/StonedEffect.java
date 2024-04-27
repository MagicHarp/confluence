package org.confluence.mod.effect.harmful;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.InputEvent;
import org.confluence.mod.effect.ModEffects;

import java.util.function.Consumer;

public class StonedEffect extends MobEffect {
    public StonedEffect() {
        super(MobEffectCategory.HARMFUL, 0x999999);
    }

    public static void onRightClick(LivingEntity entity, Consumer<Boolean> consumer) {
        if (!entity.isSpectator() && !entity.getUseItem().isEmpty() && entity.hasEffect(ModEffects.STONED.get())) {
            consumer.accept(true);
        }
    }

    public static void onLeftClick(LocalPlayer player, InputEvent.InteractionKeyMappingTriggered event) {
        if (player.hasEffect(ModEffects.STONED.get()) && (event.isUseItem() || event.isAttack() || event.isPickBlock())) {
            event.setCanceled(true);
        }
    }
}
