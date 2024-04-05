package org.confluence.mod.effect.NeutralEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class GravitationEffect extends MobEffect {
    /*重力药水是一种增益药水，使用时会提供重力增益。

    增益允许玩家通过按下▲ 上来控制重力。

    重力反转后会颠倒世界视角与人物，

    人物将向上坠落，并可在天花板上行走。

    其效果可在空中切换。*/

    public GravitationEffect(){
        super(MobEffectCategory.NEUTRAL,0x7FFFD4);
    }
}
