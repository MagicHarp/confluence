package org.confluence.mod.capability.prefix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import org.confluence.mod.item.ModPrefix;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.mana.IManaWeapon;

import java.util.Optional;

public final class PrefixProvider {
    public static final String KEY = "confluence:prefix";

    public static void initPrefix(ServerPlayer serverPlayer, ItemStack itemStack) {
        Item item = itemStack.getItem();
        RandomSource randomSource = serverPlayer.level().random;
        if (item instanceof IUniversalOnly) {
            create(randomSource, itemStack, PrefixType.UNIVERSAL);
        } else if (item instanceof Vanishable) {
            if (item instanceof SwordItem || item instanceof DiggerItem) {
                create(randomSource, itemStack, PrefixType.MELEE);
            } else if (item instanceof TridentItem || item instanceof BowItem || item instanceof CrossbowItem) {
                create(randomSource, itemStack, PrefixType.RANGED);
            }
        } else if (item instanceof IManaWeapon) {
            create(randomSource, itemStack, PrefixType.MAGIC_AND_SUMMING);
        } else if (item instanceof BaseCurioItem) {
            create(randomSource, itemStack, PrefixType.CURIO);
        }
    }

    private static void create(RandomSource randomSource, ItemStack itemStack, PrefixType prefixType) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.contains(KEY, Tag.TAG_COMPOUND)) return;
        ModPrefix modPrefix = randomPrefix(randomSource, prefixType);
        if (modPrefix.isHarmful() && randomSource.nextFloat() < 0.6667F) return;
        ItemPrefix itemPrefix = new ItemPrefix(prefixType);
        modPrefix.copyTo(itemPrefix);
        nbt.put(KEY, itemPrefix.serializeNBT());
    }

    public static ModPrefix randomPrefix(RandomSource random, PrefixType type) {
        Enum<? extends ModPrefix>[] values = type.available[random.nextInt(type.available.length)];
        return (ModPrefix) values[random.nextInt(values.length)];
    }

    public static Optional<ItemPrefix> getPrefix(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getTag();
        if (nbt == null || !nbt.contains(KEY, Tag.TAG_COMPOUND)) return Optional.empty();
        CompoundTag prefix = nbt.getCompound(KEY);
        ItemPrefix itemPrefix = new ItemPrefix(PrefixType.valueOf(prefix.getString("type")));
        itemPrefix.deserializeNBT(nbt);
        return Optional.of(itemPrefix);
    }

    public static void updatePrefix(ItemStack itemStack, ModPrefix modPrefix) {
        getPrefix(itemStack).ifPresent(itemPrefix -> {
            modPrefix.copyTo(itemPrefix);
            itemStack.getOrCreateTag().put(KEY, itemPrefix.serializeNBT());
        });
    }
}
