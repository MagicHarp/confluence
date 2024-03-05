package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.confluence.mod.datagen.Image32x;

public class BoardSwordItem extends SwordItem implements Image32x {
    public BoardSwordItem(Tier tier, int rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public BoardSwordItem(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, (int) (rawDamage - tier.getAttackDamageBonus() - 1), rawSpeed - 4, properties);
    }
}
