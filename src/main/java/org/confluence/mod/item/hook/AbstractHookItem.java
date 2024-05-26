package org.confluence.mod.item.hook;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.hook.AbstractHookEntity;
import org.jetbrains.annotations.Nullable;
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
            list.removeIf(tag -> getHookEntity(tag, level) == null);
            if (this instanceof IHookFastThrow) {
                if (list.isEmpty()) return true;
                return list.stream().allMatch(tag -> {
                    AbstractHookEntity hookEntity = getHookEntity(tag, level);
                    return hookEntity == null || hookEntity.getHookState() == AbstractHookEntity.HookState.HOOKED;
                });
            }
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

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (ItemStack.isSameItem(newStack, stack)) return;
        if (slotContext.entity().level() instanceof ServerLevel level && stack.getOrCreateTag().get("hooks") instanceof ListTag list) {
            removeAll(list, level);
        }
    }

    public static void removeAll(ListTag list, ServerLevel level) {
        list.removeIf(tag -> {
            AbstractHookEntity hookEntity = getHookEntity(tag, level);
            if (hookEntity != null) hookEntity.discard();
            return true;
        });
    }

    @Nullable
    public static AbstractHookEntity getHookEntity(Tag tag, ServerLevel level) {
        if (tag instanceof CompoundTag compoundTag) {
            return level.getEntity(compoundTag.getInt("id")) instanceof AbstractHookEntity hookEntity ? hookEntity : null;
        }
        return null;
    }

    public enum HookType {
        SINGLE, // 只有一个钩爪
        SIMULTANEOUS, // 有多个钩爪,且可以同时保持
        INDIVIDUAL // 有多个钩爪,但只能保持其一
    }
}
