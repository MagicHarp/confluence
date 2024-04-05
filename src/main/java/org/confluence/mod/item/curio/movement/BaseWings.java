package org.confluence.mod.item.curio.movement;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.SlotContext;

public class BaseWings extends BaseCurioItem implements IMayFly {
    private final int flyTicks;
    private final double flySpeed;

    public BaseWings(Rarity rarity, int flyTicks, double multiY) {
        super(rarity);
        this.flyTicks = flyTicks;
        this.flySpeed = 0.3 * multiY;
    }

    @Override
    public int getFlyTicks() {
        return flyTicks;
    }

    @Override
    public double getFlySpeed() {
        return flySpeed;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return CuriosUtils.noSameCurio(slotContext.entity(), BaseWings.class);
    }
}
