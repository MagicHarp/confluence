package org.confluence.mod.client.renderer;

import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class AchievementToast implements Toast {
    private static final Hashtable<ResourceLocation, Toast> ACHIEVEMENTS = new Hashtable<>();
    private final ResourceLocation icon;
    private final AchievementDisplay display;
    public boolean playedSound;

    public AchievementToast(ResourceLocation icon, AchievementDisplay display) {
        this.icon = icon;
        this.display = display;
        this.playedSound = false;
    }

    @Override
    public int height() {
        return 64;
    }

    @Override
    public @NotNull Visibility render(@NotNull GuiGraphics pGuiGraphics, @NotNull ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        Font font = pToastComponent.getMinecraft().font;
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, 128.0F, 0.0F);
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 0, width(), height());
        renderTitle(pGuiGraphics, pTimeSinceLastVisible, font);
        renderDescription(pGuiGraphics, font);
        renderIcon(pGuiGraphics);
        pGuiGraphics.pose().popPose();
        playSound(pToastComponent, pTimeSinceLastVisible);
        return (double) pTimeSinceLastVisible >= 5000.0 * pToastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void playSound(@NotNull ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        if (!playedSound && pTimeSinceLastVisible > 0L) {
            this.playedSound = true;
            if (display.frame() == FrameType.CHALLENGE) {
                pToastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
            }
        }
    }

    private void renderDescription(@NotNull GuiGraphics pGuiGraphics, Font font) {
        List<FormattedCharSequence> list = font.split(display.description(), 141);
        if (list.size() == 1) {
            pGuiGraphics.drawString(font, list.get(0), 8, 18 + 32, -1, false);
        } else {
            int l = 48 - list.size() * 9 / 2;
            for (FormattedCharSequence formattedcharsequence : list) {
                pGuiGraphics.drawString(font, formattedcharsequence, 8, l, 0, false);
                l += 9;
            }
        }
    }

    private void renderIcon(@NotNull GuiGraphics pGuiGraphics) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(4, 4, 0);
        pGuiGraphics.pose().scale(0.375F, 0.375F, 1.0F);
        pGuiGraphics.blit(icon, 0, 0, 0, 0, 64, 64, 64, 64);
        pGuiGraphics.pose().popPose();
    }

    private void renderTitle(@NotNull GuiGraphics pGuiGraphics, long pTimeSinceLastVisible, Font font) {
        List<FormattedCharSequence> list = font.split(display.title(), 125);
        int i = display.frame() == FrameType.CHALLENGE ? 16746751 : 16776960;
        if (list.size() == 1) {
            pGuiGraphics.drawString(font, display.frame().getDisplayName(), 30, 7, i | -16777216, false);
            pGuiGraphics.drawString(font, list.get(0), 30, 18, -1, false);
        } else {
            if (pTimeSinceLastVisible < 1500L) {
                int k = Mth.floor(Mth.clamp((float) (1500L - pTimeSinceLastVisible) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                pGuiGraphics.drawString(font, display.frame().getDisplayName(), 30, 11, i | k, false);
            } else {
                int i1 = Mth.floor(Mth.clamp((float) (pTimeSinceLastVisible - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                int l = 16 - list.size() * 9 / 2;

                for (FormattedCharSequence formattedcharsequence : list) {
                    pGuiGraphics.drawString(font, formattedcharsequence, 30, l, 16777215 | i1, false);
                    l += 9;
                }
            }
        }
    }

    public static void registerToast(ResourceLocation advancement, Toast toast) {
        ACHIEVEMENTS.put(advancement, toast);
    }

    public static void registerToast(ResourceLocation advancement) {
        String namespace = advancement.getNamespace();
        String path = advancement.getPath();
        ACHIEVEMENTS.put(advancement, new AchievementToast(
                new ResourceLocation(namespace, "textures/achievement/" + path + ".png"),
                new AchievementDisplay(FrameType.CHALLENGE,
                        Component.translatable("achievements." + namespace + "." + path + ".title"),
                        Component.translatable("achievements." + namespace + "." + path + ".description")
                )));
    }

    public static Toast getToast(ResourceLocation advancement) {
        return ACHIEVEMENTS.get(advancement);
    }

    public static void removeToast(ResourceLocation advancement) {
        ACHIEVEMENTS.remove(advancement);
    }

    public static void clearToast() {
        ACHIEVEMENTS.clear();
    }
}
