package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.confluence.mod.item.curio.informational.IWatch;
import org.confluence.mod.util.CuriosUtils;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class InformationHandler {
    private static final ArrayList<String> information = new ArrayList<>();

    public static void update(LocalPlayer localPlayer) {
        information.clear();
        CuriosUtils.findCurio(localPlayer, IWatch.class)
            .ifPresent(iWatch -> information.add(iWatch.wrapTime(localPlayer.level().dayTime())));
    }

    public static ArrayList<String> getInformation() {
        return information;
    }
}
