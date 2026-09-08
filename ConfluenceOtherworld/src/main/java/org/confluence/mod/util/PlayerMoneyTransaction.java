package org.confluence.mod.util;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.common.attachment.ExtraInventory;
import org.confluence.mod.common.attachment.PlayerPiggyBankContainer;
import org.confluence.mod.common.init.ModTags;
import org.confluence.mod.common.init.item.ModItems;
import org.confluence.mod.common.item.common.CoinItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.confluence.mod.common.attachment.ExtraInventory.SIZE_COINS;

/// 为玩家钱币提供先规划、后提交的原子扣款。
public final class PlayerMoneyTransaction {
    private PlayerMoneyTransaction() {}

    /// 从主背包、钱币栏以及可选存钱罐中扣款。
    ///
    /// @return 扣款已经完整提交时为 {@code true}；资金或找零空间不足时为 {@code false}
    public static boolean debit(Player player, long cost, boolean includePiggyBank) {
        return execute(player, cost, includePiggyBank, ItemStack.EMPTY);
    }

    /// 在同一事务中扣款并把商品放入玩家主背包。
    public static boolean purchase(Player player, long cost, boolean includePiggyBank, ItemStack result) {
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Purchase result cannot be empty");
        }
        return execute(player, cost, includePiggyBank, result);
    }

    /// 把售回所得的钱币完整写入钱包；空间不足时不写入任何钱币。
    public static boolean credit(Player player, long amount, boolean includePiggyBank) {
        if (amount < 0) {
            throw new IllegalArgumentException("Money credit cannot be negative");
        }
        if (amount == 0) {
            return true;
        }

        try {
            return creditChecked(player, amount, includePiggyBank);
        } catch (ArithmeticException ignored) {
            // 数据或附属内容给出的金额无法用 long 精确表示时，拒绝整笔事务。
            return false;
        }
    }

    /// 从玩家主背包的指定槽位移除物品并结算售出所得。
    public static boolean creditFromInventory(Player player, int sourceSlot, ItemStack expectedStack, long amount, boolean includePiggyBank) {
        if (sourceSlot < 0 || sourceSlot >= player.getInventory().items.size() || expectedStack.isEmpty() || amount <= 0) {
            return false;
        }
        try {
            return creditFromInventoryChecked(player, sourceSlot, expectedStack, amount, includePiggyBank);
        } catch (ArithmeticException ignored) {
            return false;
        }
    }

    private static boolean creditChecked(Player player, long amount, boolean includePiggyBank) {
        Inventory inventory = player.getInventory();
        ExtraInventory extraInventory = ExtraInventory.of(player);
        PlayerPiggyBankContainer piggyBank = includePiggyBank ? PlayerPiggyBankContainer.of(player) : null;
        List<ItemStack> inventoryCopy = copyStacks(inventory.items);
        List<ItemStack> extraCopy = copyStacks(extraInventory.getAllCoins());
        List<ItemStack> piggyCopy = piggyBank == null ? List.of() : copyContainer(piggyBank);

        boolean credited = addMoneyToFirstAvailableWallet(amount, extraCopy, piggyCopy, inventoryCopy);
        if (!credited) {
            return false;
        }

        commitInventory(inventory, inventoryCopy);
        commitExtraInventory(extraInventory, extraCopy);
        if (piggyBank != null) {
            commitContainer(piggyBank, piggyCopy);
        }
        return true;
    }

    private static boolean creditFromInventoryChecked(Player player, int sourceSlot, ItemStack expectedStack, long amount, boolean includePiggyBank) {
        Inventory inventory = player.getInventory();
        ExtraInventory extraInventory = ExtraInventory.of(player);
        PlayerPiggyBankContainer piggyBank = includePiggyBank ? PlayerPiggyBankContainer.of(player) : null;
        ItemStack source = inventory.items.get(sourceSlot);
        if (!ItemStack.matches(source, expectedStack)) return false;

        List<ItemStack> inventoryCopy = copyStacks(inventory.items);
        List<ItemStack> extraCopy = copyStacks(extraInventory.getAllCoins());
        List<ItemStack> piggyCopy = piggyBank == null ? List.of() : copyContainer(piggyBank);
        inventoryCopy.set(sourceSlot, ItemStack.EMPTY);

        boolean credited = addMoneyToFirstAvailableWallet(amount, extraCopy, piggyCopy, inventoryCopy);
        if (!credited) return false;

        commitInventory(inventory, inventoryCopy);
        commitExtraInventory(extraInventory, extraCopy);
        if (piggyBank != null) commitContainer(piggyBank, piggyCopy);
        return true;
    }

    private static boolean execute(Player player, long cost, boolean includePiggyBank, ItemStack result) {
        if (cost < 0) {
            throw new IllegalArgumentException("Money cost cannot be negative");
        }

        try {
            return executeChecked(player, cost, includePiggyBank, result);
        } catch (ArithmeticException ignored) {
            // 金额溢出意味着无法证明事务守恒，必须在提交任何快照前失败。
            return false;
        }
    }

    private static boolean executeChecked(Player player, long cost, boolean includePiggyBank, ItemStack result) {
        Inventory inventory = player.getInventory();
        ExtraInventory extraInventory = ExtraInventory.of(player);
        PlayerPiggyBankContainer piggyBank = includePiggyBank ? PlayerPiggyBankContainer.of(player) : null;

        List<ItemStack> inventoryCopy = copyStacks(inventory.items);
        List<ItemStack> extraCopy = copyStacks(extraInventory.getAllCoins());
        List<ItemStack> piggyCopy = piggyBank == null ? List.of() : copyContainer(piggyBank);

        long piggyMoney = sumCoins(piggyCopy);
        long carriedMoney = Math.addExact(sumCoins(inventoryCopy), sumCoins(extraCopy));
        long total = Math.addExact(piggyMoney, carriedMoney);
        if (total < cost) {
            return false;
        }

        // 总额校验包含存钱罐；实际付款先使用背包和钱币栏，不足部分才扣存钱罐。
        // 两个区域分别找零，交易不会在它们之间迁移余额。
        long carriedCost = Math.min(cost, carriedMoney);
        long piggyCost = cost - carriedCost;
        if (carriedCost > 0) {
            sumAndClearCoins(inventoryCopy);
            sumAndClearCoins(extraCopy);
        }
        if (piggyCost > 0) {
            boolean rewritten = rewriteMoney(piggyCopy, piggyMoney - piggyCost);
            if (!rewritten) return false;
        }

        if (!result.isEmpty() && !insertIntoInventory(result, inventoryCopy)) {
            return false;
        }

        if (carriedCost > 0) {
            Optional<List<ItemStack>> change = encodeCoins(carriedMoney - carriedCost,
                    inventoryCopy.size() + extraCopy.size());
            if (change.isEmpty()) {
                return false;
            }
            for (ItemStack stack : change.get()) {
                if (!placeIntoEmptySlot(stack, extraCopy) && !placeIntoEmptySlot(stack, inventoryCopy)) {
                    return false;
                }
            }
        }

        commitInventory(inventory, inventoryCopy);
        commitExtraInventory(extraInventory, extraCopy);
        if (piggyBank != null) {
            commitContainer(piggyBank, piggyCopy);
        }
        return true;
    }

    private static List<ItemStack> copyStacks(List<ItemStack> source) {
        List<ItemStack> copy = new ArrayList<>(source.size());
        for (ItemStack stack : source) {
            copy.add(stack.copy());
        }
        return copy;
    }

    private static List<ItemStack> copyContainer(Container container) {
        List<ItemStack> copy = new ArrayList<>(container.getContainerSize());
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            copy.add(container.getItem(slot).copy());
        }
        return copy;
    }

    private static long sumAndClearCoins(List<ItemStack> stacks) {
        long total = 0;
        for (int slot = 0; slot < stacks.size(); slot++) {
            ItemStack stack = stacks.get(slot);
            long value = CoinItem.valueOf(stack.getItem());
            if (stack.isEmpty() || !stack.is(ModTags.Items.COINS) || value == 0) {
                continue;
            }
            total = Math.addExact(total, Math.multiplyExact(value, stack.getCount()));
            stacks.set(slot, ItemStack.EMPTY);
        }
        return total;
    }

    private static long sumCoins(List<ItemStack> stacks) {
        long total = 0;
        for (ItemStack stack : stacks) {
            long value = CoinItem.valueOf(stack.getItem());
            if (!stack.isEmpty() && stack.is(ModTags.Items.COINS) && value > 0) {
                total = Math.addExact(total, Math.multiplyExact(value, stack.getCount()));
            }
        }
        return total;
    }

    /// 在单个存储区域内重新编码余额，区域中的非钱币物品保持原位。
    private static boolean rewriteMoney(List<ItemStack> slots, long amount) {
        if (slots.isEmpty()) return amount == 0;
        sumAndClearCoins(slots);
        Optional<List<ItemStack>> encoded = encodeCoins(amount, slots.size());
        if (encoded.isEmpty()) return false;
        for (ItemStack stack : encoded.get()) {
            if (!placeIntoEmptySlot(stack, slots)) return false;
        }
        return true;
    }

    /// 售出所得只写入一个能够容纳整笔金额的钱包，不重排其他钱包已有的钱币。
    private static boolean addMoneyToFirstAvailableWallet(long amount, List<ItemStack> extraInventory, List<ItemStack> piggyBank, List<ItemStack> inventory) {
        return addMoneyToWallet(amount, extraInventory) || addMoneyToWallet(amount, piggyBank) || addMoneyToWallet(amount, inventory);
    }

    private static boolean addMoneyToWallet(long amount, List<ItemStack> wallet) {
        if (wallet.isEmpty()) return false;
        List<ItemStack> candidate = copyStacks(wallet);
        long current = sumCoins(candidate);
        boolean rewritten = rewriteMoney(candidate, Math.addExact(current, amount));
        if (!rewritten) return false;
        for (int slot = 0; slot < wallet.size(); slot++) {
            wallet.set(slot, candidate.get(slot));
        }
        return true;
    }

    /// 在明确的槽位预算内拆分钱币。
    private static Optional<List<ItemStack>> encodeCoins(long amount, int maxStacks) {
        if (amount < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative");
        }
        if (maxStacks < 0) {
            throw new IllegalArgumentException("Money stack budget cannot be negative");
        }
        List<ItemStack> result = new ArrayList<>(4);
        amount = appendCoins(result, ModItems.PLATINUM_COIN.get(), amount, CoinItem.PLATINUM_VALUE, maxStacks);
        if (amount < 0) return Optional.empty();
        amount = appendCoins(result, ModItems.GOLD_COIN.get(), amount, CoinItem.GOLD_VALUE, maxStacks);
        if (amount < 0) return Optional.empty();
        amount = appendCoins(result, ModItems.SILVER_COIN.get(), amount, CoinItem.SILVER_VALUE, maxStacks);
        if (amount < 0) return Optional.empty();
        amount = appendCoins(result, ModItems.COPPER_COIN.get(), amount, CoinItem.COPPER_VALUE, maxStacks);
        return amount < 0 ? Optional.empty() : Optional.of(result);
    }

    private static long appendCoins(List<ItemStack> output, Item coin, long amount, long value, int maxStacks) {
        long count = amount / value;
        long remaining = amount % value;
        int maxStackSize = coin.getMaxStackSize();
        long requiredStacks = count == 0 ? 0 : ((count - 1L) / maxStackSize) + 1L;
        if (requiredStacks > maxStacks - output.size()) {
            return -1L;
        }
        while (count > 0) {
            int stackSize = (int) Math.min(count, maxStackSize);
            output.add(new ItemStack(coin, stackSize));
            count -= stackSize;
        }
        return remaining;
    }

    private static boolean placeIntoEmptySlot(ItemStack stack, List<ItemStack> slots) {
        if (slots.isEmpty()) {
            return false;
        }
        for (int slot = 0; slot < slots.size(); slot++) {
            if (slots.get(slot).isEmpty()) {
                slots.set(slot, stack.copy());
                return true;
            }
        }
        return false;
    }

    private static boolean insertIntoInventory(ItemStack source, List<ItemStack> inventory) {
        ItemStack remaining = source.copy();
        for (int slot = 0; slot < inventory.size() && !remaining.isEmpty(); slot++) {
            ItemStack existing = inventory.get(slot);
            if (existing.isEmpty() || !ItemStack.isSameItemSameTags(existing, remaining)) {
                continue;
            }
            int transferable = Math.min(remaining.getCount(), existing.getMaxStackSize() - existing.getCount());
            if (transferable <= 0) {
                continue;
            }
            existing.grow(transferable);
            remaining.shrink(transferable);
        }
        for (int slot = 0; slot < inventory.size() && !remaining.isEmpty(); slot++) {
            if (!inventory.get(slot).isEmpty()) {
                continue;
            }
            int transferable = Math.min(remaining.getCount(), remaining.getMaxStackSize());
            ItemStack inserted = remaining.copy();
            inserted.setCount(transferable);
            inventory.set(slot, inserted);
            remaining.shrink(transferable);
        }
        return remaining.isEmpty();
    }

    private static void commitInventory(Inventory inventory, List<ItemStack> stacks) {
        for (int slot = 0; slot < stacks.size(); slot++) {
            inventory.items.set(slot, stacks.get(slot));
        }
        inventory.setChanged();
    }

    private static void commitExtraInventory(ExtraInventory inventory, List<ItemStack> stacks) {
        if (stacks.size() != SIZE_COINS) {
            throw new IllegalStateException("Coin inventory snapshot has an invalid size");
        }
        for (int slot = 0; slot < SIZE_COINS; slot++) {
            inventory.setCoins(slot, stacks.get(slot));
        }
    }

    private static void commitContainer(Container container, List<ItemStack> stacks) {
        if (container.getContainerSize() != stacks.size()) {
            throw new IllegalStateException("Money container changed size during transaction");
        }
        for (int slot = 0; slot < stacks.size(); slot++) {
            container.setItem(slot, stacks.get(slot));
        }
        container.setChanged();
    }
}
