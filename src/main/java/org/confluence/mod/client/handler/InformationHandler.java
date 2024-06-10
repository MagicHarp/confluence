package org.confluence.mod.client.handler;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.client.KeyBindings;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.network.s2c.AttackDamagePacketS2C;
import org.confluence.mod.network.s2c.EntityKilledPacketS2C;
import org.confluence.mod.network.s2c.InfoCurioCheckPacketS2C;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class InformationHandler {
    private static final ArrayList<Component> information = new ArrayList<>();

    private static final byte[] infoData = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final Int2ObjectOpenHashMap<byte[]> REMOTE_DATA = new Int2ObjectOpenHashMap<>();

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

        byte b = infoData[IWatch.INDEX];
        if (b != 0 && timeInfo != null) {
            information.add(timeInfo.apply(localPlayer.level().dayTime()));
        }

        if (infoData[IWeatherRadio.INDEX] != 0) {
            if (gameTime % 200 == IWeatherRadio.INDEX) weatherRadioInfo = IWeatherRadio.getInfo(localPlayer);
            information.add(weatherRadioInfo);
        }

        if (infoData[ISextant.INDEX] != 0) {
            information.add(ISextant.getInfo(localPlayer));
        }

        if (infoData[IFishermansPocketGuide.INDEX] != 0) {
            information.add(IFishermansPocketGuide.getInfo(localPlayer));
        }

        b = infoData[IMetalDetector.INDEX];
        if (KeyBindings.METAL_DETECTOR.get().isDown()) {
            if (!detectorPressed && b != 0) {
                detectorPressed = true;
                metalDetectorInfo = IMetalDetector.getInfo(localPlayer);
            }
        } else detectorPressed = false;
        if (b != 0) {
            information.add(metalDetectorInfo);
        }

        if (infoData[ILifeFormAnalyzer.INDEX] != 0) {
            if (gameTime % 200 == ILifeFormAnalyzer.INDEX) lifeFormAnalyzerInfo = ILifeFormAnalyzer.getInfo(localPlayer);
            information.add(lifeFormAnalyzerInfo);
        }

        if (infoData[IRadar.INDEX] != 0) {
            if (gameTime % 200 == IRadar.INDEX) radarInfo = IRadar.getInfo(localPlayer);
            information.add(radarInfo);
        }

        if (infoData[ITallyCounter.INDEX] != 0) {
            information.add(tallyCounterInfo);
        }

        if (infoData[IDPSMeter.INDEX] != 0) {
            long delta = gameTime - lastAttackTime;
            if (delta % 20 == 0) {
                float sum = 0.0F;
                for (float value : attackDamage) sum += value;
                dpsMeterInfo = IDPSMeter.getInfo(sum / (attackDamage.size() + 1));
            }
            information.add(dpsMeterInfo);
        }

        if (infoData[IStopwatch.INDEX] != 0) {
            information.add(IStopwatch.getInfo(localPlayer));
        }

        if (infoData[ICompass.INDEX] != 0) {
            information.add(ICompass.getInfo(localPlayer));
        }

        if (infoData[IDepthMeter.INDEX] != 0) {
            information.add(IDepthMeter.getInfo(localPlayer));
        }

        if (gameTime % 200 == 0) {
            for (int i = 0; i < infoData.length; i++) {
                if (infoData[i] >= 0) continue;
                boolean match = false;
                for (Player player : localPlayer.level().players()) {
                    if (player == localPlayer || player.distanceToSqr(localPlayer) > 1024.0) continue;
                    byte[] data = REMOTE_DATA.get(player.getId());
                    if (data == null) continue;
                    if (data[i] > -125) {
                        match = true;
                        break;
                    }
                }
                if (!match) infoData[i] = 0;
            }
        }
    }

    public static ArrayList<Component> getInformation() {
        return information;
    }

    public static void handlePacket(InfoCurioCheckPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            byte[] enabled = packet.enabled();
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && packet.playerId() != player.getId()) {
                // 存入远程玩家信息
                REMOTE_DATA.put(packet.playerId(), packet.enabled());
            }

            byte b = enabled[IWatch.INDEX];
            byte c = infoData[IWatch.INDEX];
            // 玩家发给自己的信息 || 收到别人共享的信息
            if ((b >= 0 && c >= 0) || (b != -125 && c <= 0)) infoData[IWatch.INDEX] = b;
            timeInfo = switch (infoData[IWatch.INDEX]) {
                case HourWatch.OWNER, HourWatch.OTHER -> HourWatch::wrapTime;
                case HalfHourWatch.OWNER, HalfHourWatch.OTHER -> HalfHourWatch::wrapTime;
                case MinuteWatch.OWNER, MinuteWatch.OTHER -> MinuteWatch::wrapTime;
                default -> null;
            };

            b = enabled[IWeatherRadio.INDEX];
            c = infoData[IWeatherRadio.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IWeatherRadio.INDEX] = b;

            b = enabled[ISextant.INDEX];
            c = infoData[ISextant.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[ISextant.INDEX] = b;

            b = enabled[IFishermansPocketGuide.INDEX];
            c = infoData[IFishermansPocketGuide.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IFishermansPocketGuide.INDEX] = b;

            b = enabled[IMetalDetector.INDEX];
            c = infoData[IMetalDetector.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IMetalDetector.INDEX] = b;

            b = enabled[ILifeFormAnalyzer.INDEX];
            c = infoData[ILifeFormAnalyzer.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[ILifeFormAnalyzer.INDEX] = b;

            b = enabled[IRadar.INDEX];
            c = infoData[IRadar.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IRadar.INDEX] = b;

            b = enabled[ITallyCounter.INDEX];
            c = infoData[ITallyCounter.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[ITallyCounter.INDEX] = b;

            b = enabled[IDPSMeter.INDEX];
            c = infoData[IDPSMeter.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IDPSMeter.INDEX] = b;

            b = enabled[IStopwatch.INDEX];
            c = infoData[IStopwatch.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IStopwatch.INDEX] = b;

            b = enabled[ICompass.INDEX];
            c = infoData[ICompass.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[ICompass.INDEX] = b;

            b = enabled[IDepthMeter.INDEX];
            c = infoData[IDepthMeter.INDEX];
            if ((b >= 0 && c >= 0) || (b != -128 && c <= 0)) infoData[IDepthMeter.INDEX] = b;
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
