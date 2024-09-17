package org.confluence.mod.entity.boss;

import java.util.function.Consumer;
import java.util.function.Function;

public class bossSkill {

    public String skillID;
    public String skill;
    public int timeContinue;
    public int timeTrigger;

    public Consumer<terraBossBase> stateInit;
    public Consumer<terraBossBase> stateTick;
    public Consumer<terraBossBase> stateOver;

    /**
     *
     * @param skillID 强制状态跳转标识
     * @param skill 动画名称
     * @param timeContinue 状态持续时间
     * @param timeTrigger 逻辑触发时间
     */
    public bossSkill(String skillID,String skill,int timeContinue,int timeTrigger){
        this.skill = skill;
        this.timeContinue = timeContinue;
        this.timeTrigger = timeTrigger;
        this.skillID = skillID;
    }

    public bossSkill(String skillID,String animName,int timeContinue,int timeTrigger,
                     Consumer<terraBossBase> stateInit,
                     Consumer<terraBossBase> stateTick,
                     Consumer<terraBossBase> stateOver
    ){
        this.skill = animName;
        this.timeContinue = timeContinue;
        this.timeTrigger = timeTrigger;
        this.skillID = skillID;
        this.stateInit = stateInit;
        this.stateTick = stateTick;
        this.stateOver = stateOver;
    }

    public void addStateReset(Consumer<terraBossBase> stateTick){
        this.stateTick = stateTick;
    };
    public void addStateInit(Consumer<terraBossBase> stateInit){
        this.stateInit = stateInit;
    };
    public void addStateOver(Consumer<terraBossBase> stateOver){
        this.stateOver = stateOver;
    };
}
