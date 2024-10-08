package org.confluence.mod.capability.prefix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.gun.AbstractGunItem;
import org.confluence.mod.item.mana.IManaWeapon;

import java.util.Optional;

public final class PrefixProvider {
    public static final String KEY = "confluence:prefix";

    public static boolean canInit(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof IUniversalOnly || item instanceof IManaWeapon || item instanceof BaseCurioItem) {
            return true;
        } else if (item instanceof Vanishable) {
            if (item instanceof SwordItem || item instanceof DiggerItem) {
                return true;
            } else {
                return item instanceof BowItem || item instanceof CrossbowItem || item instanceof AbstractGunItem;
            }
        }
        return false;
    }

    public static void initPrefix(ServerPlayer serverPlayer, ItemStack itemStack) {
        Item item = itemStack.getItem();
        RandomSource randomSource = serverPlayer.level().random;
        if (item instanceof IUniversalOnly) {
            create(randomSource, itemStack, PrefixType.UNIVERSAL);
        } else if (item instanceof Vanishable) {
            if (item instanceof SwordItem || item instanceof DiggerItem) {
                create(randomSource, itemStack, PrefixType.MELEE);
            } else if (item instanceof BowItem || item instanceof CrossbowItem || item instanceof AbstractGunItem) { // 三叉戟不好弄
                create(randomSource, itemStack, PrefixType.RANGED);
            }
        } else if (item instanceof IManaWeapon) {
            create(randomSource, itemStack, PrefixType.MAGIC);
        } else if (item instanceof BaseCurioItem) {
            create(randomSource, itemStack, PrefixType.CURIO);
        }
    }

    public static void create(RandomSource randomSource, ItemStack itemStack, PrefixType prefixType) {
        if (itemStack.getOrCreateTag().contains(KEY, Tag.TAG_COMPOUND)) return;
        ModPrefix modPrefix = prefixType.randomPrefix(randomSource);
        if (modPrefix.isHarmful() && randomSource.nextFloat() < 0.6667F) prefixType = PrefixType.UNKNOWN;
        new ItemPrefix(prefixType, itemStack).copyFrom(modPrefix);
    }

    public static Optional<ItemPrefix> getPrefix(ItemStack itemStack) {
        CompoundTag prefix = itemStack.getTagElement(KEY);
        if (prefix == null) return Optional.empty();
        ItemPrefix itemPrefix = new ItemPrefix(itemStack);
        itemPrefix.deserializeNBT(prefix);
        if (itemPrefix.type == PrefixType.UNKNOWN) return Optional.empty();
        return Optional.of(itemPrefix);
    }

    public static void random(RandomSource randomSource, ItemStack itemStack, PrefixType prefixType) {
        new ItemPrefix(prefixType, itemStack).copyFrom(prefixType.randomPrefix(randomSource));
    }

    public static void unknown(ItemStack itemStack) {
        itemStack.getOrCreateTag().put(PrefixProvider.KEY, new ItemPrefix(PrefixType.UNKNOWN, itemStack).serializeNBT());
    }
}
