package org.confluence.mod.client;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.PlayerInputHandler;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.common.RainbowRarity;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClient {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        LocalPlayer localPlayer = MINECRAFT.player;
        if (localPlayer == null || event.phase == TickEvent.Phase.START) return;
        PlayerInputHandler.handleJump(localPlayer);

        ModRarity.Animate.DoUpdate_AnimateDiscoRGB();
        ModRarity.Animate.DoUpdate_AnimateCursorColors();
    }

    @SubscribeEvent
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof RainbowRarity item) {
            event.getTooltipElements().set(0, Either.left(item.getComponent()
                .withStyle(style -> style.withColor(ModRarity.Animate.getDiscoColor()))
            ));
        }
    }
}
