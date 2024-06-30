package org.confluence.mod.client.screens;

import org.checkerframework.checker.units.qual.h;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import javax.annotation.Nullable;
import org.confluence.mod.client.handler.ClientPacketHandler;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class Information {
    private static double maxHealth = -1;
    private static double currentHealth = -1;

    public static double execute(Entity entity) {
        return execute(entity);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        Level world = null;
        double x = 0;
        double y = 0;
        double z = 0;
        Player entity = Minecraft.getInstance().player;
        if (entity != null) {
            world = entity.level();
            x = entity.getX();
            y = entity.getY();
            z = entity.getZ();
        }
        if (entity instanceof LivingEntity) {
            LivingEntity livEnt = (LivingEntity) entity;
            maxHealth = livEnt.getMaxHealth();
        } else {
            maxHealth = -1;
        }

        if (entity instanceof LivingEntity) {
            LivingEntity livEnt = (LivingEntity) entity;
            currentHealth = livEnt.getHealth();
        } else {
            currentHealth = -1;
        }
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        int maxHeartCount = (int) (((int) maxHealth) / 4);
        int currentHealthCount = (int) (((int) currentHealth) / 4);
        double lastHeart = 0.0d;
        if (((int) maxHealth) % 4 != 0) {
            maxHeartCount += 1;
        }
        if (((int) currentHealth) % 4 != 0) {
            currentHealthCount += 1;
            lastHeart = (currentHealth % 4) / 4;
        }
        int count = 0;
        int heartCount = 0;
        int smP = (int) (maxHeartCount / 10);
        int heartSmP = (int) (currentHealthCount / 10);
        if (maxHeartCount % 10 != 0) {
            smP += 1;
        }
        if (currentHealthCount % 10 != 0) {
            heartSmP += 1;
        }
        int line = smP * 14;
        int heartLine = heartSmP * 14;
        while (smP > 0) {
            if (smP > 1) {
                count = 8;
            } else if (maxHeartCount % 10 != 0) {
                count = maxHeartCount % 10 - 2;
            } else {
                count = 8;
            }
            int setCount = 8 - count;
            if (maxHeartCount <= 1) {
                event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_single_fancy.png"), w - 20 - 28 - setCount * 12, 10 + line - smP * 14, 0, 0, 18, 19, 18, 19);
            } else {
                event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_left.png"), w - 20 - (count + 1) * 12 - 26 - setCount * 12, 12 + line - smP * 14, 0, 0, 14, 15, 14, 15);
                //绘制血条左端
                if (count >= 1) {
                    while (count > 0) {
                        event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_middle.png"), w - 20 - count * 12 - 24 - setCount * 12, 12 + line - smP * 14, 0, 0, 12, 15, 12, 15);
                        count -= 1;
                    }
                }
                //绘制血条中部
                if (smP == 1) {
                    event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_right_fancy.png"), w - 20 - 28 - setCount * 12, 10 + line - smP * 14, 0, 0, 18, 19, 18, 19);
                } else {
                    event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_right.png"), w - 20 - 24 - setCount * 12, 12 + line - smP * 14, 0, 0, 13, 15, 13, 15);
                }
                //绘制血条右端
            }
            smP -= 1;
        }
        while (heartSmP > 0) {
            if (heartSmP > 1) {
                heartCount = 8;
            } else if (currentHealthCount % 10 != 0) {
                heartCount = currentHealthCount % 10 - 2;
            } else {
                heartCount = 8;
            }
            int setHeartCount = 8 - heartCount;
            if (currentHealthCount <= 1) {
                event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_fill.png"), w - 20 - 28 + 4 - setHeartCount * 12, 10 + heartLine - heartSmP * 14 + 4, 0, 0, 11, 11, 11, 11);
            } else {
                event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_fill.png"), w - 20 - (heartCount + 1) * 12 - 26 + 2 - setHeartCount * 12, 12 + heartLine - heartSmP * 14 + 2, 0, 0, 11, 11, 11, 11);
                //绘制血条左端
                if (heartCount >= 1) {
                    while (heartCount > 0) {
                        event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_fill.png"), w - 20 - heartCount * 12 - 24 - setHeartCount * 12, 12 + heartLine - heartSmP * 14 + 2, 0, 0, 11, 11, 11, 11);
                        heartCount -= 1;
                    }
                }
                //绘制血条中部
                if (heartSmP == 1 && lastHeart != 0.0f) {
                    event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_fill.png"), w - 20 - 24 - setHeartCount * 12 + (int) ((11 - 11 * lastHeart) / 2 + 0.5d), 12 + heartLine - heartSmP * 14 + 2 + (int) ((11 - 11 * lastHeart) / 2 + 0.5d), 0, 0, ((int) (11 * lastHeart)), ((int) (11 * lastHeart)), ((int) (11 * lastHeart)), ((int) (11 * lastHeart)));
                } else {
                    event.getGuiGraphics().blit(new ResourceLocation("confluence:textures/gui/screens/heart_fill.png"), w - 20 - 24 - setHeartCount * 12, 12 + heartLine - heartSmP * 14 + 2, 0, 0, 11, 11, 11, 11);
                }
                //绘制血条右端
            }
            heartSmP -= 1;
        }
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
