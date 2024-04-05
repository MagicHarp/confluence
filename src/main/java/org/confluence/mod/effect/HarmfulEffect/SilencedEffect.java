package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.confluence.mod.item.magic.IMagicAttack;
import org.confluence.mod.item.magic.MagicMirror;
import org.confluence.mod.item.magic.ManaWeapons;

public class SilencedEffect extends MobEffect { //沉默 禁用使用魔力的物品
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL,0xFFFAFA);
    }
}
