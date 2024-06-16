package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;

public class FrozenTurtleShell extends BaseCurioItem {
    public FrozenTurtleShell() {
        super(ModRarity.PINK);
    }

    public static float apply(LivingEntity living, float amount) {
        if (living.getHealth() / living.getMaxHealth() > 0.5F ||
                CuriosUtils.noSameCurio(living, CurioItems.FROZEN_TURTLE_SHELL.get()) ||
                CuriosUtils.noSameCurio(living, CurioItems.FROZEN_SHIELD.get())
        ) return amount;
        return amount * 0.75F;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.frozen_turtle_shell.info"),
                Component.translatable("item.confluence.frozen_turtle_shell.info2"),
                Component.translatable("item.confluence.frozen_turtle_shell.info3")
        };
    }
}
