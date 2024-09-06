package org.confluence.mod.client.renderer;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class AchievementToast implements Toast {
    private final ResourceLocation icon;
    private final DisplayInfo displayinfo;
    private boolean playedSound;

    public AchievementToast(ResourceLocation icon, DisplayInfo displayinfo) {
        this.icon = icon;
        this.displayinfo = displayinfo;
        this.playedSound = false;
    }

    @Override
    public @NotNull Visibility render(@NotNull GuiGraphics pGuiGraphics, @NotNull ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        Font font = pToastComponent.getMinecraft().font;
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, 128.0F, 0.0F);
        renderTitle(pGuiGraphics, pTimeSinceLastVisible, font, displayinfo.getTitle());
        renderDescription(pGuiGraphics, font);
        playSound(pToastComponent, pTimeSinceLastVisible);
        renderIcon(pGuiGraphics);
        pGuiGraphics.pose().popPose();
        return (double) pTimeSinceLastVisible >= 5000.0 * pToastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void playSound(@NotNull ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        if (!playedSound && pTimeSinceLastVisible > 0L) {
            this.playedSound = true;
            if (displayinfo.getFrame() == FrameType.CHALLENGE) {
                pToastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
            }
        }
    }

    private void renderDescription(@NotNull GuiGraphics pGuiGraphics, Font font) {
        pGuiGraphics.blit(TEXTURE, 0, height(), 0, 0, width(), height());
        List<FormattedCharSequence> list = font.split(displayinfo.getDescription(), 141);
        if (list.size() == 1) {
            pGuiGraphics.drawString(font, list.get(0), 8, 18 + height(), -1, false);
        } else {
            int l = height() * 3 / 2 - list.size() * 9 / 2;
            for (FormattedCharSequence formattedcharsequence : list) {
                pGuiGraphics.drawString(font, formattedcharsequence, 8, l, 16777215, false);
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

    private void renderTitle(@NotNull GuiGraphics pGuiGraphics, long pTimeSinceLastVisible, Font font, FormattedText formattedText) {
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 0, width(), height());
        List<FormattedCharSequence> list = font.split(formattedText, 125);
        int i = displayinfo.getFrame() == FrameType.CHALLENGE ? 16746751 : 16776960;
        if (list.size() == 1) {
            pGuiGraphics.drawString(font, displayinfo.getFrame().getDisplayName(), 30, 7, i | -16777216, false);
            pGuiGraphics.drawString(font, list.get(0), 30, 18, -1, false);
        } else {
            if (pTimeSinceLastVisible < 1500L) {
                int k = Mth.floor(Mth.clamp((float) (1500L - pTimeSinceLastVisible) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                pGuiGraphics.drawString(font, displayinfo.getFrame().getDisplayName(), 30, 11, i | k, false);
            } else {
                int i1 = Mth.floor(Mth.clamp((float) (pTimeSinceLastVisible - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                int l = this.height() / 2 - list.size() * 9 / 2;

                for (FormattedCharSequence formattedcharsequence : list) {
                    pGuiGraphics.drawString(font, formattedcharsequence, 30, l, 16777215 | i1, false);
                    l += 9;
                }
            }
        }
    }
}
