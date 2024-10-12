package org.confluence.mod.event;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.client.renderer.gui.ArrowInBowOverlay;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.bow.Bows;
import org.confluence.mod.item.bow.ShortBowItem;
import org.confluence.mod.util.DelayedTaskExecutor;
import org.joml.Matrix4f;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID,value = Dist.CLIENT)
public class GuiRenderEvent {

    @SubscribeEvent
    public static void RenderGuiEvent(RenderHandEvent event) {
        ArrowInBowOverlay.render(event);



    }
}
