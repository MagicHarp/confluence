package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.HookOfDissonanceEntity;
import org.confluence.mod.misc.ModRarity;

public class HookOfDissonanceItem extends AbstractHookItem {
    public HookOfDissonanceItem() {
        super(ModRarity.PINK);
    }

    @Override
    public int getHookAmount() {
        return 1;
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
        return new HookOfDissonanceEntity(item, player, level);
    }

    @Override
    public HookType getHookType() {
        return HookType.SINGLE;
    }
}
