package org.confluence.mod.block.functional.mechanical;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;

public class NetworkNode {
    private final int id;
    private final Int2ObjectMap<Network> networks;
    private final AbstractMechanicalBlock.Entity blockEntity;
    boolean inQueue; // 仅用于寻路服务
    boolean cachedSignal; // 仅用于寻路服务

    public NetworkNode(int id, AbstractMechanicalBlock.Entity blockEntity) {
        this.id = id;
        this.blockEntity = blockEntity;
        this.networks = new Int2ObjectOpenHashMap<>();
        this.inQueue = false;
        this.cachedSignal = false;
    }

    public int getId() {
        return id;
    }

    public Network getNetwork(int color) {
        return networks.get(color);
    }

    public Network getOrCreateNetwork(int color) {
        Network network = networks.get(color);
        if (network == null) {
            Network network1 = NetworkService.INSTANCE.createNetwork(color);
            network1.setSignal(cachedSignal);
            NetworkService.INSTANCE.addNodeToNetwork(this, network1);
            return network1;
        } else {
            return network;
        }
    }

    public Int2ObjectMap<Network> getNetworks() {
        return networks;
    }

    public void removeNetwork(int color) {
        networks.remove(color);
    }

    public void addNetwork(Network network) {
        networks.put(network.getColor(), network);
    }

    public BlockPos getPos() {
        return blockEntity.getBlockPos();
    }

    public AbstractMechanicalBlock.Entity getBlockEntity() {
        return blockEntity;
    }
}