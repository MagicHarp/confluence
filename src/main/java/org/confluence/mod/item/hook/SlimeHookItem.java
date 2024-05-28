package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.SlimeHookEntity;
import org.confluence.mod.misc.ModRarity;

public class SlimeHookItem extends AbstractHookItem {
    public SlimeHookItem() {
        super(ModRarity.ORANGE);
    }

    @Override
    public int getHookAmount() {
        return 3;
    }

    @Override
    public float getHookRange() {
        return 12.5F;
    }

    @Override
    public float getHookVelocity() {
        return 1.3F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new SlimeHookEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
