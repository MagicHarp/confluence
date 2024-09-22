package org.confluence.mod.mixinauxiliary;

public interface Immunity {
    enum Types{
        /** 静态无敌帧，以类而不是对象区分不同的伤害，比如魔刺，多个魔刺弹幕叠在一起伤害频率也不会变快 */
        STATIC,
        /** 局部无敌帧，以对象区分不同的伤害，比如召唤物，多个同种召唤物同时击中不会骗伤 */
        LOCAL
    }
    Types confluence$getImmunityType();

    default int confluence$getImmunityDuration(){
        return 0;
    }
}
