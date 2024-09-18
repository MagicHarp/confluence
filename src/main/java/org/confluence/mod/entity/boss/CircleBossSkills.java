package org.confluence.mod.entity.boss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleBossSkills {
    public TerraBossBase owner;
    protected final List<BossSkill> bossSkills = new ArrayList<>();
    protected Map<String,Integer> stateIndexMap = new HashMap<>();

    public int tick = 0;
    public int index = 0;
    public boolean ifStateInit = false;

    public CircleBossSkills(TerraBossBase owner){ this.owner = owner;}
    public int count(){return bossSkills.size();};

    public boolean pushSkill(BossSkill skill){
        bossSkills.add(skill);
        stateIndexMap.put(skill.skillID,bossSkills.size()-1);
        if(bossSkills.size()==1) tick = 0;
        return true;
    }


    public void tick(){
        this.tick++;

        if(bossSkills.get(index).stateTick !=null) {
            bossSkills.get(index).stateTick.accept(owner);
        }
        if(bossSkills.isEmpty())return;
        if(bossSkills.get(index).timeContinue < tick) forceEnd();
    }


    /** 强制结束当前状态 **/
    public void forceEnd(){
        tick = 0;
        int lastIndex = index;
        index = (index +1) % bossSkills.size();

        //状态结束
        if(bossSkills.get(lastIndex).stateOver!=null) bossSkills.get(lastIndex).stateOver.accept(owner);

        //初次进入状态
        if(bossSkills.get(index).stateInit!=null) bossSkills.get(index).stateInit.accept(owner);
    }
    /** 强制跳转状态 **/
    public void forceStartIndex(int index){
        tick = 0;
        this.index = index;
    }


    /** tick == triggerTime **/
    public boolean canTrigger(){
        if(bossSkills.isEmpty()) return false;
        return bossSkills.get(index).timeTrigger == this.tick;
    }
    /** tick > triggerTime **/
    public boolean canContinue(){
        if(bossSkills.isEmpty()) return false;
        return bossSkills.get(index).timeTrigger < this.tick;
    }
    public String getCurSkill(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index).skill;
        return "";
    }
    public int getCurAnimFullTick(){
        if(!bossSkills.isEmpty())
            return bossSkills.get(index).timeContinue;
        return -1;
    }



}
