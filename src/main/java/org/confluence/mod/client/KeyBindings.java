package org.confluence.mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class KeyBindings {
    @SubscribeEvent
    public static void keyBinding(RegisterKeyMappingsEvent event) {
        event.register(METAL_DETECTOR.get());
        event.register(STEP_STOOL.get());
    }

    public static final Lazy<KeyMapping> METAL_DETECTOR = Lazy.of(() -> new KeyMapping(
        "key.confluence.metal_detector",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_RIGHT_CONTROL,
        "key.categories.misc"
    ));

    public static final Lazy<KeyMapping> STEP_STOOL = Lazy.of(() -> new KeyMapping(
        "key.confluence.step_stool",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_UP,
        "key.categories.gameplay"
    ));
}
