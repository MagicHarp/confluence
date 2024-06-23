package org.confluence.mod.block.functional.mechanical;

import org.confluence.mod.Confluence;

import java.util.HashSet;
import java.util.Set;

public class NetworkService {
    private IntAllocator nodeID;
    private Set<Network> networks;
    public static final NetworkService INSTANCE = new NetworkService();

    public void onServerStart() {
        this.nodeID = new IntAllocator();
        this.networks = new HashSet<>();
    }

    public void onServerStop() {
        nodeID.clear();
        networks.clear();
    }

    public Network createNetwork(int color) {
        Network network = new Network(color);
        networks.add(network);
        Confluence.LOGGER.info("Create network#{}", network.getColor());
        return network;
    }

    public void createNetworkNode(BaseMechanicalBlock.Entity blockEntity) {
        NetworkNode node = new NetworkNode(nodeID.insert(), blockEntity);
        blockEntity.setNetworkNode(node);
        Confluence.LOGGER.debug("Create network node#{}", node.getId());
    }

    public void removeNetwork(Network network) {
        Confluence.LOGGER.debug("Remove network#{}", network.getColor());
        networks.remove(network);
        network.destroy();
    }

    public void removeNetworkNode(NetworkNode node) {
        Confluence.LOGGER.debug("Remove network node#{}", node.getId());
        for (Network network : node.getNetworks().values()) {
            removeNodeInNetwork(node, network);
        }
        nodeID.remove(node.getId());
    }

    public void addNodeToNetwork(NetworkNode node, Network network) {
        network.addNode(node);
        node.addNetwork(network);
    }

    public void removeNodeInNetwork(NetworkNode node, Network network) {
        network.removeNode(node);
        node.removeNetwork(network.getColor());
    }

    public Set<Network> getNetworks() {
        return networks;
    }

    public void mergeNetwork(Network n1, Network n2) {
        // n3为合并后的网络
        Network n3 = Network.merge(n1, n2);
        // 将被合并的网络删除
        if (n3 == n1) removeNetwork(n2);
        else removeNetwork(n1);
    }
}
