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
    public int textRange = 30;//球形显示文本范围
    public float maxAlpha = 0.4f;//边框最大alpha(0 - 1)



    public static SpelunkerHelper blockGen;
    public Map<Block, Tuple> targets = new HashMap<>();
    public Map<BlockPos,BlockPos> centerCache = new HashMap<>();
    public Map<BlockPos,Block> centerCacheFrame = new HashMap<>();//当前帧渲染的cache
    public Map<Block, ArrayList<BlockPos>> centers = new LinkedHashMap<>();
    public Map<Block, java.util.List<BlockPos>> blockMap = new HashMap<>();
    private Boolean INIT = false;
    private Player player;



    public SpelunkerHelper(Player player) {
        this.player = player;
        genBlocks();

        if(!INIT){

            //钻石矿
            targets.put(Blocks.DIAMOND_ORE, new Tuple(Color.CYAN,true) );
            targets.put(Blocks.DEEPSLATE_DIAMOND_ORE, new Tuple(Color.CYAN,true) );
            //远古残骸
            targets.put(Blocks.ANCIENT_DEBRIS,new Tuple(Color.MAGENTA,true) );//这个还必须放这个位置

            //绿宝石矿
            targets.put(Blocks.EMERALD_ORE,new Tuple(Color.green,true) );
            targets.put(Blocks.DEEPSLATE_EMERALD_ORE,new Tuple(Color.green,true) );

            //铁矿
            targets.put(Blocks.IRON_ORE, new Tuple(Color.PINK,true) );
            targets.put(Blocks.DEEPSLATE_IRON_ORE, new Tuple(Color.PINK,true) );

            //金矿
            targets.put(Blocks.GOLD_ORE, new Tuple(Color.ORANGE,true) );
            targets.put(Blocks.DEEPSLATE_GOLD_ORE, new Tuple(Color.ORANGE,true) );

            //煤矿
            targets.put(Blocks.COAL_ORE, new Tuple(Color.BLACK,false) );
            targets.put(Blocks.DEEPSLATE_COAL_ORE, new Tuple(Color.BLACK,false) );

            //铜矿
            targets.put(Blocks.COPPER_ORE, new Tuple(Color.LIGHT_GRAY,false) );
            targets.put(Blocks.DEEPSLATE_COPPER_ORE, new Tuple(Color.LIGHT_GRAY,false) );

            //锡矿
            targets.put(Ores.TIN_ORE.get(), new Tuple(Color.LIGHT_GRAY,false) );
            targets.put(Ores.DEEPSLATE_TIN_ORE.get(), new Tuple(Color.LIGHT_GRAY,false) );

            //铅矿
            targets.put(Ores.LEAD_ORE.get(), new Tuple(Color.PINK,false) );
            targets.put(Ores.DEEPSLATE_LEAD_ORE.get(), new Tuple(Color.PINK,false) );

            //铂金矿
            targets.put(Ores.PLATINUM_ORE.get(), new Tuple(Color.ORANGE,true) );
            targets.put(Ores.DEEPSLATE_PLATINUM_ORE.get(), new Tuple(Color.ORANGE,true) );

            //生命水晶
            targets.put(ModBlocks.LIFE_CRYSTAL_BLOCK.get(), new Tuple(Color.RED,true) );
            //箱子
            targets.put(ModBlocks.BASE_CHEST_BLOCK.get(), new Tuple(Color.ORANGE,true) );



            //青金石
            targets.put(Blocks.LAPIS_ORE, new Tuple(Color.blue,false) );
            targets.put(Blocks.DEEPSLATE_LAPIS_ORE, new Tuple(Color.blue,false) );

            //红石
            targets.put(Blocks.REDSTONE_ORE, new Tuple(Color.red,false) );
            targets.put(Blocks.DEEPSLATE_REDSTONE_ORE, new Tuple(Color.red,false) );


            //example
           // targets.put(Blocks.STONE, new Color(255,255,255));



            INIT = true;
        }
    }



    public record Tuple(Color color,Boolean showText) { }

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
                    if (targets.containsKey(block) /*&&//有目标且
                            (!centerCache.containsKey(pos) ||//未已缓存或
                                    centerCache.containsKey(pos) && player.level().getBlockState(pos).is(Blocks.AIR))*/) {//已缓存但为空

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
