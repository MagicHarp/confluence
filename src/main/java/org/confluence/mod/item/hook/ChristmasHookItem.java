package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.ChristmasHookEntity;
import org.confluence.mod.item.ModRarity;

public class ChristmasHookItem extends AbstractHookItem {
    public ChristmasHookItem() {
        super(ModRarity.LIME);
    }

    @Override
    public int getHookAmount() {
        return 3;
    }

    @Override
    public float getHookRange() {
        return 22.92F;
    }

    @Override
    public float getHookVelocity() {
        return 1.55F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new ChristmasHookEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
