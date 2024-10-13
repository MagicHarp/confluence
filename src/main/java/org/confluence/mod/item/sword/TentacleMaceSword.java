package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.Image24x;
import org.confluence.mod.datagen.limit.ReversalImage16x;

public class TentacleMaceSword extends SwordItem implements ReversalImage16x {
    public TentacleMaceSword(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, rawDamage, rawSpeed, properties);
    }
}