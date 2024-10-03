package org.confluence.mod.datagen.limit;

import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static net.minecraft.world.item.ItemDisplayContext.*;

public interface Image24xAdjustmentExamples {
    default void preset(ItemModelBuilder builder) {
        builder.transforms()
            .transform(THIRD_PERSON_RIGHT_HAND).translation(0, 14, 2).rotation(60, 90, 0).scale(1.5F, 1.5F, 1F).end()
            .transform(THIRD_PERSON_LEFT_HAND).translation(-1, 14, 2).rotation(-45, 85, 0).scale(1.5F, 1.5F, 1F).end()
            .transform(FIRST_PERSON_RIGHT_HAND).rotation(-20F, -80F, 0).translation(3, 8, 0).scale(1.5F, 1.5F, 1F).end()
            .transform(FIRST_PERSON_LEFT_HAND).rotation(0, 105F, 0).translation(0, 8, 5).scale(0.5F, 1F, 1F).end()
            .transform(GROUND).scale(1F, 1F, 0.5F).translation(0F, 4F, 0).end()
            .transform(GUI).translation(2.8F, 2.8F, 0).scale(1.3F).rotation(0, 0, 0).end()
            .transform(FIXED).translation(-4.5F, 4.5F, 0).scale(1.5F).rotation(0, 0, 90).end();
    }
}
