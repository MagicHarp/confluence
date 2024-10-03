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

import static org.confluence.mod.effect.beneficial.helper.SpelunkerHelper.renderLevel;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderEvents {



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
        renderLevel(event);//洞探

    }
}
