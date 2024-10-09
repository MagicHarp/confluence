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
import org.confluence.mod.item.ModItems;
import org.confluence.mod.util.DelayedTaskExecutor;
import org.joml.Matrix4f;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID,value = Dist.CLIENT)
public class GuiRenderEvent {

    @SubscribeEvent
    public static void RenderGuiEvent(RenderHandEvent event) {


        if(Minecraft.getInstance().player.isUsingItem() && Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem){
            Minecraft mc = Minecraft.getInstance();

            float charge = mc.player.getTicksUsingItem() / 20.0f;
            if(charge < 0.1f) return;

            //todo 
            //ArrowItem arrowItem = mc.player.getProjectile()

            //拉弓前后位移
            float scale = 1f;
            float offset = charge < 0.65f? 0:
                    charge < 0.9f?0.65f:0.9f;

            var pose = event.getPoseStack();

            pose.pushPose();
            pose.translate(0.3, -0.2, -0.6+offset*0.3);
            pose.scale(scale,scale,scale);
            pose.mulPose(Axis.YP.rotationDegrees(2));
            pose.mulPose(Axis.XP.rotationDegrees(-100));

            ItemStack bow = mc.player.getItemInHand(InteractionHand.MAIN_HAND);

            //应用抖动
            float f7 = (float)bow.getUseDuration() - ((float)mc.player.getUseItemRemainingTicks());
            float f11 = f7 / 10.0F;
            if (f11 > 1.0F) {
                f11 = 1.0F;
            }
            if (f11 > 0.1F) {
                float f14 = Mth.sin((f7 - 0.1F) * 1.3F);
                float f17 = f11 - 0.1F;
                float f19 = f14 * f17;
                pose.translate(0, f19 * 0.004F, 0);
            }



            ItemInHandRenderer renderer = mc .gameRenderer.itemInHandRenderer;

            renderer.renderItem(
                    mc.player,
                    mc.player.getItemInHand(InteractionHand.OFF_HAND),
                    ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                    false,
                    pose,
                    mc .renderBuffers().bufferSource(),
                    event.getPackedLight()
            );

/*
            LocalPlayer player = mc.player;
            float f = Mth.lerp(1, player.xRotO, player.getXRot());

            renderer.renderArmWithItem(
                    mc.player,
                    1,
                    f,
                    mc .player.getItemInHand(InteractionHand.MAIN_HAND),
                    0,
                    event.getPoseStack(),
                    pose,
                    ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                    mc .renderBuffers().bufferSource(),
                    event.getPackedLight()
            );
*/
            pose.popPose();
        }
    }
}
