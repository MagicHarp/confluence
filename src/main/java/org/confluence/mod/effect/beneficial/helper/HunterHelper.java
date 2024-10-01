package org.confluence.mod.effect.beneficial.helper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
public class HunterHelper {

    public boolean alwaysShow = false;
    public boolean alwaysShowNeutral = false;
    public Color defaultColor = Color.white;//其他生物颜色
    public Color neutralColor = Color.blue;//中立生物颜色
    public Color angerColor = Color.red;//todo 中立生物愤怒颜色

    public static HunterHelper getSingleton(){
        if(hunterHelper==null){
            hunterHelper = new HunterHelper();
            //上面覆盖下面


            hunterHelper.colorMap.put(Monster.class, new Tuple(Color.orange,true) );//怪物
            hunterHelper.colorMap.put(Animal.class, new Tuple(Color.green,false));//友好生物
        }
        return hunterHelper;
    }



    private static HunterHelper hunterHelper;
    public  Map<Class<? extends Entity>,Tuple> colorMap = new LinkedHashMap<>();//优先颜色类型表
    public record Tuple(Color color, boolean alwaysShow){ }
}
