package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class BuilderEffect extends MobEffect {  //建筑工 增加触及距离
    public static final String BUILDER_UUID = "02B12F05-C426-AC6F-953C-AC0DBE1CEB57";

    public BuilderEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8B6914);
        addAttributeModifier(ForgeMod.BLOCK_REACH.get(), BUILDER_UUID, 3, AttributeModifier.Operation.ADDITION);
    }
}
