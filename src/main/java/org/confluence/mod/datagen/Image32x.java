package org.confluence.mod.datagen;

import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static net.minecraft.world.item.ItemDisplayContext.*;

public interface Image32x {
    default void preset(ItemModelBuilder builder) {
        builder.transforms()
            .transform(THIRD_PERSON_RIGHT_HAND).translation(0.5F, 9.75F, 0).rotation(-12, 0, 0).end()
            .transform(FIRST_PERSON_LEFT_HAND).translation(0, 8, 6.5F).rotation(-11, 0, 0).end()
            .transform(FIRST_PERSON_RIGHT_HAND).rotation(-74, 0, 0).end()
            .transform(FIRST_PERSON_LEFT_HAND).rotation(-71, 0, 0).end()
            .transform(GROUND).scale(0.5F).end()
            .transform(GUI).translation(-2.75F, -3, 0).scale(0.45F).rotation(0, 90, 0).end()
            .transform(FIXED).translation(3, -2.75F, 0).scale(0.5F).rotation(0, -90, 0).end();
    }
}
