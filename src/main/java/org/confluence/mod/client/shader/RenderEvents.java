package org.confluence.mod.client.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.helper.SpelunkerHelper;
import org.confluence.mod.mixin.accessor.FontAccessor;
import org.confluence.mod.util.ModUtils;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderEvents {


    static int tick = 20;
    private static VertexBuffer vertexBuffer;
    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
        /*
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
            LightTexture lightTexture = gameRenderer.lightTexture();
            ClientLevel level = Minecraft.getInstance().level;
            assert level != null;
            RenderSystem.setShader(() -> ModRenderTypes.Shaders.aether);
            lightTexture.turnOnLightLayer();
            int light = LevelRenderer.getLightColor(level, BlockPos.ZERO);
            lightTexture.turnOffLightLayer();
        }
*/
        Minecraft minecraft = Minecraft.getInstance();
        SpelunkerHelper blockGen= SpelunkerHelper.getSingleton(minecraft.player);
        //效果消失，清除缓存
        if(!Minecraft.getInstance().player.hasEffect(ModEffects.SPELUNKER.get())){
            if(blockGen!=null){
                blockGen.centerCache.clear();;
                blockGen.centers.clear();;
                blockGen.blockMap.clear();
            }
            return;
        }

        boolean ifRefresh = false;

        if(--tick<0){
            tick = blockGen.refreshTick;
            //todo 如果性能消耗太大，应该在破坏方块事件里设定是否刷新
            blockGen.genBlocks();
            ifRefresh = true;
        }


        Vec3 playerPos = minecraft.gameRenderer.getMainCamera().getPosition();

        //重新加载缓存，提速
        Map<Block, ArrayList<BlockPos>> centers = blockGen.centers;
        centers.clear();
        var shouldRenderCache = blockGen.centerCacheFrame;
        if(ifRefresh){
            var mapList = blockGen.getBlockMap();

            vertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder buffer = tessellator.getBuilder();
            buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

            var centerMap = blockGen.centerCache;
            shouldRenderCache.clear();
            for(var n : mapList.entrySet()) {//每种矿石
                //初始化键
                centers.put(n.getKey(),new ArrayList<>());

                Color color = blockGen.targets.get(n.getKey()).color();
                int colorInt = color.getRGB();
                if(n.getValue()==null)return;
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int a = (int) (255*0.2);


                for (net.minecraft.core.BlockPos blockProps : n.getValue()) {//每个矿石
                    if (blockProps == null) {
                        return;
                    }

                    final float size = 1.0f;
                    final int x = blockProps.getX(),
                            y = blockProps.getY(),
                            z = blockProps.getZ();

                    /* 相近同种方块禁止渲染 */
                    boolean ifNear = false;


                    if(Minecraft.getInstance().level.getBlockState(blockProps).is(Blocks.AIR)){//已被挖掘，取消缓存

                        System.out.println("block break");
                        centerMap.remove(blockProps);
                        for(BlockPos centerPos : centers.get(n.getKey())){//刷新周围中心块
                            double distance = centerPos.distSqr(blockProps);
                            if(distance < 25){//附近有中心块，清除改块
                                centers.get(n.getKey()).remove(centerPos);
                            }
                        }
                    }


                        //todo 可以优化
                    for(BlockPos centerPos : centers.get(n.getKey())){//否则查找所有的中心块
                        double distance = centerPos.distSqr(blockProps);
                        if(distance < blockGen.centerInternal){//附近有中心块，添加缓存
                            centerMap.put(blockProps, centerPos);
                            ifNear = true;
                            break;
                        }
                    }


                    if(ifNear){
                        if(!KeyBindings.TAB.get().isDown())continue;//非中心块或tab按下不渲染

                    }else{
                        centers.get(n.getKey()).add(blockProps);//太远则自己成为中心块
                        shouldRenderCache.put(blockProps,n.getKey());//只渲染中心块文本
                    }

                    //距离越远透明度越低
                    a = (int) ((255- Math.min(playerPos.distanceToSqr(blockProps.getX(),blockProps.getY(),blockProps.getZ())/(blockGen.range*blockGen.range)*255,255))* blockGen.maxAlpha);
                    if(a<=0)continue;



                    buffer.vertex(x, y + size, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y + size, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y + size, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y + size, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y + size, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y + size, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y + size, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y + size, z).color(r,g,b,a).endVertex();

                    // BOTTOM
                    buffer.vertex(x + size, y, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y, z).color(r,g,b,a).endVertex();

                    // Edge 1
                    buffer.vertex(x + size, y, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y + size, z + size).color(r,g,b,a).endVertex();

                    // Edge 2
                    buffer.vertex(x + size, y, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x + size, y + size, z).color(r,g,b,a).endVertex();

                    // Edge 3
                    buffer.vertex(x, y, z + size).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y + size, z + size).color(r,g,b,a).endVertex();

                    // Edge 4
                    buffer.vertex(x, y, z).color(r,g,b,a).endVertex();
                    buffer.vertex(x, y + size, z).color(r,g,b,a).endVertex();




                }

            }




            var build = buffer.end();
            if (build == null) {
                vertexBuffer = null;
                return;
            } else {
                vertexBuffer.bind();
                vertexBuffer.upload(build);
                VertexBuffer.unbind();
            }
        }






        if (vertexBuffer != null) {

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate(-playerPos.x(), -playerPos.y(), -playerPos.z());
            minecraft.getBlockRenderer().renderSingleBlock(minecraft.level.getBlockState(BlockPos.containing(playerPos.subtract(0,-1,0))),poseStack,minecraft.renderBuffers().bufferSource(),15, OverlayTexture.NO_OVERLAY);
            vertexBuffer.bind();
            vertexBuffer.drawWithShader(poseStack.last().pose(), event.getProjectionMatrix(), RenderSystem.getShader());
            VertexBuffer.unbind();
            poseStack.popPose();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);

            poseStack.pushPose();
            poseStack.translate(-playerPos.x(), -playerPos.y(), -playerPos.z());

            var map = blockGen.targets;
            int textRange = blockGen.textRange;
            AtomicInteger count = new AtomicInteger();
            float scale = 20;
            int textDir = blockGen.textRenderType;
            for(var n : shouldRenderCache.entrySet()){
                var  pos = n.getKey();
                var block = n.getValue();
                if(block.asItem() == Blocks.ANCIENT_DEBRIS.asItem()) count.getAndIncrement();
                if(playerPos.distanceToSqr(pos.getX(),pos.getY(),pos.getZ())< textRange*textRange){
                    if(KeyBindings.TAB.get().isDown() && map.get(block).showText()){
                        double x = pos.getX()+0.5;
                        double y = pos.getY()+0.5;
                        double z = pos.getZ()+0.5;

                        var dir = playerPos.subtract(x,y,z).scale(-1);
                        var pf = minecraft.player.getForward();
                        System.out.println(pf);
                        double angle = Math.acos(dir.dot(pf)/dir.length()/pf.length());
                        if(angle<50*Math.PI/180){
                            poseStack.pushPose();
                            poseStack.translate(x,y,z);//调整到中心位置
                            poseStack.scale(-1/scale,-1/scale,-1/scale);

                            if(textDir==0){
                                var fs = ModUtils.dirToRot(dir.scale(-1));
                                Matrix4f m = new Matrix4f().rotate(Axis.YP.rotationDegrees(180-fs[0])).rotate(Axis.XN.rotationDegrees(fs[1]));
                                poseStack.mulPoseMatrix(m);
                            }else{
                                //Quaternionf quaternionf = new Quaternionf().rotateTo( new Vector3f(0,1,0),dir.toVector3f());
                                Quaternionf q1 = new Quaternionf( event.getCamera().rotation());//旋转到摄像机视角
                                poseStack.mulPose(q1);
                            }


                            Component component = block.asItem().getDefaultInstance().getDisplayName();
                            poseStack.translate(-component.getString().length()/5.0 * scale,0.7 * scale,0);//旋转后偏移


                            ((FontAccessor) (minecraft.font)).callRenderText(Component.literal(component.getString()).withStyle(style -> style.withColor(map.get(block).color().getRGB())).getVisualOrderText(),
                                    -5, -5f, map.get(block).color().getRGB(),
                                    false, poseStack.last().pose(), minecraft.renderBuffers().bufferSource(), Font.DisplayMode.SEE_THROUGH, 0, 15 << 20 | 15 << 4);

                            poseStack.popPose();

                        }
                    }
                }
            }

            //System.out.println(count.get());
            poseStack.popPose();




        }



    }
}
