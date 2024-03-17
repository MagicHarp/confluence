package org.confluence.mod.datagen;

import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static net.minecraft.world.item.ItemDisplayContext.*;

public interface Image32x {
    default void preset(ItemModelBuilder builder) {
        builder.transforms()
            .transform(THIRD_PERSON_RIGHT_HAND).translation(0.5F, 9.75F, 0).rotation(-12, 0, 0).end()
            .transform(FIRST_PERSON_RIGHT_HAND).rotation(-20F, -65F, 0).translation(3, 8, 0).scale(1.5F,1.5F,1F).end()
            .transform(FIRST_PERSON_LEFT_HAND).rotation(0, 0, 0).translation(0, 8, 0).scale(0.5F,1F,1F).end()
            .transform(GROUND).scale(1.5F,1.5F,1F).end()
            .transform(GUI).translation(4.5F, 4.5F, 0).scale(1.5F).rotation(0, 0, 0).end()
            .transform(FIXED).translation(0, 0, 0).scale(1.5F).rotation(0, 0, 0).end();
    }
}
