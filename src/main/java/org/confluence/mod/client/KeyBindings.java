package org.confluence.mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public final class KeyBindings {
    public static final Lazy<KeyMapping> metalDetector = Lazy.of(() -> new KeyMapping(
        "key.confluence.metal_detector",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_RIGHT_CONTROL,
        "key.categories.misc"
    ));
}
