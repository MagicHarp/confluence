package org.confluence.mod.item.curio.movement;

import net.minecraft.server.level.ServerPlayer;

public interface IOneTimeJump extends IMultiJump {
    int getJumpTicks();

    static void sendMsg(ServerPlayer serverPlayer) {
        IMultiJump.sendMsg(serverPlayer);
    }
}
