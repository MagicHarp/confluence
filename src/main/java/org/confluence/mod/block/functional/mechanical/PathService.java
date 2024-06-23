package org.confluence.mod.block.functional.mechanical;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.confluence.mod.Confluence;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class PathService {
    public static final PathService INSTANCE = new PathService();
    private Queue<NetworkNode> queue;

    public void onServerStart() {
        this.queue = new ArrayDeque<>();
    }

    public void onServerStop() {
        this.queue = null;
    }

    // 方块加载时,创捷网络结点
    public void onBlockEntityLoad(BaseMechanicalBlock.Entity blockEntity) {
        NetworkService.INSTANCE.createNetworkNode(blockEntity);
    }

    // 方块卸载时,删除网络结点和所属网络
    // todo 这里出问题了,需要修复
    public void onBlockEntityUnload(BaseMechanicalBlock.Entity blockEntity) {
        if (blockEntity.getNetworkNode() == null) return;
        for (Network network : blockEntity.getNetworkNode().getNetworks().values()) {
            NetworkService.INSTANCE.removeNetworkNode(blockEntity.getNetworkNode());
            if (network != null) {
                // 网络已损坏,需要将所拥有的全部结点重新处理,放入处理队列中
                queue.addAll(network.getNodes());
                NetworkService.INSTANCE.removeNetwork(network);
            }
        }
    }

    public void pathFindingTick() {
        if (queue == null) return;
        boolean flag = false;
        while (!queue.isEmpty()) {
            flag = true;
            NetworkNode cur = queue.remove();
            Level world = cur.getBlockEntity().getLevel();
            // 如果未加载则直接跳过
            if (world == null || !world.isLoaded(cur.getPos())) continue;
            if (cur.getNetworks().isEmpty()) {
                // 如果没有所属网络则创建一个
                Network network = NetworkService.INSTANCE.createNetwork(0x000000);
                NetworkService.INSTANCE.addNodeToNetwork(cur, network);
            }
            BaseMechanicalBlock.Entity entity = cur.getBlockEntity();
            for (Int2ObjectMap.Entry<Network> entry : cur.getNetworks().int2ObjectEntrySet()) {
                int color = entry.getIntKey();
                Network curNetwork = entry.getValue();
                Iterator<Int2ObjectMap.Entry<BlockPos>> iterator = entity.connectedPoses.int2ObjectEntrySet().iterator();
                while (iterator.hasNext()) {
                    Int2ObjectMap.Entry<BlockPos> entry1 = iterator.next();
                    BlockPos pos = entry1.getValue();
                    if (!world.isLoaded(pos)) continue;
                    if (world.getBlockEntity(pos) instanceof BaseMechanicalBlock.Entity blockEntity) {
                        NetworkNode next = blockEntity.getNetworkNode();
                        // 匹配相同的颜色
                        if (entry1.getIntKey() != color) continue;
                        Network nextNetwork = next.getNetwork(color);
                        if (nextNetwork == null) {
                            // 将结点加入到网络中
                            NetworkService.INSTANCE.addNodeToNetwork(next, curNetwork);
                            queue.add(next);
                        } else if (curNetwork != nextNetwork) {
                            // 合并网络
                            NetworkService.INSTANCE.mergeNetwork(curNetwork, nextNetwork);
                            curNetwork = cur.getNetwork(color);
                        }
                    } else {
                        // 移除不存在的坐标
                        iterator.remove();
                    }
                }
            }
        }
        if (flag) {
            Confluence.LOGGER.debug("Path finding finish");
            for (Network network : NetworkService.INSTANCE.getNetworks()) {
                Confluence.LOGGER.debug("Network#{}:", network.getColor());
                for (NetworkNode node : network.getNodes()) {
                    Confluence.LOGGER.debug("Node#{}: {}", node.getId(), node.getPos().toShortString());
                }
            }
        }
    }
}