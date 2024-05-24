package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.BaseHookEntity;
import org.confluence.mod.item.ModRarity;

public class EmeraldHookItem extends AbstractHookItem{
    public EmeraldHookItem() {
        super(ModRarity.BLUE);
    }

    @Override
    public int getHookAmount() {
        return 0;
    }

    @Override
    public float getHookRange() {
        return 16.25F;
    }

    @Override
    public float getHookVelocity() {
        return 1.15F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new BaseHookEntity(item, player, level, BaseHookEntity.Variant.EMERALD);
    }

    @Override
    public HookType getHookType() {
        return HookType.SINGLE;
    }
}
