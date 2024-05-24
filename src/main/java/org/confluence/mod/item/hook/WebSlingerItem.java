package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.item.ModRarity;

public class WebSlingerItem extends AbstractHookItem {
    public WebSlingerItem() {
        super(ModRarity.GREEN);
    }

    @Override
    public int getHookAmount() {
        return 0;
    }

    @Override
    public float getHookRange() {
        return 0;
    }

    @Override
    public float getHookVelocity() {
        return 0;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return null;
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
