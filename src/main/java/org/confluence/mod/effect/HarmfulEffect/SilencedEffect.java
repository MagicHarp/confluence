package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.confluence.mod.event.EffectEvents;
import org.confluence.mod.item.magic.IMagicAttack;

public class SilencedEffect extends MobEffect { //沉默 禁用使用魔力的物品
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFAFA);
    }

    public static void noUseMagicItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.MAIN_HAND && event.getItemStack().getItem() instanceof IMagicAttack) {
            event.setCanceled(true);
        }
    }

    public void onAdd(Player player) {
        if (player.getMainHandItem().getItem() instanceof IMagicAttack) {
            noUseMagicItem(new PlayerInteractEvent.RightClickItem(player, InteractionHand.MAIN_HAND));
        }
    }
}
