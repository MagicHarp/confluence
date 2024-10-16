package org.confluence.mod.client.post;


import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.shader.LightPostPass;
import org.confluence.mod.client.shader.ModRenderTypes;
import org.confluence.mod.mixin.client.accessor.GameRendererAccessor;
import org.confluence.mod.mixin.client.accessor.PostChainAccessor;
import org.joml.Vector4f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;

public class postUtil {





}
