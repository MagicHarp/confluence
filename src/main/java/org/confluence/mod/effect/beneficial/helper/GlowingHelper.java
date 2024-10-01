package org.confluence.mod.effect.beneficial.helper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.block.TripWireHookBlock;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.projectile.BoulderEntity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class GlowingHelper {


/** 狩猎药水发光 **/
    public boolean alwaysShow = false;


    public Color defaultColor = Color.white;//其他生物颜色

    public Color neutralColor = Color.blue;//中立生物颜色
    public boolean alwaysShowNeutral = false;//中立生物持续显示
    public Color angerColor = Color.red;//todo 中立生物愤怒颜色

    public Color enemyColor = Color.orange;//敌人颜色

    public static GlowingHelper getHunterHelper(){
        if(hunterHelper==null){ //上面覆盖下面
            hunterHelper = new GlowingHelper();
            //狩猎药水实体   表优先于中立生物和敌人

            hunterHelper.addHunter(Slime.class,Color.MAGENTA,true);//EXAMPLE:史莱姆持续显示紫色，覆盖enemy的orange
            hunterHelper.addHunter(Animal.class,Color.green,false);//动物

            //危险感知实体
            hunterHelper.addDanger(BoulderEntity.class,Color.red,true);//巨石

        }
        return hunterHelper;
    }





    private static GlowingHelper hunterHelper;
    public  Map<Class<? extends Entity>,Tuple> colorMap = new LinkedHashMap<>();//优先颜色类型表

    public record Tuple(Color color, boolean alwaysShow){ }

    public List<Class<? extends Entity>> hunterCatalog = new ArrayList<>();
    public List<Class<? extends Entity>> dangerCatalog = new ArrayList<>();
    public void addHunter(Class<?extends Entity> clazz, Color color,boolean alwaysShow){
        hunterHelper.hunterCatalog.add(clazz);
        hunterHelper.colorMap.put(clazz, new Tuple(color,alwaysShow) );
    }
    public void addDanger(Class<?extends Entity> clazz, Color color,boolean alwaysShow){
        hunterHelper.dangerCatalog.add(clazz);
        hunterHelper.colorMap.put(clazz, new Tuple(color,alwaysShow) );
    }


}
