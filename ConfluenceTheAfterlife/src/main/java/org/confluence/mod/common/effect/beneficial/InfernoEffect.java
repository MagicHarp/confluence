package org.confluence.mod.common.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class InfernoEffect extends MobEffect {  //狱火 点燃周围的怪物 （以玩家为中心的5×5×5范围内）
    public InfernoEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
    }
}
