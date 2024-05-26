package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.IvyWhipEntity;
import org.confluence.mod.item.ModRarity;

public class IvyWhipItem extends AbstractHookItem {
    public IvyWhipItem() {
        super(ModRarity.ORANGE);
    }

    @Override
    public int getHookAmount() {
        return 3;
    }

    @Override
    public float getHookRange() {
        return 18.67F;
    }

    @Override
    public float getHookVelocity() {
        return 1.3F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new IvyWhipEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
