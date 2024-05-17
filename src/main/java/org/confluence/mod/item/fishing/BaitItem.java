package org.confluence.mod.item.fishing;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.ModRarity;

public class BaitItem extends Item implements IBait {
    private final float bonus;

    public BaitItem(Rarity rarity, float bonus) {
        super(new Properties().rarity(rarity));
        this.bonus = bonus;
    }

    public BaitItem(float power) {
        this(ModRarity.BLUE, power);
    }

    @Override
    public float getBaitBonus() {
        return bonus;
    }
}
