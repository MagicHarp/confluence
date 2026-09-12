package org.confluence.mod.common.item.whip;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.confluence.mod.api.whip.WhipFriendlyHitContext;
import org.confluence.mod.api.whip.WhipTagEffect;

import java.util.function.Supplier;

public final class LeatherWhipItem extends BaseWhipItem {
    public static final float TAG_DAMAGE = 1.0F;

    public LeatherWhipItem(Supplier<? extends WhipTagEffect> tagEffect) {
        super("leather_whip", 7F, 0.5F, 0.9F, 15, tagEffect);
    }

    @Override
    public boolean canHitFriendlySummons() {return true;}

    @Override
    public void onFriendlyHit(WhipFriendlyHitContext context) {
        context.summon().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100), context.owner());
    }
}
