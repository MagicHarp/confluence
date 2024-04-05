package org.confluence.mod.client;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.GunShootingHandler;
import org.confluence.mod.client.handler.InformationHandler;
import org.confluence.mod.client.handler.PlayerJumpHandler;
import org.confluence.mod.item.ModRarity;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClient {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null || event.phase == TickEvent.Phase.START) return;
        PlayerJumpHandler.handle(localPlayer);
        InformationHandler.update(localPlayer);

        ModRarity.Animate.doUpdateRainbowColor();
        ModRarity.Animate.doUpdateMasterColor();
    }

    @SubscribeEvent
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        Item item = event.getItemStack().getItem();
        if (item instanceof ModRarity.Expert expert) {
            event.getTooltipElements().set(0, Either.left(expert.getComponent()
                .withStyle(style -> style.withColor(ModRarity.Animate.getRainbowColor()))
            ));
        } else if (item instanceof ModRarity.Master master) {
            event.getTooltipElements().set(0, Either.left(master.getComponent()
                .withStyle(style -> style.withColor(ModRarity.Animate.getMasterColor()))
            ));
        }
    }

    @SubscribeEvent
    public static void click(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        GunShootingHandler.handle(event, localPlayer);
        if (ClientPacketHandler.shouldContinueSwing()) event.setSwingHand(true);
    }
}
