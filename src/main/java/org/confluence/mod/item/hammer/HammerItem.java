package org.confluence.mod.item.hammer;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, float rawDamage, float rawSpeed) {
        this(tier, rawDamage, rawSpeed, new Properties());
    }

    public HammerItem(Tier tier, float rawDamage, float rawSpeed, Properties properties) {
        super(rawDamage - tier.getAttackDamageBonus() - 1, rawSpeed - 4, tier, BlockTags.WALLS, properties);
    }
}
