package org.confluence.mod.datagen;

import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static net.minecraft.world.item.ItemDisplayContext.*;

public interface Image32x {
    default void preset(ItemModelBuilder builder) {
        builder.transforms()
            .transform(THIRD_PERSON_RIGHT_HAND).translation(0, 12, 4).rotation(60, 90, 0).scale(1.5F,1.5F,1F).end()
                .transform(THIRD_PERSON_LEFT_HAND).translation(-1, 8, 12).rotation(0, 85, 0).scale(1.5F,1.5F,1F).end()
            .transform(FIRST_PERSON_RIGHT_HAND).rotation(-20F, -80F, 0).translation(3, 8, 0).scale(1.5F,1.5F,1F).end()
            .transform(FIRST_PERSON_LEFT_HAND).rotation(0, 105F, 0).translation(0, 8, 5).scale(0.5F,1F,1F).end()
            .transform(GROUND).scale(1F,1F,0.5F).end()
            .transform(GUI).translation(4.5F, 4.5F, 0).scale(1.5F).rotation(0, 0, 0).end()
            .transform(FIXED).translation(-4.5F, 4.5F, 0).scale(1.5F).rotation(0, 0, 90).end();
    }
}
