package org.confluence.mod.common.item.whip;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.confluence.mod.api.whip.WhipDirectHitContext;
import org.confluence.mod.api.whip.WhipTagEffect;

import java.util.function.Supplier;

public final class SnapthornItem extends BaseWhipItem {
    public static final float TAG_DAMAGE = 3.0F;

    public SnapthornItem(Supplier<? extends WhipTagEffect> tagEffect) {
        super("snapthorn", 15F, 0.7F, 1.85F, 15, tagEffect);
    }

    @Override
    public void onDirectHit(WhipDirectHitContext context) {
        context.target().addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1), context.owner());
    }
}
