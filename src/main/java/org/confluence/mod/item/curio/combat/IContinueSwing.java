package org.confluence.mod.item.curio.combat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.ContinuingSwingHandPacketS2C;
import org.confluence.mod.util.CuriosUtils;

public interface IContinueSwing {
    static void sendMsg(ServerPlayer serverPlayer) {
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new ContinuingSwingHandPacketS2C(CuriosUtils.hasCurio(serverPlayer, IContinueSwing.class))
        );
    }
}
