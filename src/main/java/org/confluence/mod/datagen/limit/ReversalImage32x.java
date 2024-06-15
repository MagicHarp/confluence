package org.confluence.mod.datagen.limit;

import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static net.minecraft.world.item.ItemDisplayContext.*;

public interface ReversalImage32x {
    default void preset(ItemModelBuilder builder) {
        builder.transforms()
            .transform(THIRD_PERSON_RIGHT_HAND).translation(0, 12, 4).rotation(60, 90, 0).scale(1.5F, 1.5F, 1F).end()
            .transform(THIRD_PERSON_LEFT_HAND).translation(-1, 8, 12).rotation(0, 85, 0).scale(1.5F, 1.5F, 1F).end()
            .transform(FIRST_PERSON_RIGHT_HAND).rotation(-10F,100F, 80).translation(5, 8, -1).scale(1.5F, 1.5F, 1F).end()
            /*  数据 分析                                     x轴自转                  x   y
                                                                            越大越右   越大越上  越大越后
            不用管我这构史分析，调模型用的
             */
            .transform(FIRST_PERSON_LEFT_HAND).rotation(0, 105F, 0).translation(0, 8, 5).scale(0.5F, 1F, 1F).end()
            .transform(GROUND).scale(1F, 1F, 0.5F).translation(0F, 4F, 0).end()
            .transform(GUI).translation(4.5F, 4.5F, 0).scale(1.5F).rotation(0, 0, 0).end()
            .transform(FIXED).translation(-4.5F, 4.5F, 0).scale(1.5F).rotation(0, 0, 90).end();
    }
}
