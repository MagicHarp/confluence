package org.confluence.mod.common.item.whip;

import net.minecraft.world.effect.MobEffectInstance;
import org.confluence.mod.api.whip.WhipDirectHitContext;
import org.confluence.mod.api.whip.WhipTagEffect;
import org.confluence.mod.common.init.ModEffects;

import java.util.function.Supplier;

public final class FirecrackerItem extends BaseWhipItem {
    public static final float TAG_DAMAGE = 0.0F;

    public FirecrackerItem(Supplier<? extends WhipTagEffect> tagEffect) {
        super("firecracker", 34F, 0.5F, 1.85F, 15, tagEffect);
    }

    @Override
    public void onDirectHit(WhipDirectHitContext context) {
        context.target().addEffect(new MobEffectInstance(ModEffects.HELLFIRE.get(), 40), context.owner());
    }
}
