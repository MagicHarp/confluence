package org.confluence.mod.util;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
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
            ResourceLocation id = new ResourceLocation(Confluence.MODID, "reveal/step" + i);
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(id);
            if (advancement != null) serverPlayer.getAdvancements().award(advancement, "never");
        }
    }

    public static boolean isServerNotFake(Player player) {
        return player instanceof ServerPlayer && !(player instanceof FakePlayer);
    }

    public static void resetClientPacket(ServerPlayer serverPlayer) {
        AtomicDouble fartSpeed = new AtomicDouble(-1.0);
        AtomicDouble sandstormSpeed = new AtomicDouble(-1.0);
        AtomicInteger sandstormTicks = new AtomicInteger();
        AtomicDouble blizzardSpeed = new AtomicDouble(-1.0);
        AtomicInteger blizzardTicks = new AtomicInteger();
        AtomicDouble tsunamiSpeed = new AtomicDouble(-1.0);
        AtomicDouble cloudSpeed = new AtomicDouble(-1.0);
        AtomicInteger maxFlyTicks = new AtomicInteger();
        AtomicDouble flySpeed = new AtomicDouble();
        AtomicBoolean glide = new AtomicBoolean();
        AtomicBoolean autoAttack = new AtomicBoolean();
        AtomicBoolean scope = new AtomicBoolean();
        AtomicInteger substractor = new AtomicInteger();
        AtomicInteger climb = new AtomicInteger();
        AtomicBoolean shield = new AtomicBoolean();
        AtomicBoolean tabi = new AtomicBoolean();


        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                Item curio = itemStack.getItem();
                if (curio == CurioItems.SHIELD_OF_CTHULHU.get()) {
                    shield.set(true);
                    continue;
                } else if (curio == CurioItems.SPECTRE_GOGGLES.get()) {
                    NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> serverPlayer),
                        new EchoBlockVisibilityPacketS2C(itemStack.getTag() != null && itemStack.getTag().getBoolean(((SpectreGoggles) itemStack.getItem()).getEnableKey()))
                    );
                    continue;
                }
                if (curio instanceof FartInAJar fart) {
                    fartSpeed.set(fart.getJumpSpeed());
                } else if (curio instanceof SandstormInABottle sandstorm) {
                    sandstormSpeed.set(sandstorm.getJumpSpeed());
                    sandstormTicks.set(sandstorm.getJumpTicks());
                } else if (curio instanceof BlizzardInABottle blizzard) {
                    blizzardSpeed.set(blizzard.getJumpSpeed());
                    blizzardTicks.set(blizzard.getJumpTicks());
                } else if (curio instanceof TsunamiInABottle tsunami) {
                    tsunamiSpeed.set(tsunami.getJumpSpeed());
                } else if (curio instanceof CloudInABottle cloud) {
                    cloudSpeed.set(cloud.getJumpSpeed());
                } else if (curio instanceof BundleOfBalloons) {
                    sandstormSpeed.set(SandstormInABottle.SPEED);
                    sandstormTicks.set(SandstormInABottle.TICKS);
                    blizzardSpeed.set(BlizzardInABottle.SPEED);
                    blizzardTicks.set(BlizzardInABottle.TICKS);
                    cloudSpeed.set(CloudInABottle.SPEED);
                }
                if (curio instanceof IMayFly iMayFly) {
                    maxFlyTicks.set(Math.max(iMayFly.getFlyTicks(), maxFlyTicks.get()));
                    flySpeed.set(Math.max(iMayFly.getFlySpeed(), flySpeed.get()));
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
        });


        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerJumpPacketS2C(
                fartSpeed.get(),
                sandstormSpeed.get(),
                sandstormTicks.get(),
                blizzardSpeed.get(),
                blizzardTicks.get(),
                tsunamiSpeed.get(),
                cloudSpeed.get()
            )
        );
        NetworkHandler.CHANNEL.send(
            PacketDistributor.PLAYER.with(() -> serverPlayer),
            new PlayerFlyPacketS2C(maxFlyTicks.get(), flySpeed.get(), glide.get())
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
    }
}
