package org.confluence.mod.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.client.renderer.gui.ArrowInBowOverlay;
import org.confluence.mod.client.shader.ModRenderTypes;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.client.post.PostUtil.*;

@Mod.EventBusSubscriber(modid = MODID,value = Dist.CLIENT)
public class GuiRenderEvent {

    @SubscribeEvent
    public static void renderHandEvent(RenderHandEvent event) {
        ArrowInBowOverlay.render(event);
        if(bloom==null) return;
        // 如果右手无，左手有，先处理右手缓冲
        Minecraft.getInstance().gameRenderer.renderBuffers.bufferSource().endBatch();
        if(event.getHand() == InteractionHand.MAIN_HAND) bloom.mainhandItem = event.getItemStack().getItem();
        else bloom.offhandItem = event.getItemStack().getItem();


        // 物品优先级大于类
        AtomicBoolean isChild = new AtomicBoolean(false);
        AtomicReference<Class<? extends Item>> clazz = new AtomicReference<>(Item.class);
        if(!bloom.itemMap.containsKey(event.getItemStack().getItem())){
            bloom.itemClassMap.forEach((itemClass, bloomItem) -> {
                if(itemClass.isAssignableFrom(event.getItemStack().getItem().getClass())){
                    isChild.set(true);
                    clazz.set(itemClass);
                }
            });
        }
        if(bloom.itemMap.containsKey(event.getItemStack().getItem()) || isChild.get()){
            bloom.shouldRender = true;
            bloom.glowTargetOri.bindWrite(true);
            ModRenderTypes.Shaders.positionColorSampler.setMultiOutTarget(bloom.glowTargetH);
            ModRenderTypes.Shaders.positionColorSampler.apply();
            var r = Minecraft.getInstance().gameRenderer.itemInHandRenderer;
            int light;
            if(isChild.get()) light = bloom.itemClassMap.get(clazz.get()).selfGlow()?0xa000a0:event.getPackedLight();
            else light = bloom.itemMap.get(event.getItemStack().getItem()).selfGlow()?0xa000a0:event.getPackedLight();
            r.renderArmWithItem(
                    Minecraft.getInstance().player,
                    event.getPartialTick(),
                    event.getInterpolatedPitch(),
                    event.getHand(),
                    event.getSwingProgress(),
                    event.getItemStack(),
                    event.getEquipProgress(),
                    event.getPoseStack(),
                    event.getMultiBufferSource(),
                    light);
            // 及时处理缓冲
            Minecraft.getInstance().gameRenderer.renderBuffers.bufferSource().endBatch();
            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
            GlStateManager._bindTexture(Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
            event.setCanceled(true);
        }

    }
    @SubscribeEvent
    public static void renderGuiEvent(RenderGuiEvent event) {

    }

}
