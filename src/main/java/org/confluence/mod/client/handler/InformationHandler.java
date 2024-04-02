package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.item.curio.informational.IWatch;
import org.confluence.mod.network.s2c.EnemyInfoPacketS2C;
import org.confluence.mod.util.CuriosUtils;

import java.util.ArrayList;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class InformationHandler {
    private static final ArrayList<String> information = new ArrayList<>();
    private static String enemyInfo = "";

    public static void update(LocalPlayer localPlayer) {
        information.clear();
        CuriosUtils.findCurio(localPlayer, IWatch.class)
            .ifPresent(iWatch -> information.add(iWatch.wrapTime(localPlayer.level().dayTime())));
        if (!enemyInfo.isEmpty()) information.add(enemyInfo);
    }

    public static void handleEnemyInfo(EnemyInfoPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> enemyInfo = packet.amount() < 0 ? "" : "Enemy: " + packet.amount());
        context.setPacketHandled(true);
    }

    public static ArrayList<String> getInformation() {
        return information;
    }
}
