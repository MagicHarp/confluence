package org.confluence.mod.block.functional.network;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class IntAllocator {
    private final IntOpenHashSet table;

    public IntAllocator() {
        this.table = new IntOpenHashSet();
    }

    public int insert() {
        int size = table.size();
        for (int i = 0; i < size; i++) {
            if (!table.contains(i)) {
                table.add(i);
                return i;
            }
        }
        table.add(size);
        return size;
    }

    public void remove(int id) {
        table.remove(id);
    }

    public void clear() {
        table.clear();
    }
}
