package org.confluence.mod.item.bow;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.mixinauxiliary.IAbstractArrow;
import org.jetbrains.annotations.NotNull;

public class RoutineBowItem extends BowItem implements CustomModel {
    private final float baseDamage;

    public RoutineBowItem(float baseDamage, Properties pProperties) {
        super(pProperties);
        this.baseDamage = baseDamage;
    }



    @Override
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow) {
        arrow.setBaseDamage(baseDamage);
        return arrow;
    }

}
