package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.ThornHookEntity;
import org.confluence.mod.item.ModRarity;

public class ThornHookItem extends AbstractHookItem {
    public ThornHookItem() {
        super(ModRarity.LIGHT_PURPLE);
    }

    @Override
    public int getHookAmount() {
        return 3;
    }

    @Override
    public float getHookRange() {
        return 20.0F;
    }

    @Override
    public float getHookVelocity() {
        return 1.6F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new ThornHookEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
