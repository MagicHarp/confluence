package org.confluence.mod.client.renderer.gui;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderHandEvent;
import org.confluence.mod.item.bow.ShortBowItem;

public class ArrowInBowOverlay {
    public static void render(RenderHandEvent event){

        if(Minecraft.getInstance().player.isUsingItem() && Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem){
            Minecraft mc = Minecraft.getInstance();
            float charge = mc.player.getTicksUsingItem() / 20.0f;
            if(charge < 0.1f) return;
            var pose = event.getPoseStack();
            pose.pushPose();
            ItemStack bow = mc.player.getItemInHand(InteractionHand.MAIN_HAND);


            //拉弓前后位移
            float scale = 1f;
            float offset = charge < 0.65f? 0:
                    charge < 0.9f?0.58f:1f;

            float f7 = (float)bow.getUseDuration() - ((float)mc.player.getUseItemRemainingTicks());
            float f11 = f7 / 10.0F;
            f11=Math.min(f11,1);
            float f19 = f11 > 0.1F? Mth.sin((f7 - 0.1F) * 1.3F) * (f11 - 0.1F):0;
            //应用抖动
            //               左右偏移        上下抖动系数
            pose.translate(0.29, f19 * 0.004F-0.1,0);
            if(bow.getItem() instanceof ShortBowItem){
                float f12 = Math.min(f7 / 20.0F,1);
                //                                  前后帧偏移系数
                pose.translate(0,0, -0.6 + f12*0.13);
            } else{
                //                                  前后阶段偏移系数  前后帧偏移系数
                pose.translate(0,0, offset*0.13-0.6 + f11*0.04);
            }

            pose.scale(scale,scale,scale+f11*0.2f);
            pose.mulPose(Axis.YP.rotationDegrees(8));
            pose.mulPose(Axis.XP.rotationDegrees(-110));

            ItemStack arrowItem = mc.player.getProjectile(bow);
            ItemInHandRenderer renderer = mc .gameRenderer.itemInHandRenderer;

            renderer.renderItem(
                    mc.player,
                    arrowItem,
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
