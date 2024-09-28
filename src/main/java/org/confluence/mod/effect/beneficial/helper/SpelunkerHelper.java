package org.confluence.mod.effect.beneficial.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.natural.Ores;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SpelunkerHelper {
/** 调参表 **/
    public int refreshTick = 100;//客户端渲染刷新间隔
    public int range = 30;//球形侦测范围
    public float maxAlpha = 0.4f;//边框最大alpha(0 - 1)



    public static SpelunkerHelper blockGen;
    public Map<Block, Color> targets = new HashMap<>();
    public Map<BlockPos,BlockPos> centerCache = new HashMap<>();
    public Map<Block, ArrayList<BlockPos>> centers = new LinkedHashMap<>();
    public Map<Block, java.util.List<BlockPos>> blockMap = new HashMap<>();
    Boolean INIT = false;
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

            //锡矿
            targets.put(Ores.TIN_ORE.get(), Color.LIGHT_GRAY);
            targets.put(Ores.DEEPSLATE_TIN_ORE.get(), Color.LIGHT_GRAY);

            //铅矿
            targets.put(Ores.LEAD_ORE.get(), Color.PINK);
            targets.put(Ores.DEEPSLATE_LEAD_ORE.get(), Color.PINK);

            //铂金矿
            targets.put(Ores.PLATINUM_ORE.get(), Color.ORANGE);
            targets.put(Ores.DEEPSLATE_PLATINUM_ORE.get(), Color.ORANGE);

            //生命水晶
            targets.put(ModBlocks.LIFE_CRYSTAL_BLOCK.get(), Color.RED);
            //箱子
            targets.put(ModBlocks.BASE_CHEST_BLOCK.get(), Color.ORANGE);



            //青金石
            targets.put(Blocks.LAPIS_ORE, Color.blue);
            targets.put(Blocks.DEEPSLATE_LAPIS_ORE, Color.blue);

            //红石
            targets.put(Blocks.REDSTONE_ORE, Color.red);
            targets.put(Blocks.DEEPSLATE_REDSTONE_ORE, Color.red);


            //example
           // targets.put(Blocks.STONE, new Color(255,255,255));



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
                    if (targets.containsKey(block) && !centerCache.containsKey(pos)) {
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
