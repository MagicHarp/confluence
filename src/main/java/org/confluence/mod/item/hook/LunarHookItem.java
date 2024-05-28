package org.confluence.mod.item.hook;

import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.confluence.mod.entity.hook.LunarHookEntity;
import org.confluence.mod.misc.ModRarity;

import java.util.concurrent.atomic.AtomicBoolean;

public class LunarHookItem extends AbstractHookItem implements IHookFastThrow {
    public LunarHookItem() {
        super(ModRarity.RED);
    }

    @Override
    public int getHookAmount() {
        return 4;
    }

    @Override
    public float getHookRange() {
        return 22.92F;
    }

    @Override
    public float getHookVelocity() {
        return 1.8F;
    }

    @Override
    public AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level) {
        if (itemStack.getOrCreateTag().get("hooks") instanceof ListTag list) {
            AtomicBoolean nebula = new AtomicBoolean(true);
            AtomicBoolean solar = new AtomicBoolean(true);
            AtomicBoolean stardust = new AtomicBoolean(true);
            AtomicBoolean vortex = new AtomicBoolean(true);
            list.forEach(tag -> {
                AbstractHookEntity hookEntity = getHookEntity(tag, (ServerLevel) level);
                if (hookEntity instanceof LunarHookEntity lunarHookEntity) {
                    switch (lunarHookEntity.getVariant()) {
                        case NEBULA -> nebula.set(false);
                        case SOLAR -> solar.set(false);
                        case STARDUST -> stardust.set(false);
                        case VORTEX -> vortex.set(false);
                    }
                }
            });
            if (nebula.get()) return new LunarHookEntity(item, player, level, LunarHookEntity.Variant.NEBULA);
            if (solar.get()) return new LunarHookEntity(item, player, level, LunarHookEntity.Variant.SOLAR);
            if (stardust.get()) return new LunarHookEntity(item, player, level, LunarHookEntity.Variant.STARDUST);
            if (vortex.get()) return new LunarHookEntity(item, player, level, LunarHookEntity.Variant.VORTEX);
        }
        return new LunarHookEntity(item, player, level, LunarHookEntity.Variant.NEBULA);
    }

    @Override
    public HookType getHookType() {
        return HookType.SIMULTANEOUS;
    }
}
