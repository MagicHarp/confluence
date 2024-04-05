package org.confluence.mod.client.handler;

import de.dafuqs.revelationary.api.revelations.WorldRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class InformationHandler {
    private static final ArrayList<Component> information = new ArrayList<>();

    private static byte time = 0;
    private static byte metalDetector = 0;
    private static byte lifeFormAnalyzer = 0;
    private static byte radar = 0;
    private static byte tallyCounter = 0;
    private static byte stopwatch = 0;
    private static byte compass = 0;
    private static byte depthMeter = 0;
    private static byte mechanicalLens = 0;

    private static boolean detectorPressed = false;

    private static @Nullable Function<Long, Component> timeInfo = null;
    private static Component metalDetectorInfo = Component.translatable("info.confluence.metal_detector.none");
    private static Component lifeFormAnalyzerInfo = Component.translatable("info.confluence.life_form_analyzer.none");
    private static Component radarInfo = Component.translatable("info.confluence.radar", 0);
    private static Component tallyCounterInfo = Component.translatable("info.confluence.tally_counter.unknown");

    public static void update(LocalPlayer localPlayer) {
        information.clear();
        long gameTime = localPlayer.level().getGameTime();

        if (time > 0) {
            if (timeInfo != null) {
                information.add(timeInfo.apply(localPlayer.level().dayTime()));
            }
        } else if (time < 0 && gameTime % 200 == 0) {
            if (time == -1) {
                if (nearPlayerNoCurio(localPlayer, HourWatch.class)) timeInfo = null;
            } else if (time == -2) {
                if (nearPlayerNoCurio(localPlayer, HalfHourWatch.class)) timeInfo = null;
            } else if (nearPlayerNoCurio(localPlayer, MinuteWatch.class)) {
                timeInfo = null;
            }
        }
        /* 天气 */
        /* 月相 */
        /* 渔力 */
        if (KeyBindings.metalDetector.get().isDown()) {
            if (!detectorPressed && metalDetector != 0) {
                detectorPressed = true;
                metalDetectorInfo = IMetalDetector.getInfo(localPlayer);
            }
        } else {
            detectorPressed = false;
        }
        if (metalDetector > 0) {
            information.add(metalDetectorInfo);
        } else if (metalDetector < 0 && gameTime % 200 == 4 && nearPlayerNoCurio(localPlayer, IMetalDetector.class)) {
            metalDetector = 0;
        }
        if (lifeFormAnalyzer > 0) {
            if (gameTime % 200 == 5) {
                lifeFormAnalyzerInfo = ILifeFormAnalyzer.getInfo(localPlayer);
            }
            information.add(lifeFormAnalyzerInfo);
        } else if (lifeFormAnalyzer < 0 && gameTime % 200 == 5 && nearPlayerNoCurio(localPlayer, ILifeFormAnalyzer.class)) {
            lifeFormAnalyzer = 0;
        }
        if (radar > 0) {
            if (gameTime % 200 == 6) {
                radarInfo = IRadar.getInfo(localPlayer);
            }
            information.add(radarInfo);
        } else if (radar < 0 && gameTime % 200 == 6 && nearPlayerNoCurio(localPlayer, IRadar.class)) {
            radar = 0;
        }
        if (tallyCounter > 0) {
            information.add(tallyCounterInfo);
        } else if (tallyCounter < 0 && gameTime % 200 == 7 && nearPlayerNoCurio(localPlayer, ITallyCounter.class)) {
            tallyCounter = 0;
        }
        /* 伤害 */
        if (stopwatch > 0) {
            information.add(IStopwatch.getInfo(localPlayer));
        } else if (stopwatch < 0 && gameTime % 200 == 9 && nearPlayerNoCurio(localPlayer, IStopwatch.class)) {
            stopwatch = 0;
        }
        if (compass > 0) {
            information.add(ICompass.getInfo(localPlayer));
        } else if (compass < 0 && gameTime % 200 == 10 && nearPlayerNoCurio(localPlayer, ICompass.class)) {
            compass = 0;
        }
        if (depthMeter > 0) {
            information.add(IDepthMeter.getInfo(localPlayer));
        } else if (depthMeter < 0 && gameTime % 200 == 11 && nearPlayerNoCurio(localPlayer, IDepthMeter.class)) {
            depthMeter = 0;
        }

        if (mechanicalLens < 0 && gameTime % 200 == 12 && nearPlayerNoCurio(localPlayer, MechanicalLens.class)) {
            mechanicalLens = 0;
        }
    }

    public static ArrayList<Component> getInformation() {
        return information;
    }

    private static boolean nearPlayerNoCurio(LocalPlayer self, Class<?> clazz) {
        return self.level().players().stream()
            .noneMatch(player -> player.distanceTo(self) < 31.5F && CuriosUtils.hasCurio(player, clazz));
    }

    public static void handlePacket(InfoCurioCheckPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            byte[] enabled = packet.enabled();
            time = enabled[0];
            int absTime = Math.abs(time);
            if (absTime == 1) {
                timeInfo = MinuteWatch::wrapTime;
            } else if (absTime == 2) {
                timeInfo = HalfHourWatch::wrapTime;
            } else if (absTime == 3) {
                timeInfo = HourWatch::wrapTime;
            } else {
                timeInfo = null;
            }
            metalDetector = enabled[4];
            lifeFormAnalyzer = enabled[5];
            radar = enabled[6];
            tallyCounter = enabled[7];
            stopwatch = enabled[9];
            compass = enabled[10];
            depthMeter = enabled[11];

            if (mechanicalLens != (byte) Math.abs(enabled[12])) {
                mechanicalLens = enabled[12];
                ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).rebuildAllChunks();
            }
        });
        context.setPacketHandled(true);
    }

    public static void handleEntityKilled(EntityKilledPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(packet.type());
            if (entityType != null) {
                tallyCounterInfo = ITallyCounter.getInfo(packet.amount() + 1, entityType.getDescription());
            }
        });
        context.setPacketHandled(true);
    }

    public static boolean isMechanicalBlockVisible() {
        return mechanicalLens != 0;
    }
}
