package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.FishHookEntity;
import org.confluence.mod.misc.ModRarity;

public class FishHookItem extends AbstractHookItem implements IHookFastThrow {
    public FishHookItem() {
        super(ModRarity.ORANGE);
    }

    @Override
    public int getHookAmount() {
        return 2;
    }

    @Override
    public float getHookRange() {
        return 16.67F;
    }

    @Override
    public float getHookVelocity() {
        return 1.3F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new FishHookEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
