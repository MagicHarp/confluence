package org.confluence.mod.effect.beneficial.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.Ores;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpelunkerHelper {

    public static Map<Block, Color> targets = new HashMap<>();


    Boolean INIT = false;
    public int range = 30;
    private Map<Block, java.util.List<BlockPos>> blockMap = new HashMap<>();
    private Player player;

    public SpelunkerHelper(Player player) {
        this.player = player;
        genBlocks();

        if(!INIT){
            //远古残骸
            targets.put(Blocks.ANCIENT_DEBRIS,Color.MAGENTA);

            //钻石矿
            targets.put(Blocks.DIAMOND_ORE, Color.CYAN);
            targets.put(Blocks.DEEPSLATE_DIAMOND_ORE, Color.CYAN);

            //绿宝石矿
            targets.put(Blocks.EMERALD_ORE,Color.green);
            targets.put(Blocks.DEEPSLATE_EMERALD_ORE,Color.green);

            //铁矿
            targets.put(Blocks.IRON_ORE, Color.PINK);
            targets.put(Blocks.DEEPSLATE_IRON_ORE, Color.PINK);

            //金矿
            targets.put(Blocks.GOLD_ORE, Color.ORANGE);
            targets.put(Blocks.DEEPSLATE_GOLD_ORE, Color.ORANGE);

            //煤矿
            targets.put(Blocks.COAL_ORE, Color.BLACK);
            targets.put(Blocks.DEEPSLATE_COAL_ORE, Color.BLACK);

            //铜矿
            targets.put(Blocks.COPPER_ORE, Color.LIGHT_GRAY);
            targets.put(Blocks.DEEPSLATE_COPPER_ORE, Color.LIGHT_GRAY);


            //青金石
            targets.put(Blocks.LAPIS_ORE, Color.blue);
            targets.put(Blocks.DEEPSLATE_LAPIS_ORE, Color.blue);

            //红石
            targets.put(Blocks.REDSTONE_ORE, Color.red);
            targets.put(Blocks.DEEPSLATE_REDSTONE_ORE, Color.red);


            //example
           // targets.put(Blocks.STONE, new Color(255,255,255));
// todo 泰拉矿石检测
            INIT = true;
        }
    }

    public void genBlocks() {
        for(var n : blockMap.entrySet()){
            n.getValue().clear();
        }
        blockMap.clear();
        Level level = player.level();
        BlockPos center = player.blockPosition();
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j < range; j++) {
                for (int k = -range; k < range; k++) {
                    BlockPos pos = center.offset(i, j, k);
                    Block block = level.getBlockState(pos).getBlock();
                    if (targets.containsKey(block)) {
                        var list = blockMap.computeIfAbsent(block, k1 -> new ArrayList<>());
                        list.add(pos);
                    }
                }
            }
        }
    }

    public Map<Block, List<BlockPos>> getBlockMap() {
        return blockMap;
    }

}
