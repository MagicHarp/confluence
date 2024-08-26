package org.confluence.mod.entity.boss;

import net.minecraft.world.entity.player.Player;

import java.util.List;

/** Finite State Machine */
public interface IBossFSM {
    interface State<E extends IBossFSM> {
        /** 于进入此state时触发 */
        default void enter(E boss) {};
        /** 于离开此state时触发 */
        default void leave(E boss) {};
        /** 每刻触发
         * @return 下一刻的State（可为自身）
         * */
        void tick(E boss);
    }

    /** 转到新的state */
    void toState(State newState);

    void AI();

    List<Player> getNearbyPlayers(double radius);
}
