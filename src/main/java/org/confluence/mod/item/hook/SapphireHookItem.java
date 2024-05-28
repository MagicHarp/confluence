package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.BaseHookEntity;
import org.confluence.mod.misc.ModRarity;

public class SapphireHookItem extends AbstractHookItem{
    public SapphireHookItem() {
        super(ModRarity.BLUE);
    }

    @Override
    public int getHookAmount() {
        return 1;
    }

    @Override
    public float getHookRange() {
        return 15.0F;
    }

    @Override
    public float getHookVelocity() {
        return 1.1F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new BaseHookEntity(item, player, level, BaseHookEntity.Variant.SAPPHIRE);
    }

    @Override
    public HookType getHookType() {
        return HookType.SINGLE;
    }
}
