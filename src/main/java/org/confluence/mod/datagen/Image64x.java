package org.confluence.mod.datagen;

import net.minecraftforge.client.model.generators.ItemModelBuilder;

import static net.minecraft.world.item.ItemDisplayContext.*;

public interface Image64x {
    default void preset(ItemModelBuilder builder) {
        // 还没设置
        builder.transforms()
            .transform(THIRD_PERSON_RIGHT_HAND).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end()
            .transform(FIRST_PERSON_LEFT_HAND).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end()
            .transform(FIRST_PERSON_RIGHT_HAND).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end()
            .transform(FIRST_PERSON_LEFT_HAND).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end()
            .transform(GROUND).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end()
            .transform(GUI).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end()
            .transform(FIXED).translation(0, 0, 0).scale(1).rotation(0, 0, 0).end();
    }
}
