package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class NutrientSolution extends BaseCurioItem implements EffectInvul.Weakness, EffectInvul.Hunger {
    public NutrientSolution() {
        super(ModRarity.PINK);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.nutrient_solution.info"),
            Component.translatable("item.confluence.nutrient_solution.info2")
        };
    }
}
