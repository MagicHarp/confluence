package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.SkeletronHandEntity;
import org.confluence.mod.misc.ModRarity;

public class SkeletronHandItem extends AbstractHookItem {
    public SkeletronHandItem() {
        super(ModRarity.GREEN);
    }

    @Override
    public int getHookAmount() {
        return 2;
    }

    @Override
    public float getHookRange() {
        return 14.58F;
    }

    @Override
    public float getHookVelocity() {
        return 1.5F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new SkeletronHandEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
