package org.confluence.mod.util;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.combat.IAutoAttack;
import org.confluence.mod.item.curio.combat.IScope;
import org.confluence.mod.item.curio.construction.IRightClickSubtractor;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.network.s2c.*;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public final class ModUtils {
    public static float nextFloat(RandomSource randomSource, float origin, float bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return origin + randomSource.nextFloat() * (bound - origin);
        }
    }

    public static Component getModifierTooltip(double amount, String type) {
        boolean b = amount > 0.0;
        amount *= 100.0;
        return Component.translatable(
                "prefix.confluence.tooltip." + (b ? "plus" : "take"),
                ATTRIBUTE_MODIFIER_FORMAT.format(b ? amount : -amount),
                Component.translatable("prefix.confluence.tooltip." + type)
        ).withStyle(b ? ChatFormatting.BLUE : ChatFormatting.RED);
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
        AtomicBoolean autoAttack = new AtomicBoolean();
        AtomicBoolean scope = new AtomicBoolean();
        AtomicInteger substractor = new AtomicInteger();
        AtomicInteger climb = new AtomicInteger();
        AtomicBoolean shield = new AtomicBoolean();
        AtomicBoolean tabi = new AtomicBoolean();
        AtomicBoolean magil = new AtomicBoolean();


        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(curiosItemHandler -> {
            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
                Item curio = itemStack.getItem();
                if (curio == CurioItems.SHIELD_OF_CTHULHU.get()) {
                    shield.set(true);
                    continue;
                } else if (curio == CurioItems.MAGILUMINESCENCE.get()) {
                    magil.set(true);
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
                    sandstormSpeed.set(ModConfigs.SANDSTORM_IN_A_BALLOON_JUMP_SPEED.get());
                    sandstormTicks.set(ModConfigs.SANDSTORM_IN_A_BALLOON_JUMP_TICKS.get());
                    blizzardSpeed.set(ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_SPEED.get());
                    blizzardTicks.set(ModConfigs.BLIZZARD_IN_A_BALLOON_JUMP_TICKS.get());
                    cloudSpeed.set(ModConfigs.CLOUD_IN_A_BALLOON_JUMP_SPEED.get());
                }
                if (curio instanceof IMayFly iMayFly) {
                    maxFlyTicks.set(Math.max(iMayFly.getFlyTicks(), maxFlyTicks.get()));
                    flySpeed.set(Math.max(iMayFly.getFlySpeed(), flySpeed.get()));
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
                new PlayerFlyPacketS2C(maxFlyTicks.get(), flySpeed.get())
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
                new MagiluminescencePacketS2C(magil.get())
        );
    }
}
