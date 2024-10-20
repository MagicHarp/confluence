package org.confluence.mod.common.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.InputEvent;
import org.confluence.mod.common.init.ModEffects;

import java.util.function.Consumer;

public class StonedEffect extends MobEffect {
    public static final Vec3 DOWN = new Vec3(0.0, -1.0, 0.0);

    public StonedEffect() {
        super(MobEffectCategory.HARMFUL, 0x999999);
    }

    public static void onRightClick(LivingEntity entity, Consumer<Boolean> consumer) {
        if (!entity.isSpectator() && !entity.getUseItem().isEmpty() && entity.hasEffect(ModEffects.STONED)) {
            consumer.accept(true);
        }
    }

    public static void onLeftClick(Player player, InputEvent.InteractionKeyMappingTriggered event) {
        if (player.hasEffect(ModEffects.STONED) && (event.isUseItem() || event.isAttack() || event.isPickBlock())) {
            event.setCanceled(true);
        }
    }
}
