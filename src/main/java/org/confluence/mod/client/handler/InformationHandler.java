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
import org.confluence.mod.network.s2c.AttackDamagePacketS2C;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.confluence.mod.network.s2c.WindSpeedPacketS2C;
import org.confluence.mod.util.CuriosUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class InformationHandler {
    private static final ArrayList<Component> information = new ArrayList<>();

    private static byte time = 0;
    private static byte weatherRadio = 0;
    private static byte sextant = 0;
    private static byte fishermansPocketGuide = 0;
    private static byte metalDetector = 0;
    private static byte lifeFormAnalyzer = 0;
    private static byte radar = 0;
    private static byte tallyCounter = 0;
    private static byte dpsMeter = 0;
    private static byte stopwatch = 0;
    private static byte compass = 0;
    private static byte depthMeter = 0;
    private static byte mechanicalLens = 0;

    private static @Nullable Function<Long, Component> timeInfo = null;
    private static Component weatherRadioInfo = Component.translatable("info.confluence.weather_radio.clear", "0.00");
    private static boolean detectorPressed = false;
    private static Component metalDetectorInfo = Component.translatable("info.confluence.metal_detector.none");
    private static Component lifeFormAnalyzerInfo = Component.translatable("info.confluence.life_form_analyzer.none");
    private static Component radarInfo = Component.translatable("info.confluence.radar", 0);
    private static Component tallyCounterInfo = Component.translatable("info.confluence.tally_counter.unknown");
    private static long lastAttackTime = 0;
    private static float attackDamage = 0;
    private static Component dpsMeterInfo = Component.translatable("info.confluence.dps_meter", 0.00F);

    public static void update(LocalPlayer localPlayer) {
        information.clear();
        long gameTime = localPlayer.level().getGameTime();

        if (time != 0) {
            if (timeInfo != null) {
                information.add(timeInfo.apply(localPlayer.level().dayTime()));
            }
        }
        if (time < 0 && gameTime % 200 == 0) {
            if (time == -1) {
                if (nearPlayerNoCurio(localPlayer, HourWatch.class)) timeInfo = null;
            } else if (time == -2) {
                if (nearPlayerNoCurio(localPlayer, HalfHourWatch.class)) timeInfo = null;
            } else if (nearPlayerNoCurio(localPlayer, MinuteWatch.class)) {
                timeInfo = null;
            }
        }
        if (weatherRadio != 0) information.add(weatherRadioInfo);
        if (weatherRadio < 0 && gameTime % 200 == 1 && nearPlayerNoCurio(localPlayer, IWeatherRadio.class)) {
            weatherRadio = 0;
        }
        if (sextant != 0) information.add(ISextant.getInfo(localPlayer));
        if (sextant < 0 && gameTime % 200 == 2 && nearPlayerNoCurio(localPlayer, ISextant.class)) {
            sextant = 0;
        }
        if (fishermansPocketGuide != 0) information.add(IFishermansPocketGuide.getInfo(localPlayer));
        if (fishermansPocketGuide < 0 && gameTime % 200 == 3 && nearPlayerNoCurio(localPlayer, IFishermansPocketGuide.class)) {
            fishermansPocketGuide = 0;
        }
        if (KeyBindings.metalDetector.get().isDown()) {
            if (!detectorPressed && metalDetector != 0) {
                detectorPressed = true;
                metalDetectorInfo = IMetalDetector.getInfo(localPlayer);
            }
        } else {
            detectorPressed = false;
        }
        if (metalDetector != 0) information.add(metalDetectorInfo);
        if (metalDetector < 0 && gameTime % 200 == 4 && nearPlayerNoCurio(localPlayer, IMetalDetector.class)) {
            metalDetector = 0;
        }
        if (lifeFormAnalyzer != 0) {
            if (gameTime % 200 == 5) lifeFormAnalyzerInfo = ILifeFormAnalyzer.getInfo(localPlayer);
            information.add(lifeFormAnalyzerInfo);
        }
        if (lifeFormAnalyzer < 0 && gameTime % 200 == 5 && nearPlayerNoCurio(localPlayer, ILifeFormAnalyzer.class)) {
            lifeFormAnalyzer = 0;
        }
        if (radar != 0) {
            if (gameTime % 200 == 6) radarInfo = IRadar.getInfo(localPlayer);
            information.add(radarInfo);
        }
        if (radar < 0 && gameTime % 200 == 6 && nearPlayerNoCurio(localPlayer, IRadar.class)) {
            radar = 0;
        }
        if (tallyCounter != 0) information.add(tallyCounterInfo);
        if (tallyCounter < 0 && gameTime % 200 == 7 && nearPlayerNoCurio(localPlayer, ITallyCounter.class)) {
            tallyCounter = 0;
        }
        if (dpsMeter != 0) {
            long delta = gameTime - lastAttackTime;
            if (delta > 1200) attackDamage = 0.0F;
            else dpsMeterInfo = IDPSMeter.getInfo(attackDamage / delta);
            information.add(dpsMeterInfo);
        }
        if (dpsMeter < 0 && gameTime % 200 == 8 && nearPlayerNoCurio(localPlayer, IDPSMeter.class)) {
            dpsMeter = 0;
        }
        if (stopwatch != 0) information.add(IStopwatch.getInfo(localPlayer));
        if (stopwatch < 0 && gameTime % 200 == 9 && nearPlayerNoCurio(localPlayer, IStopwatch.class)) {
            stopwatch = 0;
        }
        if (compass != 0) information.add(ICompass.getInfo(localPlayer));
        if (compass < 0 && gameTime % 200 == 10 && nearPlayerNoCurio(localPlayer, ICompass.class)) {
            compass = 0;
        }
        if (depthMeter != 0) information.add(IDepthMeter.getInfo(localPlayer));
        if (depthMeter < 0 && gameTime % 200 == 11 && nearPlayerNoCurio(localPlayer, IDepthMeter.class)) {
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
            weatherRadio = enabled[1];
            sextant = enabled[2];
            fishermansPocketGuide = enabled[3];
            metalDetector = enabled[4];
            lifeFormAnalyzer = enabled[5];
            radar = enabled[6];
            tallyCounter = enabled[7];
            dpsMeter = enabled[8];
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

    public static void handleAttackDamage(AttackDamagePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            attackDamage += packet.amount();
            lastAttackTime = packet.gameTime();
        });
        context.setPacketHandled(true);
    }

    public static void handleWindSpeed(WindSpeedPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            weatherRadioInfo = IWeatherRadio.getInfo(localPlayer, packet.x(), packet.z());
        });
        context.setPacketHandled(true);
    }
}
