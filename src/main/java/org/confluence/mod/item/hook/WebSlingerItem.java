package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.WebSlingerEntity;
import org.confluence.mod.misc.ModRarity;

public class WebSlingerItem extends AbstractHookItem implements IHookFastThrow {
    public WebSlingerItem() {
        super(ModRarity.GREEN);
    }

    @Override
    public int getHookAmount() {
        return 8;
    }

    @Override
    public float getHookRange() {
        return 15.08F;
    }

    @Override
    public float getHookVelocity() {
        return 1.0F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new WebSlingerEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
