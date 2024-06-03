package org.confluence.mod.client.handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.network.s2c.AttackDamagePacketS2C;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class InformationHandler {
    private static final ArrayList<Component> information = new ArrayList<>();

    private static byte time = 0;
    private static byte weatherRadio = 0;
    private static byte sextant = 0;
    private static byte fpg = 0;
    private static byte metalDetector = 0;
    private static byte lfa = 0;
    private static byte radar = 0;
    private static byte tallyCounter = 0;
    private static byte dpsMeter = 0;
    private static byte stopwatch = 0;
    private static byte compass = 0;
    private static byte depthMeter = 0;

    private static @Nullable Function<Long, Component> timeInfo = null;
    private static Component weatherRadioInfo = Component.translatable("info.confluence.weather_radio.clear", "0.00");
    private static boolean detectorPressed = false;
    private static Component metalDetectorInfo = Component.translatable("info.confluence.metal_detector.none");
    private static Component lifeFormAnalyzerInfo = Component.translatable("info.confluence.life_form_analyzer.none");
    private static Component radarInfo = Component.translatable("info.confluence.radar", 0);
    private static Component tallyCounterInfo = Component.translatable("info.confluence.tally_counter.unknown");
    private static long lastAttackTime = 0;
    private static final ArrayDeque<Float> attackDamage = new ArrayDeque<>(5);
    private static Component dpsMeterInfo = Component.translatable("info.confluence.dps_meter", 0.00F);

    public static void handle(LocalPlayer localPlayer) {
        information.clear();
        long gameTime = localPlayer.level().getGameTime();

        if (time != 0 && timeInfo != null) {
            information.add(timeInfo.apply(localPlayer.level().dayTime()));
            if (time < 0 && gameTime % 200 == 0) {
                if (time == HourWatch.OTHER) {
                    if (check(localPlayer, HourWatch.class)) timeInfo = null;
                } else if (time == HalfHourWatch.OTHER) {
                    if (check(localPlayer, HalfHourWatch.class)) timeInfo = null;
                } else if (time == MinuteWatch.OTHER) {
                    if (check(localPlayer, MinuteWatch.class)) timeInfo = null;
                }
            }
        }
        if (weatherRadio != 0) {
            if (gameTime % 200 == IWeatherRadio.INDEX) weatherRadioInfo = IWeatherRadio.getInfo(localPlayer);
            information.add(weatherRadioInfo);
            if (weatherRadio < 0 && gameTime % 200 == IWeatherRadio.INDEX && check(localPlayer, IWeatherRadio.class)) weatherRadio = 0;
        }
        if (sextant != 0) {
            information.add(ISextant.getInfo(localPlayer));
            if (sextant < 0 && gameTime % 200 == ISextant.INDEX && check(localPlayer, ISextant.class)) sextant = 0;
        }
        if (fpg != 0) {
            information.add(IFishermansPocketGuide.getInfo(localPlayer));
            if (fpg < 0 && gameTime % 200 == IFishermansPocketGuide.INDEX && check(localPlayer, IFishermansPocketGuide.class)) fpg = 0;
        }
        if (KeyBindings.METAL_DETECTOR.get().isDown()) {
            if (!detectorPressed && metalDetector != 0) {
                detectorPressed = true;
                metalDetectorInfo = IMetalDetector.getInfo(localPlayer);
            }
        } else detectorPressed = false;
        if (metalDetector != 0) {
            information.add(metalDetectorInfo);
            if (metalDetector < 0 && gameTime % 200 == IMetalDetector.INDEX && check(localPlayer, IMetalDetector.class)) metalDetector = 0;
        }
        if (lfa != 0) {
            if (gameTime % 200 == ILifeFormAnalyzer.INDEX) lifeFormAnalyzerInfo = ILifeFormAnalyzer.getInfo(localPlayer);
            information.add(lifeFormAnalyzerInfo);
            if (lfa < 0 && gameTime % 200 == ILifeFormAnalyzer.INDEX && check(localPlayer, ILifeFormAnalyzer.class)) lfa = 0;
        }
        if (radar != 0) {
            if (gameTime % 200 == IRadar.INDEX) radarInfo = IRadar.getInfo(localPlayer);
            information.add(radarInfo);
            if (radar < 0 && gameTime % 200 == IRadar.INDEX && check(localPlayer, IRadar.class)) radar = 0;
        }
        if (tallyCounter != 0) {
            information.add(tallyCounterInfo);
            if (tallyCounter < 0 && gameTime % 200 == ITallyCounter.INDEX && check(localPlayer, ITallyCounter.class)) tallyCounter = 0;
        }
        if (dpsMeter != 0) {
            long delta = gameTime - lastAttackTime;
            if (delta % 20 == 0) {
                float sum = 0.0F;
                for (float value : attackDamage) sum += value;
                dpsMeterInfo = IDPSMeter.getInfo(sum / (attackDamage.size() + 1));
            }
            information.add(dpsMeterInfo);
            if (dpsMeter < 0 && gameTime % 200 == IDPSMeter.INDEX && check(localPlayer, IDPSMeter.class)) dpsMeter = 0;
        }
        if (stopwatch != 0) {
            information.add(IStopwatch.getInfo(localPlayer));
            if (stopwatch < 0 && gameTime % 200 == IStopwatch.INDEX && check(localPlayer, IStopwatch.class)) stopwatch = 0;
        }
        if (compass != 0) {
            information.add(ICompass.getInfo(localPlayer));
            if (compass < 0 && gameTime % 200 == ICompass.INDEX && check(localPlayer, ICompass.class)) compass = 0;
        }
        if (depthMeter != 0) {
            information.add(IDepthMeter.getInfo(localPlayer));
            if (depthMeter < 0 && gameTime % 200 == IDepthMeter.INDEX && check(localPlayer, IDepthMeter.class)) depthMeter = 0;
        }
    }

    public static ArrayList<Component> getInformation() {
        return information;
    }

    private static boolean check(LocalPlayer self, Class<?> clazz) {
        return self.level().players().stream()
            .noneMatch(player -> player.distanceTo(self) < 31.5F && CuriosUtils.hasCurio(player, clazz));
    }

    public static void handlePacket(InfoCurioCheckPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            byte[] enabled = packet.enabled();
            byte b = enabled[IWatch.INDEX];
            // 玩家发给自己的信息 || 收到别人共享的信息
            if ((b >= 0 && time >= 0) || (b != -125 && time < 0)) time = b;
            timeInfo = switch (time) {
                case HourWatch.OWNER, HourWatch.OTHER -> HourWatch::wrapTime;
                case HalfHourWatch.OWNER, HalfHourWatch.OTHER -> HalfHourWatch::wrapTime;
                case MinuteWatch.OWNER, MinuteWatch.OTHER -> MinuteWatch::wrapTime;
                default -> null;
            };
            b = enabled[IWeatherRadio.INDEX];
            if ((b >= 0 && weatherRadio >= 0) || (b != -128 && weatherRadio < 0)) weatherRadio = b;
            b = enabled[ISextant.INDEX];
            if ((b >= 0 && sextant >= 0) || (b != -128 && sextant < 0)) sextant = b;
            b = enabled[IFishermansPocketGuide.INDEX];
            if ((b >= 0 && fpg >= 0) || (b != -128 && fpg < 0)) fpg = b;
            b = enabled[IMetalDetector.INDEX];
            if ((b >= 0 && metalDetector >= 0) || (b != -128 && metalDetector < 0)) metalDetector = b;
            b = enabled[ILifeFormAnalyzer.INDEX];
            if ((b >= 0 && lfa >= 0) || (b != -128 && lfa < 0)) lfa = b;
            b = enabled[IRadar.INDEX];
            if ((b >= 0 && radar >= 0) || (b != -128 && radar < 0)) radar = b;
            b = enabled[ITallyCounter.INDEX];
            if ((b >= 0 && tallyCounter >= 0) || (b != -128 && tallyCounter < 0)) tallyCounter = b;
            b = enabled[IDPSMeter.INDEX];
            if ((b >= 0 && dpsMeter >= 0) || (b != -128 && dpsMeter < 0)) dpsMeter = b;
            b = enabled[IStopwatch.INDEX];
            if ((b >= 0 && stopwatch >= 0) || (b != -128 && stopwatch < 0)) stopwatch = b;
            b = enabled[ICompass.INDEX];
            if ((b >= 0 && compass >= 0) || (b != -128 && compass < 0)) compass = b;
            b = enabled[IDepthMeter.INDEX];
            if ((b >= 0 && depthMeter >= 0) || (b != -128 && depthMeter < 0)) depthMeter = b;
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

    public static void handleAttackDamage(AttackDamagePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (attackDamage.size() == 5) attackDamage.removeLast();
            attackDamage.addFirst(packet.amount());
            lastAttackTime = packet.gameTime();
        });
        context.setPacketHandled(true);
    }
}
