package org.confluence.mod.entity.boss;

import java.util.function.Consumer;

public class BossSkill {

    public String skillID;
    public String skill;
    public int timeContinue;
    public int timeTrigger;

    public Consumer<TerraBossBase> stateInit;
    public Consumer<TerraBossBase> stateTick;
    public Consumer<TerraBossBase> stateOver;

    /**
     *
     * @param skillID 强制状态跳转标识  已废弃
     * @param skill 动画名称
     * @param timeContinue 状态持续时间
     * @param timeTrigger 逻辑触发时间
     */
    public BossSkill(String skillID, String skill, int timeContinue, int timeTrigger){
        this.skill = skill;
        this.timeContinue = timeContinue;
        this.timeTrigger = timeTrigger;
        this.skillID = skillID;
    }

    public BossSkill(String skillID, String animName, int timeContinue, int timeTrigger,
                     Consumer<TerraBossBase> stateInit,
                     Consumer<TerraBossBase> stateTick,
                     Consumer<TerraBossBase> stateOver
    ){
        this.skill = animName;
        this.timeContinue = timeContinue;
        this.timeTrigger = timeTrigger;
        this.skillID = skillID;
        this.stateInit = stateInit;
        this.stateTick = stateTick;
        this.stateOver = stateOver;
    }

    public void addStateReset(Consumer<TerraBossBase> stateTick){
        this.stateTick = stateTick;
    };
    public void addStateInit(Consumer<TerraBossBase> stateInit){
        this.stateInit = stateInit;
    };
    public void addStateOver(Consumer<TerraBossBase> stateOver){
        this.stateOver = stateOver;
    };
}
