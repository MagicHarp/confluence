package org.confluence.mod.entity.boss;

import java.util.*;
import java.util.function.Consumer;

public class FSMBossSkills extends circleBossSkills{


    public FSMBossSkills(terraBossBase owner) {
        super(owner);
    }

    public boolean pushSkill(String skillID,String skill,int timeContinue,int timeTrigger,
                             Consumer<terraBossBase> stateInit,
                             Consumer<terraBossBase> stateTick,
                             Consumer<terraBossBase> stateOver
                             ){
        bossSkill skill1 = new bossSkill(skillID,skill,timeContinue,timeTrigger,stateInit,stateTick,stateOver);
        bossSkills.add(skill1);
        stateIndexMap.put(skillID,bossSkills.size()-1);
        if(bossSkills.size()==1) tick = 0;
        return true;
    }


}
