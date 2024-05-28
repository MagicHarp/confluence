package org.confluence.mod.item.hook;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.DualHookEntity;
import org.confluence.mod.misc.ModRarity;

public class DualHookItem extends AbstractHookItem {
    public DualHookItem() {
        super(ModRarity.LIGHT_RED);
    }

    @Override
    public int getHookAmount() {
        return 2;
    }

    @Override
    public float getHookRange() {
        return 18.33F;
    }

    @Override
    public float getHookVelocity() {
        return 1.4F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        CompoundTag tag = itemStack.getOrCreateTag();
        boolean isRed = tag.getBoolean("isRed");
        tag.putBoolean("isRed", !isRed);
        return new DualHookEntity(item, player, level, isRed ? DualHookEntity.Variant.RED : DualHookEntity.Variant.BLUE);
    }

    @Override
    public HookType getHookType() {
        return HookType.INDIVIDUAL;
    }
}
