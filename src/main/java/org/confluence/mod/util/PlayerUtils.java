package org.confluence.mod.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.mana.ManaProvider;
import org.confluence.mod.capability.mana.ManaStorage;
import org.confluence.mod.command.ConfluenceData;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.IScope;
import org.confluence.mod.item.curio.construction.IRightClickSubtractor;
import org.confluence.mod.item.curio.miscellaneous.SpectreGoggles;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.*;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

public final class PlayerUtils {
    public static void syncMana2Client(ServerPlayer serverPlayer, ManaStorage manaStorage) {
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new ManaPacketS2C(manaStorage.getMaxMana(), manaStorage.getCurrentMana())
        );
    }

    public static void syncMana2Client(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(ManaProvider.CAPABILITY)
            .ifPresent(manaStorage -> syncMana2Client(serverPlayer, manaStorage));
    }

    public static void regenerateMana(ServerPlayer serverPlayer) {
        serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
            int delay = manaStorage.getRegenerateDelay();
            boolean notMove = Math.abs(serverPlayer.xCloak - serverPlayer.xCloakO) < 1.0E-7;
            if (delay > 0) {
                if (manaStorage.isArcaneCrystalUsed()) delay = (int) ((float) delay * (notMove ? 0.975F : 0.95F));
                if (delay > 20 && serverPlayer.hasEffect(ModEffects.MANA_REGENERATION.get())) delay = 20;
                int delayReduce = notMove ? 2 : 1;
                if (manaStorage.hasManaRegenerationBand()) delayReduce += 1;
                manaStorage.setRegenerateDelay(delay - delayReduce);
                return;
            }

            IntSupplier receive = () -> {
                float a = manaStorage.getMaxMana() / 7.0F + (manaStorage.hasManaRegenerationBand() ? 25 : 0) + 1;
                float b = manaStorage.getCurrentMana() * 0.8F / manaStorage.getMaxMana() + 0.2F;
                if (notMove) a += manaStorage.getMaxMana() / 2.0F;
                return Math.max(Math.round(a * b * 0.0115F), 1);
            };

            if (manaStorage.receiveMana(receive)) syncMana2Client(serverPlayer, manaStorage);
        });
    }

    public static boolean extractMana(ServerPlayer serverPlayer, IntSupplier sup) {
        if (serverPlayer.gameMode.isCreative()) return true;

        AtomicBoolean success = new AtomicBoolean();
        serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
            if (manaStorage.extractMana(sup, serverPlayer)) {
                success.set(true);
                manaStorage.setRegenerateDelay((int) Math.ceil(0.7F * ((1 - (float) manaStorage.getCurrentMana() / manaStorage.getMaxMana()) * 240 + 45)));
                syncMana2Client(serverPlayer, manaStorage);
            }
        });
        return success.get();
    }

    public static void receiveMana(ServerPlayer serverPlayer, IntSupplier sup) {
        serverPlayer.getCapability(ManaProvider.CAPABILITY).ifPresent(manaStorage -> {
            if (manaStorage.receiveMana(sup)) syncMana2Client(serverPlayer, manaStorage);
        });
    }

    public static void syncSavedData(ServerPlayer serverPlayer) {
        ConfluenceData data = ConfluenceData.get(serverPlayer.serverLevel());
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new SpecificMoonPacketS2C(data.getMoonSpecific()));
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new WindSpeedPacketS2C((float) Mth.length(data.getWindSpeedX(), data.getWindSpeedZ())));
        NetworkHandler.CHANNEL.send(
            PacketDistributor.ALL.noArg(),
            new GamePhasePacketS2C(data.getGamePhase())
        );
    }

    public static void syncAdvancements(ServerPlayer serverPlayer) {
        int step = ConfluenceData.get(serverPlayer.serverLevel()).getRevealStep();
        for (int i = 0; i < step + 1; i++) {
            ResourceLocation id = Confluence.asResource("reveal/step" + i);
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(id);
            if (advancement != null) serverPlayer.getAdvancements().award(advancement, "never");
        }
    }

    public static boolean isServerNotFake(Player player) {
        return player instanceof ServerPlayer && !(player instanceof FakePlayer);
    }

    public static void resetClientPacket(ServerPlayer serverPlayer) {
        MutableFloat fartSpeed = new MutableFloat(-1.0F);
        MutableFloat sandstormSpeed = new MutableFloat(-1.0F);
        AtomicInteger sandstormTicks = new AtomicInteger();
        MutableFloat blizzardSpeed = new MutableFloat(-1.0F);
        AtomicInteger blizzardTicks = new AtomicInteger();
        MutableFloat tsunamiSpeed = new MutableFloat(-1.0F);
        MutableFloat cloudSpeed = new MutableFloat(-1.0F);
        AtomicInteger maxFlyTicks = new AtomicInteger();
        MutableFloat flySpeed = new MutableFloat();
        AtomicBoolean glide = new AtomicBoolean();
        AtomicBoolean autoAttack = new AtomicBoolean();
        AtomicBoolean scope = new AtomicBoolean();
        AtomicInteger substractor = new AtomicInteger();
        AtomicInteger climb = new AtomicInteger();
        AtomicBoolean shield = new AtomicBoolean();
        AtomicBoolean tabi = new AtomicBoolean();
        AtomicBoolean echo = new AtomicBoolean();
        AtomicInteger stepStoolSlot = new AtomicInteger(-1);
        AtomicInteger stepStoolMaxStep = new AtomicInteger();

        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            for (ICurioStacksHandler curioStacksHandler : curiosItemHandler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack itemStack = stackHandler.getStackInSlot(i);
                    if (itemStack.isEmpty()) continue;
                    Item curio = itemStack.getItem();
                    if (curio == CurioItems.SHIELD_OF_CTHULHU.get()) {
                        shield.set(true);
                        continue;
                    } else if (curio == CurioItems.SPECTRE_GOGGLES.get()) {
                        echo.set(itemStack.getTag() != null && itemStack.getTag().getBoolean(((SpectreGoggles) itemStack.getItem()).getEnableKey()));
                        continue;
                    } else if (curio instanceof StepStool) {
                        stepStoolSlot.set(i);
                        stepStoolMaxStep.set(itemStack.getOrCreateTag().getInt("maxStep"));
                        continue;
                    }

                    if (curio instanceof FartInAJar fart) {
                        fartSpeed.setValue(fart.getJumpSpeed());
                    } else if (curio instanceof SandstormInABottle sandstorm) {
                        sandstormSpeed.setValue(sandstorm.getJumpSpeed());
                        sandstormTicks.set(sandstorm.getJumpTicks());
                    } else if (curio instanceof BlizzardInABottle blizzard) {
                        blizzardSpeed.setValue(blizzard.getJumpSpeed());
                        blizzardTicks.set(blizzard.getJumpTicks());
                    } else if (curio instanceof TsunamiInABottle tsunami) {
                        tsunamiSpeed.setValue(tsunami.getJumpSpeed());
                    } else if (curio instanceof CloudInABottle cloud) {
                        cloudSpeed.setValue(cloud.getJumpSpeed());
                    } else if (curio instanceof BundleOfBalloons) {
                        sandstormSpeed.setValue(SandstormInABottle.SPEED);
                        sandstormTicks.set(SandstormInABottle.TICKS);
                        blizzardSpeed.setValue(BlizzardInABottle.SPEED);
                        blizzardTicks.set(BlizzardInABottle.TICKS);
                        cloudSpeed.setValue(CloudInABottle.SPEED);
                    }

                    if (curio instanceof IMayFly iMayFly) {
                        maxFlyTicks.set(Math.max(iMayFly.getFlyTicks(), maxFlyTicks.get()));
                        flySpeed.setValue(Math.max(iMayFly.getFlySpeed(), flySpeed.getValue()));
                        if (iMayFly.couldGlide()) glide.set(true);
                    }
                    if (curio instanceof IAutoAttack) {
                        autoAttack.set(true);
                    }
                    if (curio instanceof IScope) {
                        scope.set(true);
                    }
                    if (substractor.get() < 2 && curio instanceof IRightClickSubtractor) {
                        if (curio == CurioItems.ARCHITECT_GIZMO_PACK.get()) {
                            substractor.set(2);
                        } else {
                            substractor.addAndGet(1);
                        }
                    }
                    if (climb.get() < 2 && curio instanceof IWallClimb wallClimb) {
                        if (wallClimb.fullyWallClimb()) {
                            climb.set(2);
                            return;
                        } else {
                            climb.addAndGet(1);
                        }
                    }
                    if (curio instanceof ITabi) {
                        tabi.set(true);
                    }
                }
            }


            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerJumpPacketS2C(
                    fartSpeed.getValue(),
                    sandstormSpeed.getValue(),
                    sandstormTicks.get(),
                    blizzardSpeed.getValue(),
                    blizzardTicks.get(),
                    tsunamiSpeed.getValue(),
                    cloudSpeed.getValue()
                )
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerFlyPacketS2C(maxFlyTicks.get(), flySpeed.getValue(), glide.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new AutoAttackPacketS2C(autoAttack.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new ScopeEnablePacketS2C(scope.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new RightClickSubtractorPacketS2C(substractor.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new PlayerClimbPacketS2C(climb.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new ShieldOfCthulhuPacketS2C(shield.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new TabiPacketS2C(tabi.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new EchoBlockVisibilityPacketS2C(echo.get())
            );
            NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> serverPlayer),
                new StepStoolStepPacketS2C(stepStoolSlot.get(), stepStoolMaxStep.get())
            );
        });
    }
}
