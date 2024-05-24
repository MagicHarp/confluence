package org.confluence.mod.item.hook;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class AbstractHookItem extends Item implements ICurioItem {
    public AbstractHookItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    public AbstractHookItem(Rarity rarity) {
        this(new Properties().rarity(rarity));
    }

    public abstract int getHookAmount();

    public abstract float getHookRange();

    public abstract float getHookVelocity();

    public abstract AbstractHookEntity getHook(ItemStack itemStack, AbstractHookItem item, Player player, Level level);

    public abstract HookType getHookType();

    public boolean canHook(ServerLevel level, ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.get("hooks") instanceof ListTag list) {
            list.removeIf(tag -> level.getEntity(((CompoundTag) tag).getInt("id")) == null);
            return list.size() <= getHookAmount();
        } else {
            nbt.put("hooks", new ListTag());
            return true;
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return "hook".equals(slotContext.identifier());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return canEquip(slotContext, stack);
    }

    public enum HookType {
        SINGLE, // 只有一个钩爪
        SIMULTANEOUS, // 有多个钩爪,且可以同时保持
        INDIVIDUAL // 有多个钩爪,但只能保持其一
    }
}
