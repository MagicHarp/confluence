package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MagicPowerEffect extends MobEffect {
    public static final String MAGIC_UUID = "FB752F0E-82C8-A55F-A699-72FDF6B14AB8";

    public MagicPowerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCC00CC);
    }
}
