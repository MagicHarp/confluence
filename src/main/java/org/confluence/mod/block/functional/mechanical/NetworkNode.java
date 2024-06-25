package org.confluence.mod.block.functional.mechanical;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;

public class NetworkNode {
    private final int id;
    private final Int2ObjectMap<Network> networks;

    public int getId() {
        return id;
    }

    public Network getNetwork(int color) {
        return networks.get(color);
    }

    public Int2ObjectMap<Network> getNetworks() {
        return networks;
    }

    public void clearNetworks() {
        networks.clear();
    }

    public void removeNetwork(int color) {
        networks.remove(color);
    }

    public void addNetwork(Network network) {
        networks.put(network.getColor(), network);
    }

    private final AbstractMechanicalBlock.Entity blockEntity;

    public NetworkNode(int id, AbstractMechanicalBlock.Entity blockEntity) {
        this.id = id;
        this.blockEntity = blockEntity;
        this.networks = new Int2ObjectOpenHashMap<>();
    }

    public BlockPos getPos() {
        return blockEntity.getBlockPos();
    }

    public AbstractMechanicalBlock.Entity getBlockEntity() {
        return blockEntity;
    }
}