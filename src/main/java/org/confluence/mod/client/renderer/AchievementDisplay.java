package org.confluence.mod.client.renderer;

import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;

public record AchievementDisplay(FrameType frame, Component title, Component description) {}
