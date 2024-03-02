package org.confluence.mod.item.sword;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class BoardSwordItem extends SwordItem {
    public BoardSwordItem(Tier tier, int rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public BoardSwordItem(Tier tier, int rawDamage, float rawSpeed, Properties properties) {
        super(tier, (int) (rawDamage - tier.getAttackDamageBonus() - 1), rawSpeed - 4, properties);
    }
}
