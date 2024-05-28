package org.confluence.mod.item.hook;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.MimicHookEntity;
import org.confluence.mod.misc.ModRarity;

public class MimicHookItem extends AbstractHookItem {
    private final MimicHookEntity.Variant variant;

    public MimicHookItem(MimicHookEntity.Variant variant) {
        super(ModRarity.LIGHT_PURPLE);
        this.variant = variant;
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
        return 1.5F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        return new MimicHookEntity(item, player, level, variant);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
